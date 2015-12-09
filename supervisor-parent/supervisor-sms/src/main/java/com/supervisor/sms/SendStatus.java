package com.supervisor.sms;

import java.util.ArrayList;
import java.util.List;

public class SendStatus
{
    private String messageId;
    
    private List<SendStatus> statuses;
    
    private int messageCount;
    
    private String status;
    
    private String message;
    
    public String getMessageId()
    {
        return messageId;
    }
    
    public void setMessageId(String messageId)
    {
        this.messageId = messageId;
    }
    
    public int getMessageCount()
    {
        return messageCount;
    }
    
    public void setMessageCount(int messageCount)
    {
        this.messageCount = messageCount;
    }
    
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    public List<SendStatus> getStatuses()
    {
        return statuses;
    }
    
    public void setStatuses(List<SendStatus> statuses)
    {
        this.statuses = statuses;
    }
    
    public void addStatus(SendStatus status)
    {
        if (statuses == null)
            statuses = new ArrayList<SendStatus>();
        statuses.add(status);
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        if (statuses == null || statuses.size() == 0)
        {
            sb.append("消息编号：" + messageId);
            sb.append(",");
            sb.append("提交成功条数：" + messageCount);
            sb.append(",");
            sb.append(status);
            sb.append(",");
            sb.append(message);
        }
        else
        {
            for (SendStatus s : statuses)
            {
                sb.append(s.toString());
                sb.append("||");
            }
        }
        
        return sb.toString();
    }
    
    public String getMessage()
    {
        return message;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }
    
}
