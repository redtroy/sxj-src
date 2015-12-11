package com.supervisor.sms;

public interface Sender
{
    public SendStatus sendBatch(String[] mobileNos, String content,
            boolean oneByOne) throws MobileException;
            
    public SendStatus send(String mobileNo, String content)
            throws MobileException;
            
    public void report(String sendId) throws MobileException;
    
}
