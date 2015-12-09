package com.supervisor.sms;

import com.supervisor.httpclient.HttpClient;

public abstract class HttpSmsSender implements Sender
{
    private HttpClient httpClient;
    
    private MobileFilter fitler;
    
    protected HttpClient getHttpClient()
    {
        if (httpClient == null)
        {
            httpClient = new HttpClient();
            httpClient.setCharset("UTF-8");
        }
        return httpClient;
    }
    
    protected abstract SendStatus buildSendStatus(String xml);
    
    protected abstract String sendSingle(String mobileNo, String content)
            throws MobileException;
            
    protected abstract String[] sendBatch(String content, String[] mobileNos,
            boolean oneByOne) throws MobileException;
            
    private SendStatus buildError(String messageId, String message)
    {
        SendStatus status = new SendStatus();
        status.setMessageId(messageId);
        status.setMessage(message);
        status.setStatus("ERROR");
        return status;
    }
    
    @Override
    public SendStatus sendBatch(String[] mobileNos, String content,
            boolean oneByOne) throws MobileException
    {
        String[] fMobiles = fitler.filter(this, mobileNos);
        if (fMobiles == null || fMobiles.length == 0)
            //throw new MobileException("号码验证失败！");
            return buildError("-9999", "号码校验失败");
        String[] responses = sendBatch(content, fMobiles, oneByOne);
        SendStatus status = new SendStatus();
        for (String response : responses)
        {
            status.addStatus(buildSendStatus(response));
        }
        return status;
    }
    
    @Override
    public SendStatus send(String mobileNo, String content)
            throws MobileException
    {
        if (!fitler.filter(this, mobileNo))
            return buildError("-9999", "号码校验失败");
        String response = sendSingle(mobileNo, content);
        return buildSendStatus(response);
    }
    
    public void setMobileFilter(MobileFilter filter)
    {
        this.fitler = filter;
    }
    
}
