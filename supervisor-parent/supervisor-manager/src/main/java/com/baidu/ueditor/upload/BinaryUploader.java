package com.baidu.ueditor.upload;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import third.rewrite.fastdfs.NameValuePair;
import third.rewrite.fastdfs.service.IStorageClientService;

import com.baidu.ueditor.FileEntity;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;

public class BinaryUploader
{
    
    public static final State save(HttpServletRequest request,
            IStorageClientService storageClientService, FileEntity[] files,
            Map<String, Object> conf)
    {
        //boolean isAjaxUpload = request.getHeader("X_Requested_With") != null;
        BaseState storageState = null;
        if (!ServletFileUpload.isMultipartContent(request))
        {
            return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
        }
        
        //        ServletFileUpload upload = new ServletFileUpload(
        //                new DiskFileItemFactory());
        //        
        //        if (isAjaxUpload)
        //        {
        //            upload.setHeaderEncoding("UTF-8");
        //        }
        
        try
        {
            if (files == null || files.length == 0)
            {
                return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
            }
            for (int i = 0; i < files.length; i++)
            {
                if (files[i] == null)
                {
                    continue;
                }
                String savePath = (String) conf.get("savePath");
                String originFileName = files[i].getOriginalName();
                String suffix = FileType.getSuffixByFilename(originFileName);
                originFileName = originFileName.substring(0,
                        originFileName.length() - suffix.length());
                savePath = savePath + suffix;
                long maxSize = ((Long) conf.get("maxSize")).longValue();
                
                if (!validType(suffix, (String[]) conf.get("allowFiles")))
                {
                    return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
                }
                if (maxSize < files[i].getData().length)
                {
                    return new BaseState(false, AppInfo.MAX_SIZE);
                }
                // 上传文件
                String filePath = storageClientService.uploadFile(null,
                        new ByteArrayInputStream(files[i].getData()),
                        files[i].getData().length,
                        files[i].getExtName().toUpperCase());
                
                // 上传元数据
                NameValuePair[] metaList = new NameValuePair[1];
                metaList[0] = new NameValuePair("originalName", originFileName);
                storageClientService.overwriteMetadata(filePath, metaList);
                
                storageState = new BaseState(true);
                storageState.putInfo("size", files[i].getData().length);
                storageState.putInfo("title", originFileName);
                //savePath = PathFormat.parse(savePath, originFileName);
                //String physicalPath = (String) conf.get("rootPath") + savePath;
                //                storageState = StorageManager.saveFileByInputStream(new ByteArrayInputStream(
                //                        files[i].getData()),
                //                        physicalPath,
                //                        maxSize);
                if (storageState.isSuccess())
                {
                    storageState.putInfo("url", filePath);
                    storageState.putInfo("type", suffix);
                    storageState.putInfo("original", originFileName + suffix);
                }
                
            }
            return storageState;
            // String savePath = (String) conf.get("savePath");
            // String originFileName = "wqewqew.jpg";
            // String suffix = FileType.getSuffixByFilename(originFileName);
            
            //            originFileName = originFileName.substring(0,
            //                    originFileName.length() - suffix.length());
            //            savePath = savePath + suffix;
            
            //long maxSize = ((Long) conf.get("maxSize")).longValue();
            
            //            if (!validType(suffix, (String[]) conf.get("allowFiles")))
            //            {
            //                return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
            //            }
            
            // savePath = PathFormat.parse(savePath, originFileName);
            
            //String physicalPath = (String) conf.get("rootPath") + savePath;
            
            //InputStream is = fileStream.openStream();
            //            State storageState = StorageManager.saveFileByInputStream(inputStream,
            //                    physicalPath,
            //                    maxSize);
            //            inputStream.close();
            
            //            if (storageState.isSuccess())
            //            {
            //                storageState.putInfo("url", PathFormat.format(savePath));
            //                storageState.putInfo("type", suffix);
            //                storageState.putInfo("original", originFileName + suffix);
            //            }
            //            
            //            return storageState;
        }
        catch (Exception e)
        {
            return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
        }
        // return new BaseState(false, AppInfo.IO_ERROR);
    }
    
    private static boolean validType(String type, String[] allowTypes)
    {
        List<String> list = Arrays.asList(allowTypes);
        
        return list.contains(type);
    }
}
