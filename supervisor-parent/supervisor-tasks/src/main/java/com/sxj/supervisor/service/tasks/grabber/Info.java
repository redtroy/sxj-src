package com.sxj.supervisor.service.tasks.grabber;

public class Info
{
    private String content;
    
    private String image;
    
    public Info(String content, String image)
    {
        super();
        this.content = content;
        this.image = image;
    }
    
    public Info()
    {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public String getContent()
    {
        return content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
    
    public String getImage()
    {
        return image;
    }
    
    public void setImage(String image)
    {
        this.image = image;
    }
    
}
