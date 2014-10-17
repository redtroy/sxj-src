package com.sxj.file.fastdfs;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	private long cacheTime = 10 * 24 * 3600 * 1000;

	private TrackerClient tracker;

	private TrackerServer trackerServer;

	private StorageServer storageServer;

	static {
		// try {
		// InputStream inputFile = FastDFSImpl.class
		// .getResourceAsStream("file_client.conf");
		// File file = new File("file_client_new.conf");
		// FileOutputStream out = new FileOutputStream(file);
		// int c = inputFile.read();
		// while (c != -1) {
		// out.write(c);
		// c = inputFile.read();
		// }
		// inputFile.close();
		// out.close();
		// ClientGlobal.init(file.getPath());
		// LocalFileUtil.delete(file);
		// Logger.info("文件系统初始化成功");
		// } catch (IOException e) {
		// Logger.error(e.toString());
		// } catch (Exception e) {
		// Logger.error(e.toString());
		// }
	}

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
	}

	public String uploadFile(File file) {
		String file_id = null;
		try {
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
			HierarchicalCacheManager.set(LEVEL, CACHE_NAME, file_id, file_buff);

		} catch (SocketException e) {
			Logger.error(e.toString());
		} catch (Exception ex) {
			Logger.error(ex.toString());
		}

		return file_id;
	}

	public String uploadFile(byte[] file_buff, String originalName) {
		String file_id = null;
		try {
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
			storageServer.close();
			trackerServer.close();
			HierarchicalCacheManager.set(LEVEL, CACHE_NAME, file_id, file_buff);

		} catch (SocketException e) {
			Logger.error(e.toString());
		} catch (Exception ex) {
			Logger.error(ex.toString());
		}
		return file_id;
	}

	public String modfiyFile(String oldfile_id, byte[] newfile_buff,
			String newfile_ext_name) {
		String newFileId = null;
		try {
			if (StringUtils.isNotEmpty(oldfile_id)) {
				HierarchicalCacheManager.evict(LEVEL, CACHE_NAME, oldfile_id);
				removeFile(oldfile_id);
			}
			newFileId = uploadFile(newfile_buff, newfile_ext_name.toUpperCase());
			HierarchicalCacheManager.set(LEVEL, CACHE_NAME, newFileId,
					newfile_buff);
		} catch (Exception ex) {
			Logger.error(ex.toString());
		}
		return newFileId;
	}

	public byte[] downloadFile(String file_id) {
		byte[] file_buff = null;
		try {
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
			storageServer.close();
			trackerServer.close();
			if (file_buff != null && file_buff.length > 0) {
				HierarchicalCacheManager.set(LEVEL, CACHE_NAME, file_id,
						file_buff);
			}

		} catch (SocketException e) {
			SxjLogger.error("获取图片错误", e, this.getClass());
		} catch (Exception ex) {
			SxjLogger.error("获取图片错误", ex, this.getClass());
		}
		return file_buff;
	}

	@Override
	public List<NameValuePair> getMetaList(String file_Id) {
		List<NameValuePair> list = null;
		try {
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
		}
		return list;
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
					HierarchicalCacheManager.get(LEVEL, CACHE_NAME, key);
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

	public boolean removeFile(String file_id) {
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
		}
	}

	@Override
	public boolean removeFile(String[] file_Urls) {
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

	public long getCacheTime() {
		return cacheTime;
	}

	public void setCacheTime(long cacheTime) {
		this.cacheTime = cacheTime;
	}

	/**
	 * public static void main(String[] args) { try { String aa = "sasa.gif";
	 * boolean ac = aa.matches("[.]\\p{Punct}[.]"); System.out.println(ac); long
	 * start = System.currentTimeMillis(); IFileUpLoad fileuplaod =
	 * UpLoadFactory.buildImageUpLoad(); for (int i = 0; i < 2; i++) { byte[]
	 * ass = LocalFileUtil
	 * .readByte("C:/Users/dujinxin/Desktop/adu/DSC_0357.JPG"); String id =
	 * fileuplaod.uploadFile(ass, "jpg"); System.out.println(id);
	 * System.out.println(i); } // System.out.println(System.currentTimeMillis()
	 * - start); // FastDFSImpl im = new FastDFSImpl(); // im.deleteFile(
	 * "http://192.168.18.118/00/00/wKgSdkvFmcoAAAAAAAASoE3DieQ165.jpg"); //
	 * String file_id = //
	 * "http://192.168.18.118/data/00/AB/wKgSdkvFfIsAAAAAAAASoF6t6Q8275.jpg"; //
	 * // if (StringUtil.isNotEmpty(file_id)) { // file_id =
	 * file_id.substring(7, file_id.length()); // String[] aaa =
	 * file_id.split("/"); // String http = aaa[0] + "/" + aaa[1]; // file_id =
	 * file_id.substring(http.length(), file_id.length()); // http =
	 * properties.getProperty(http); // file_id = http + file_id; // } //
	 * System.out.println(file_id); String serviceUrl =
	 * "http://pay.5iding.com/eqtpayweb/paygetway/to_paygetway.htm?orderId=null&billId=BI201109062012170072;jsessionid=50E57794E5C6B4AE8CFC035372389162.node2&amount=null&returnUrl=null&type=2"
	 * ; // String serviceUrl = constructServiceUrl(request, response); // //
	 * int index = serviceUrl.lastIndexOf("jsessionid"); // if (index == -1) {
	 * // serviceUrl = URLDecoder.decode(serviceUrl, "utf-8"); // String[]
	 * serviceUrls = serviceUrl.split("[?]"); // serviceUrl = serviceUrls[0] +
	 * ";jsessionid=" // +"fsdfasdfasd.node4"; // if (serviceUrls.length == 2) {
	 * // serviceUrl = serviceUrl + "?" // + serviceUrls[serviceUrls.length -
	 * 1]; // } // serviceUrl = URLEncoder.encode(serviceUrl, "utf-8"); // } //
	 * Date aaa = new Date(System.currentTimeMillis() + (30 * 1000)); //
	 * Calendar calendar = Calendar.getInstance(); //
	 * calendar.add(Calendar.SECOND, 30); // System.out.println(aaa); //
	 * System.out.println(calendar.getTime()); } catch (Exception e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } }
	 **/

}
