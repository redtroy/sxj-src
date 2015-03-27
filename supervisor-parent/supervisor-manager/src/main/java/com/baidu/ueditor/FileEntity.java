package com.baidu.ueditor;

import java.io.Serializable;

public class FileEntity implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 4067872576296730885L;
    
    private String originalName;
    
    private String extName;
    
    private byte[] data;
    
    public String getOriginalName()
    {
        return originalName;
    }
    
    public void setOriginalName(String originalName)
    {
        this.originalName = originalName;
    }
    
    public String getExtName()
    {
        return extName;
    }
    
    public void setExtName(String extName)
    {
        this.extName = extName;
    }
    
    public byte[] getData()
    {
        return data;
    }
    
    public void setData(byte[] data)
    {
        this.data = data;
    }
    
}
