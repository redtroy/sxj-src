package third.rewrite.fastdfs.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import third.rewrite.fastdfs.FileInfo;
import third.rewrite.fastdfs.NameValuePair;
import third.rewrite.fastdfs.StorePath;

public interface IStorageClientService
{
    
    public static int LEVEL = 2;
    
    public static String CACHE_NAME = "FAST_DFS_CACHE";
    
    /**
     * 上传不可修改的文件
     * 
     * 
     * @param ins
     * @param size
     * @param ext
     * @return
     */
    String uploadFile(String groupName, InputStream ins, long size, String ext);
    
    /**
     * 上传可修改的文件
     * 
     * 
     * @param ins
     * @param size
     * @param ext
     * @return
     */
    StorePath uploadAppenderFile(String groupName, InputStream ins, long size,
            String ext);
    
    StorePath uploadSlaveFile(String groupName, String masterFilename,
            InputStream ins, long size, String prefixName, String ext);
    
    /**
     * 可修改文件添加内容
     * 
     * 
     * @param path
     * @param ins
     * @param size
     */
    void appendFile(String groupName, String path, InputStream ins, long size);
    
    /**
     * 修改可修改文件的内容
     * 
     * 
     * @param groupName
     * @param path
     * @param offset
     * @param ins
     * @param size
     */
    void modifyFile(String groupName, String path, long offset,
            InputStream ins, long size);
    
    /**
     * 删除文件
     * 
     * 
     * @param groupName
     * @param path
     */
    void deleteFile(String groupName, String path);
    
    /**
     * 删除文件
     * 
     * @param fielid
     */
    void deleteFile(String fielid);
    
    /**
     * 清除appender类型文件的内容
     * 
     * 
     * @param groupName
     * @param path
     */
    void truncateFile(String groupName, String path, long truncatedFileSize);
    
    /**
     * 下载整个文件
     * 
     * 
     * @param groupName
     * @param path
     * @param handling
     * @return
     */
    void downloadFile(String groupName, String path, OutputStream output);
    
    /**
     * 下载图片缩率图
     * 
     * 
     * @param groupName
     * @param path
     * @param handling
     * @return
     */
    void downloadSmallImage(String groupName, String path, int width,
            int height, OutputStream outputStream);
    
    //    /**
    //     * 下载文件片段
    //     * 
    //     * 
    //     * @param groupName
    //     * @param path
    //     * @param offset
    //     * @param size
    //     * @param handling
    //     * @return
    //     */
    //    FdfsInputStream downloadFile(String groupName, String path, long offset,
    //            long size);
    
    /**
     * 获取文件元信息
     * 
     * 
     * @param groupName
     * @param path
     * @return
     */
    NameValuePair[] getMetadata(String groupName, String path);
    
    /**
     * 获取文件元信息
     * 
     * @param fileids
     * @return
     */
    Map<String, NameValuePair[]> getMetadata(String[] fileids);
    
    /**
     * 修改文件元信息（覆盖）
     * 
     * 
     * @param groupName
     * @param path
     * @param metaList
     */
    void overwriteMetadata(String groupName, String path,
            NameValuePair[] metaList);
    
    /**
     * 修改文件元信息（覆盖）
     * 
     * @param fileId
     * @param metaList
     */
    void overwriteMetadata(String fileId, NameValuePair[] metaList);
    
    /**
     * 修改文件元信息（合并）
     * 
     * 
     * @param groupName
     * @param path
     * @param metaList
     */
    void mergeMetadata(String groupName, String path, NameValuePair[] metaList);
    
    /**
     * 查看文件的信息
     * 
     * 
     * @param groupName
     * @param path
     * @return
     */
    FileInfo queryFileInfo(String groupName, String path);
}
