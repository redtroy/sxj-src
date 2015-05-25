package third.rewrite.fastdfs.service.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;

import third.rewrite.fastdfs.FileInfo;
import third.rewrite.fastdfs.NameValuePair;
import third.rewrite.fastdfs.StorageClient;
import third.rewrite.fastdfs.StorePath;
import third.rewrite.fastdfs.exception.FdfsIOException;
import third.rewrite.fastdfs.proto.OtherConstants;
import third.rewrite.fastdfs.proto.handler.ICmdProtoHandler;
import third.rewrite.fastdfs.proto.handler.StorageAppendHandler;
import third.rewrite.fastdfs.proto.handler.StorageDeleteHandler;
import third.rewrite.fastdfs.proto.handler.StorageDownloadHandler;
import third.rewrite.fastdfs.proto.handler.StorageGetMetadataHandler;
import third.rewrite.fastdfs.proto.handler.StorageModifyHandler;
import third.rewrite.fastdfs.proto.handler.StorageQueryFileInfoHandler;
import third.rewrite.fastdfs.proto.handler.StorageSetMetadataHandler;
import third.rewrite.fastdfs.proto.handler.StorageTruncateHandler;
import third.rewrite.fastdfs.proto.handler.StorageUploadHandler;
import third.rewrite.fastdfs.proto.handler.StorageUploadSlaveHandler;
import third.rewrite.fastdfs.service.IStorageClientService;
import third.rewrite.fastdfs.service.ITrackerClientService;
import third.rewrite.fastdfs.socket.FdfsInputStream;
import third.rewrite.fastdfs.socket.FdfsSocket;
import third.rewrite.fastdfs.socket.FdfsSocketService;
import third.rewrite.fastdfs.socket.PooledFdfsSocket;

import com.sxj.cache.manager.CacheLevel;
import com.sxj.cache.manager.HierarchicalCacheManager;
import com.sxj.file.common.ImageUtil;
import com.sxj.file.common.LocalFileUtil;
import com.sxj.util.common.StringUtils;

public class StorageClientService implements IStorageClientService
{
    
    private FdfsSocketService fdfsSocketService;
    
    private ITrackerClientService trackerClientService;
    
    private String groupName;
    
    private int cacheTime = 3600;
    
    private <T> T process(FdfsSocket socket, ICmdProtoHandler<T> handler)
    {
        try
        {
            return handler.handle();
        }
        finally
        {
            IOUtils.closeQuietly(socket);
        }
    }
    
    @Override
    public String uploadFile(String groupName, InputStream ins, long size,
            String ext)
    {
        if (groupName == null)
        {
            groupName = this.groupName;
        }
        StorageClient storageClient = trackerClientService.getStoreStorage(groupName);
        FdfsSocket socket = fdfsSocketService.getSocket(storageClient.getInetSocketAddress());
        ICmdProtoHandler<StorePath> handler = new StorageUploadHandler(socket,
                false, ins, size, storageClient.getStoreIndex(), ext,
                storageClient.getCharset());
        StorePath filePath = process(socket, handler);
        return groupName + "/" + filePath.getPath();
    }
    
    @Override
    public StorePath uploadAppenderFile(String groupName, InputStream ins,
            long size, String ext)
    {
        StorageClient storageClient = trackerClientService.getStoreStorage(groupName);
        FdfsSocket socket = fdfsSocketService.getSocket(storageClient.getInetSocketAddress());
        ICmdProtoHandler<StorePath> handler = new StorageUploadHandler(socket,
                true, ins, size, storageClient.getStoreIndex(), ext,
                storageClient.getCharset());
        return process(socket, handler);
    }
    
    @Override
    public StorePath uploadSlaveFile(String groupName, String masterFilename,
            InputStream ins, long size, String prefixName, String ext)
    {
        StorageClient storageClient = trackerClientService.getUpdateStorage(groupName,
                masterFilename);
        FdfsSocket socket = fdfsSocketService.getSocket(storageClient.getInetSocketAddress());
        ICmdProtoHandler<StorePath> handler = new StorageUploadSlaveHandler(
                socket, ins, size, masterFilename, prefixName, ext,
                storageClient.getCharset());
        return process(socket, handler);
    }
    
    @Override
    public void appendFile(String groupName, String path, InputStream ins,
            long size)
    {
        StorageClient storageClient = trackerClientService.getUpdateStorage(groupName,
                path);
        FdfsSocket socket = fdfsSocketService.getSocket(storageClient.getInetSocketAddress());
        ICmdProtoHandler<Void> handler = new StorageAppendHandler(socket, ins,
                size, path, storageClient.getCharset());
        process(socket, handler);
    }
    
    @Override
    public void modifyFile(String groupName, String path, long offset,
            InputStream ins, long size)
    {
        StorageClient storageClient = trackerClientService.getUpdateStorage(groupName,
                path);
        FdfsSocket socket = fdfsSocketService.getSocket(storageClient.getInetSocketAddress());
        ICmdProtoHandler<Void> handler = new StorageModifyHandler(socket, ins,
                size, path, offset, storageClient.getCharset());
        process(socket, handler);
    }
    
    @Override
    public void deleteFile(String groupName, String path)
    {
        if (groupName == null)
        {
            groupName = this.groupName;
        }
        StorageClient storageClient = trackerClientService.getUpdateStorage(groupName,
                path);
        FdfsSocket socket = fdfsSocketService.getSocket(storageClient.getInetSocketAddress());
        ICmdProtoHandler<Void> handler = new StorageDeleteHandler(socket,
                groupName, path, storageClient.getCharset());
        process(socket, handler);
    }
    
    @Override
    public void deleteFile(String fielid)
    {
        if (fielid != null)
        {
            int index = fielid.indexOf("/");
            String groupName = fielid.substring(0, index);
            String path = fielid.substring(index + 1, fielid.length());
            deleteFile(groupName, path);
        }
    }
    
    @Override
    public void truncateFile(String groupName, String path,
            long truncatedFileSize)
    {
        StorageClient storageClient = trackerClientService.getUpdateStorage(groupName,
                path);
        
        FdfsSocket socket = fdfsSocketService.getSocket(storageClient.getInetSocketAddress());
        ICmdProtoHandler<Void> handler = new StorageTruncateHandler(socket,
                path, truncatedFileSize, storageClient.getCharset());
        process(socket, handler);
        
    }
    
    @Override
    public void downloadFile(String groupName, String path, OutputStream output)
    {
        
        long offset = 0;
        long size = 0;
        //String file_id = groupName + "/" + path;
        //        Object object = HierarchicalCacheManager.get(CacheLevel.REDIS,
        //                CACHE_NAME,
        //                file_id);
        //        if (object != null)
        //        {
        //            if (object instanceof byte[])
        //            {
        //                try
        //                {
        //                    output.write((byte[]) object);
        //                    return;
        //                }
        //                catch (IOException e)
        //                {
        //                    throw new FdfsIOException(e);
        //                }
        //                
        //            }
        //        }
        StorageClient storageClient = trackerClientService.getFetchStorage(groupName,
                path);
        FdfsSocket socket = fdfsSocketService.getSocket(storageClient.getInetSocketAddress());
        ICmdProtoHandler<FdfsInputStream> handler = new StorageDownloadHandler(
                socket, groupName, path, offset, size,
                storageClient.getCharset());
        try
        {
            FdfsInputStream fdfsInputStream = handler.handle();
            byte[] buffer = new byte[1024];
            int read = 0;
            while ((read = fdfsInputStream.read(buffer)) > 0)
            {
                output.write(buffer, 0, read);
            }
            if (!fdfsInputStream.isReadCompleted()
                    && socket instanceof PooledFdfsSocket)
            {
                ((PooledFdfsSocket) socket).setNeedDestroy(true);
            }
            IOUtils.closeQuietly(fdfsInputStream);
        }
        catch (IOException e)
        {
            if (socket instanceof PooledFdfsSocket)
            {
                ((PooledFdfsSocket) socket).setNeedDestroy(true);
            }
            throw new FdfsIOException(e);
        }
        finally
        {
            IOUtils.closeQuietly(socket);
        }
        
    }
    
    @Override
    public void downloadSmallImage(String groupName, String path, int width,
            int height, OutputStream output)
    {
        long offset = 0;
        long size = 0;
        String file_id = groupName + "/" + path;
        String file_ext_name = LocalFileUtil.getFileExtName(file_id);
        String key = file_id + width + "x" + height + "." + file_ext_name;
        Object small = HierarchicalCacheManager.get(CacheLevel.REDIS,
                CACHE_NAME,
                key);
        if (small != null)
        {
            if (small instanceof BufferedImage)
            {
                try
                {
                    ImageIO.write((BufferedImage) small, file_ext_name, output);// 输出到文件流
                    return;
                }
                catch (IOException e)
                {
                    throw new FdfsIOException(e);
                }
            }
        }
        StorageClient storageClient = trackerClientService.getFetchStorage(groupName,
                path);
        FdfsSocket socket = fdfsSocketService.getSocket(storageClient.getInetSocketAddress());
        ICmdProtoHandler<FdfsInputStream> handler = new StorageDownloadHandler(
                socket, groupName, path, offset, size,
                storageClient.getCharset());
        
        try
        {
            FdfsInputStream fdfsInputStream = handler.handle();
            BufferedImage image = ImageUtil.scaleFixed(fdfsInputStream,
                    output,
                    width,
                    height,
                    file_ext_name,
                    false);
            if (image != null)
            {
                //                HierarchicalCacheManager.set(CacheLevel.REDIS,
                //                        CACHE_NAME,
                //                        key,
                //                        image,
                //                        cacheTime);
            }
            if (!fdfsInputStream.isReadCompleted()
                    && socket instanceof PooledFdfsSocket)
            {
                ((PooledFdfsSocket) socket).setNeedDestroy(true);
            }
            IOUtils.closeQuietly(fdfsInputStream);
        }
        catch (Exception e)
        {
            if (socket instanceof PooledFdfsSocket)
            {
                ((PooledFdfsSocket) socket).setNeedDestroy(true);
            }
            throw new FdfsIOException(e);
        }
        finally
        {
            IOUtils.closeQuietly(socket);
        }
        
    }
    
    //    @Override
    //    public FdfsInputStream downloadFile(String groupName, String path,
    //            long offset, long size)
    //    {
    //        
    //        StorageClient storageClient = trackerClientService.getFetchStorage(groupName,
    //                path);
    //        
    //        FdfsSocket socket = fdfsSocketService.getSocket(storageClient.getInetSocketAddress());
    //        ICmdProtoHandler<FdfsInputStream> handler = new StorageDownloadHandler(
    //                socket, groupName, path, offset, size,
    //                storageClient.getCharset());
    //        
    //        try
    //        {
    //            FdfsInputStream fdfsInputStream = handler.handle();
    //            return fdfsInputStream;
    //            
    //            //            T result = handling.deal(fdfsInputStream);
    //            //            
    //            //            if (!fdfsInputStream.isReadCompleted()
    //            //                    && socket instanceof PooledFdfsSocket)
    //            //            {
    //            //                ((PooledFdfsSocket) socket).setNeedDestroy(true);
    //            //            }
    //            //            IOUtils.closeQuietly(fdfsInputStream);
    //            //            return null;
    //        }
    //        catch (IOException e)
    //        {
    //            if (socket instanceof PooledFdfsSocket)
    //            {
    //                ((PooledFdfsSocket) socket).setNeedDestroy(true);
    //            }
    //            throw new FdfsIOException(e);
    //        }
    //        finally
    //        {
    //            IOUtils.closeQuietly(socket);
    //        }
    //        
    //    }
    //    
    @Override
    public NameValuePair[] getMetadata(String groupName, String path)
    {
        if (groupName == null)
        {
            groupName = this.groupName;
        }
        StorageClient storageClient = trackerClientService.getFetchStorage(groupName,
                path);
        FdfsSocket socket = fdfsSocketService.getSocket(storageClient.getInetSocketAddress());
        ICmdProtoHandler<NameValuePair[]> handler = new StorageGetMetadataHandler(
                socket, groupName, path, storageClient.getCharset());
        return process(socket, handler);
    }
    
    @Override
    public Map<String, NameValuePair[]> getMetadata(String[] fileids)
    {
        Map<String, NameValuePair[]> map = new HashMap<String, NameValuePair[]>();
        if (fileids == null)
        {
            return map;
        }
        for (int i = 0; i < fileids.length; i++)
        {
            if (StringUtils.isEmpty(fileids[i]))
            {
                continue;
            }
            int index = fileids[i].indexOf("/");
            String groupName = fileids[i].substring(0, index);
            String path = fileids[i].substring(index + 1, fileids[i].length());
            NameValuePair[] metadata = getMetadata(groupName, path);
            map.put(fileids[i], metadata);
        }
        return map;
    }
    
    @Override
    public void overwriteMetadata(String groupName, String path,
            NameValuePair[] metaList)
    {
        StorageClient storageClient = trackerClientService.getUpdateStorage(groupName,
                path);
        FdfsSocket socket = fdfsSocketService.getSocket(storageClient.getInetSocketAddress());
        ICmdProtoHandler<Void> handler = new StorageSetMetadataHandler(socket,
                groupName, path, metaList,
                OtherConstants.STORAGE_SET_METADATA_FLAG_OVERWRITE,
                storageClient.getCharset());
        process(socket, handler);
        
    }
    
    @Override
    public void overwriteMetadata(String fileId, NameValuePair[] metaList)
    {
        if (fileId == null)
        {
            return;
        }
        int index = fileId.indexOf("/");
        String groupName = fileId.substring(0, index);
        String path = fileId.substring(index + 1, fileId.length());
        overwriteMetadata(groupName, path, metaList);
    }
    
    @Override
    public void mergeMetadata(String groupName, String path,
            NameValuePair[] metaList)
    {
        StorageClient storageClient = trackerClientService.getUpdateStorage(groupName,
                path);
        FdfsSocket socket = fdfsSocketService.getSocket(storageClient.getInetSocketAddress());
        ICmdProtoHandler<Void> handler = new StorageSetMetadataHandler(socket,
                groupName, path, metaList,
                OtherConstants.STORAGE_SET_METADATA_FLAG_MERGE,
                storageClient.getCharset());
        process(socket, handler);
        
    }
    
    @Override
    public FileInfo queryFileInfo(String groupName, String path)
    {
        StorageClient storageClient = trackerClientService.getFetchStorage(groupName,
                path);
        FdfsSocket socket = fdfsSocketService.getSocket(storageClient.getInetSocketAddress());
        ICmdProtoHandler<FileInfo> handler = new StorageQueryFileInfoHandler(
                socket, groupName, path, storageClient.getCharset());
        return process(socket, handler);
    }
    
    /**
     * @param trackerClientService
     */
    public void setTrackerClientService(
            ITrackerClientService trackerClientService)
    {
        this.trackerClientService = trackerClientService;
    }
    
    /**
     * @param fdfsSocketService
     *            the fdfsSocketService to set
     */
    public void setFdfsSocketService(FdfsSocketService fdfsSocketService)
    {
        this.fdfsSocketService = fdfsSocketService;
    }
    
    public String getGroupName()
    {
        return groupName;
    }
    
    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }
    
    public int getCacheTime()
    {
        return cacheTime;
    }
    
    public void setCacheTime(int cacheTime)
    {
        this.cacheTime = cacheTime;
    }
    
}
