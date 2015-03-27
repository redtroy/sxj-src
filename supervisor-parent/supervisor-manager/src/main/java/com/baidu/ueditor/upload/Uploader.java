package com.baidu.ueditor.upload;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import third.rewrite.fastdfs.service.IStorageClientService;

import com.baidu.ueditor.FileEntity;
import com.baidu.ueditor.define.State;

public class Uploader
{
    private HttpServletRequest request = null;
    
    private Map<String, Object> conf = null;
    
    private FileEntity[] files = null;
    
    private IStorageClientService storageClientService;
    
    public Uploader(HttpServletRequest request,
            IStorageClientService storageClientService, FileEntity[] files,
            Map<String, Object> conf)
    {
        this.request = request;
        this.files = files;
        this.conf = conf;
        this.storageClientService = storageClientService;
    }
    
    public final State doExec()
    {
        String filedName = (String) this.conf.get("fieldName");
        State state = null;
        
        if ("true".equals(this.conf.get("isBase64")))
        {
            state = Base64Uploader.save(this.request.getParameter(filedName),
                    this.conf);
        }
        else
        {
            state = BinaryUploader.save(this.request,
                    this.storageClientService,
                    this.files,
                    this.conf);
        }
        
        return state;
    }
}
