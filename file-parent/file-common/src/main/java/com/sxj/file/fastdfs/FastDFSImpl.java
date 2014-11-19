package com.sxj.file.fastdfs;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Semaphore;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerGroup;
import org.csource.fastdfs.TrackerServer;

import com.sxj.cache.manager.HierarchicalCacheManager;
import com.sxj.file.common.ImageUtil;
import com.sxj.file.common.LocalFileUtil;
import com.sxj.util.common.StringUtils;
import com.sxj.util.logger.SxjLogger;

public class FastDFSImpl implements IFileUpLoad {

	private static final Log Logger = LogFactory.getLog(FastDFSImpl.class);

	private DfsConfig config;

	private String trackerHost;

	private Integer trackerPort;

	private int cacheTime = 60;

	private static TrackerClient tracker;

	private TrackerServer trackerServer;

	private StorageServer storageServer;

	private Integer maxThreads = 200;

	private static Semaphore lock;

	public void init() {
		if ("no".equals(config.getAnti_steal_token())) {
			ClientGlobal.setG_anti_steal_token(false);
		} else {
			ClientGlobal.setG_anti_steal_token(true);
		}
		ClientGlobal.setG_charset(config.getCharset());
		ClientGlobal.setG_connect_timeout(config.getConnect_timeout());
		ClientGlobal.setG_network_timeout(config.getNetwork_timeout());
		ClientGlobal.setG_secret_key(config.getSecret_key());
		ClientGlobal.setG_tracker_http_port(config.getTracker_http_port());
		TrackerGroup group = new TrackerGroup(
				new InetSocketAddress[] { new InetSocketAddress(trackerHost,
						trackerPort) });
		tracker = new TrackerClient(group);
		lock = new Semaphore(getMaxThreads(), true);
	}

	public String uploadFile(File file) throws IOException {
		String file_id = null;
		try {
			lock.acquire();
			trackerServer = tracker.getConnection();
			storageServer = tracker.getStoreStorage(trackerServer);
			StorageClient1 client = new StorageClient1(trackerServer,
					storageServer);
			if (file == null) {
				return null;
			}
			byte[] file_buff = LocalFileUtil.readByte(file);
			String file_ext_name = LocalFileUtil.getFileExtName(file.getName());
			List<NameValuePair> meta_list = new ArrayList<NameValuePair>();
			meta_list.add(new NameValuePair("originalName", file.getName()));
			file_id = client.upload_file1(file_buff,
					file_ext_name.toUpperCase(),
					meta_list.toArray(new NameValuePair[meta_list.size()]));
			storageServer.close();
			trackerServer.close();
			HierarchicalCacheManager.set(LEVEL, CACHE_NAME, file_id, file_buff,
					cacheTime);

		} catch (SocketException e) {
			Logger.error(e.toString());
		} catch (Exception ex) {
			Logger.error(ex.toString());
		} finally {
			if (storageServer != null)
				storageServer.close();
			if (trackerServer != null)
				trackerServer.close();
			lock.release();
		}

		return file_id;
	}

	public String uploadFile(byte[] file_buff, String originalName)
			throws IOException {
		String file_id = null;
		try {
			lock.acquire();
			trackerServer = tracker.getConnection();
			storageServer = tracker.getStoreStorage(trackerServer);
			StorageClient1 client = new StorageClient1(trackerServer,
					storageServer);
			String file_ext_name = LocalFileUtil.getFileExtName(originalName);
			List<NameValuePair> meta_list = new ArrayList<NameValuePair>();
			meta_list.add(new NameValuePair("originalName", originalName));
			file_id = client.upload_file1(file_buff,
					file_ext_name.toUpperCase(),
					meta_list.toArray(new NameValuePair[meta_list.size()]));
			HierarchicalCacheManager.set(LEVEL, CACHE_NAME, file_id, file_buff,
					cacheTime);

		} catch (SocketException e) {
			Logger.error(e.toString());
		} catch (Exception ex) {
			Logger.error(ex.toString());
		} finally {
			if (storageServer != null)
				storageServer.close();
			lock.release();
		}
		return file_id;
	}

	@Override
	public List<String> uploadFile(List<byte[]> file_buffs,
			List<String> originalName) throws IOException {
		List<String> file_ids = new ArrayList<String>();
		try {
			lock.acquire();
			trackerServer = tracker.getConnection();
			storageServer = tracker.getStoreStorage(trackerServer);
			StorageClient1 client = new StorageClient1(trackerServer,
					storageServer);
			int i = 0;
			for (byte[] file_buff : file_buffs) {
				String file_ext_name = LocalFileUtil
						.getFileExtName(originalName.get(i));
				List<NameValuePair> meta_list = new ArrayList<NameValuePair>();
				meta_list.add(new NameValuePair("originalName", originalName
						.get(i)));
				String file_id = client.upload_file1(file_buff,
						file_ext_name.toUpperCase(),
						meta_list.toArray(new NameValuePair[meta_list.size()]));
				file_ids.add(file_id);
				HierarchicalCacheManager.set(LEVEL, CACHE_NAME, file_id,
						file_buff, cacheTime);
				i++;
			}
			storageServer.close();
			trackerServer.close();

		} catch (SocketException e) {
			Logger.error(e.toString());
		} catch (Exception ex) {
			Logger.error(ex.toString());
		} finally {
			if (storageServer != null)
				storageServer.close();
			if (trackerServer != null)
				trackerServer.close();
			lock.release();
		}
		return file_ids;
	}

	public String modfiyFile(String oldfile_id, byte[] newfile_buff,
			String newfile_ext_name) throws IOException {
		String newFileId = null;
		try {
			if (StringUtils.isNotEmpty(oldfile_id)) {
				HierarchicalCacheManager.evict(LEVEL, CACHE_NAME, oldfile_id);
				removeFile(oldfile_id);
			}
			newFileId = uploadFile(newfile_buff, newfile_ext_name.toUpperCase());
			HierarchicalCacheManager.set(LEVEL, CACHE_NAME, newFileId,
					newfile_buff, cacheTime);
		} catch (Exception ex) {
			Logger.error(ex.toString());
		} finally {
			if (storageServer != null)
				storageServer.close();
			if (trackerServer != null)
				trackerServer.close();
			lock.release();
		}
		return newFileId;
	}

	public byte[] downloadFile(String file_id) throws IOException {
		byte[] file_buff = null;
		try {
			lock.acquire();
			Object object = HierarchicalCacheManager.get(LEVEL, CACHE_NAME,
					file_id);
			if (object != null) {
				if (object instanceof byte[]) {
					return (byte[]) object;
				}
			}
			trackerServer = tracker.getConnection();
			storageServer = tracker.getStoreStorage(trackerServer);
			StorageClient1 client = new StorageClient1(trackerServer,
					storageServer);
			file_buff = client.download_file1(file_id);
			if (file_buff != null && file_buff.length > 0) {
				HierarchicalCacheManager.set(LEVEL, CACHE_NAME, file_id,
						file_buff, cacheTime);
			}

		} catch (SocketException e) {
			SxjLogger.error("获取图片错误", e, this.getClass());
		} catch (Exception ex) {
			SxjLogger.error("获取图片错误", ex, this.getClass());
		} finally {
			if (storageServer != null)
				storageServer.close();
			if (trackerServer != null)
				trackerServer.close();
			lock.release();
		}
		return file_buff;
	}

	@Override
	public List<NameValuePair> getMetaList(String file_Id) throws IOException {
		List<NameValuePair> list = null;
		try {
			Logger.info("获取元数据的ID=" + file_Id);
			lock.acquire();
			trackerServer = tracker.getConnection();
			storageServer = tracker.getStoreStorage(trackerServer);
			StorageClient1 client = new StorageClient1(trackerServer,
					storageServer);
			NameValuePair[] values = client.get_metadata1(file_Id);
			if (values != null) {
				list = Arrays.asList(values);
			}
		} catch (Exception e) {
			SxjLogger.error("获取图片元信息错误", e, this.getClass());
		} finally {
			if (storageServer != null)
				storageServer.close();
			if (trackerServer != null)
				trackerServer.close();
			lock.release();
		}
		return list;
	}

	public Map<String, NameValuePair[]> getMetaList(String[] file_Ids)
			throws IOException {
		Map<String, NameValuePair[]> nameMap = new TreeMap<String, NameValuePair[]>();
		try {
			lock.acquire();
			trackerServer = tracker.getConnection();
			storageServer = tracker.getStoreStorage(trackerServer);
			StorageClient1 client = new StorageClient1(trackerServer,
					storageServer);
			for (int i = 0; i < file_Ids.length; i++) {
				if (file_Ids[i] == null) {
					continue;
				}
				Logger.info("获取元数据的ID=" + file_Ids[i]);
				NameValuePair[] values = client.get_metadata1(file_Ids[i]);
				if (values != null) {
					nameMap.put(file_Ids[i], values);
				}
			}

		} catch (Exception e) {
			SxjLogger.error("获取图片元信息错误", e, this.getClass());
		} finally {
			if (storageServer != null)
				storageServer.close();
			if (trackerServer != null)
				trackerServer.close();
			lock.release();
		}
		return nameMap;
	}

	public byte[] getSmallImage(String file_id, int width, int height) {
		try {
			String file_ext_name = LocalFileUtil.getFileExtName(file_id);
			String key = file_id + width + "x" + height + "." + file_ext_name;
			Object small = HierarchicalCacheManager.get(LEVEL, CACHE_NAME, key);
			if (small != null) {
				if (small instanceof byte[]) {
					return (byte[]) small;
				}
			}
			byte[] file_buff = downloadFile(file_id);
			if (file_buff != null && file_buff.length > 0) {
				byte[] smallBytes = ImageUtil.scaleFixed(file_buff, width,
						height, file_ext_name, false);
				if (smallBytes != null && smallBytes.length > 0) {
					HierarchicalCacheManager.set(LEVEL, CACHE_NAME, key,
							smallBytes, cacheTime);
				}
				return smallBytes;
			} else {
				return null;
			}

		} catch (Exception ex) {
			SxjLogger.error("获取图片错误", ex, this.getClass());
			return null;
		}
	}

	public boolean removeFile(String file_id) throws IOException {
		try {
			trackerServer = tracker.getConnection();
			storageServer = tracker.getStoreStorage(trackerServer);
			StorageClient1 client = new StorageClient1(trackerServer,
					storageServer);
			int errno = client.delete_file1(file_id);
			storageServer.close();
			trackerServer.close();
			if (errno != 0) {
				return false;
			}
			HierarchicalCacheManager.evict(LEVEL, CACHE_NAME, file_id);
			return true;
		} catch (SocketException e) {
			Logger.error(e.toString());
			return false;
		} catch (Exception ex) {
			Logger.error(ex.toString());
			return false;
		} finally {
			if (storageServer != null)
				storageServer.close();
			if (trackerServer != null)
				trackerServer.close();
		}
	}

	@Override
	public boolean removeFile(String[] file_Urls) throws IOException {
		try {
			if (file_Urls == null) {
				return false;
			}
			for (int i = 0; i < file_Urls.length; i++) {
				removeFile(file_Urls[i]);
			}
			return true;
		} catch (Exception e) {
			Logger.error(e.toString());
			return false;
		} finally {
			if (storageServer != null)
				storageServer.close();
			if (trackerServer != null)
				trackerServer.close();
		}
	}

	public DfsConfig getConfig() {
		return config;
	}

	public void setConfig(DfsConfig config) {
		this.config = config;
	}

	public String getTrackerHost() {
		return trackerHost;
	}

	public void setTrackerHost(String trackerHost) {
		this.trackerHost = trackerHost;
	}

	public Integer getTrackerPort() {
		return trackerPort;
	}

	public void setTrackerPort(Integer trackerPort) {
		this.trackerPort = trackerPort;
	}

	public int getCacheTime() {
		return cacheTime;
	}

	public void setCacheTime(int cacheTime) {
		this.cacheTime = cacheTime;
	}

	public Integer getMaxThreads() {
		return maxThreads;
	}

	public void setMaxThreads(Integer maxThreads) {
		this.maxThreads = maxThreads;
	}
}
