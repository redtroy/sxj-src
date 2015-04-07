package com.sxj.util.message;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.sxj.util.common.ISxjHttpClient;
import com.sxj.util.common.SxjHttpClientImpl;
import com.sxj.util.exception.SystemException;
import com.sxj.util.logger.SxjLogger;

public class NewSendMessage
{
    
    private ISxjHttpClient httpclient;
    
    private String smsUrl;
    
    private String userName;
    
    private String password;
    
    private String sign;
    
    private String type;
    
    private NewSendMessage(String smsUrl, String userName, String password,
            String sign, String type)
    {
        if (httpclient == null)
        {
            httpclient = new SxjHttpClientImpl();
        }
        this.smsUrl = smsUrl;
        this.userName = userName;
        this.password = password;
        this.sign = sign;
        this.type = type;
    }
    
    public static NewSendMessage getInstance(String smsUrl, String userName,
            String password, String sign, String type)
    {
        return new NewSendMessage(smsUrl, userName, password, sign, type);
    }
    
    public void sendMessage(String mobiles, String message)
            throws SystemException
    {
        Map<String, String> params = new HashMap<>();
        params.put("name", userName);
        params.put("pwd", password);
        params.put("mobile", mobiles);
        params.put("content", message);
        params.put("stime", "");
        params.put("sign", sign);
        params.put("type", type);
        params.put("extno", "");
        String res;
        try
        {
            res = httpclient.post(smsUrl, params);
            SxjLogger.info("发送结果: " + res + "短线内容:" + message, this.getClass());
        }
        catch (IOException e)
        {
            SxjLogger.info("发送失败: " + e.getMessage() + "短线内容:" + message,
                    this.getClass());
        }
        
    }
    
    public void sendMessage(String[] mobiles, String message)
            throws SystemException
    {
        Map<String, String> params = new HashMap<>();
        params.put("name", userName);
        params.put("pwd", password);
        StringBuffer mobile = new StringBuffer();
        for (int i = 0; i < mobiles.length; i++)
        {
            if (mobiles[i] == null)
            {
                continue;
            }
            mobile.append(mobiles[i]);
            if (i < mobiles.length - 1)
            {
                mobile.append(",");
            }
        }
        params.put("mobile", mobile.toString());
        params.put("content", message);
        params.put("stime", "");
        params.put("sign", sign);
        params.put("type", type);
        params.put("extno", "");
        String res;
        try
        {
            res = httpclient.post(smsUrl, params);
            SxjLogger.info("发送结果: " + res + "短线内容:" + message, this.getClass());
        }
        catch (IOException e)
        {
            SxjLogger.info("发送失败: " + e.getMessage() + "短线内容:" + message,
                    this.getClass());
        }
        
    }
    
    public static void main(String[] args) throws SystemException
    {
        SxjHttpClientImpl httpclient = new SxjHttpClientImpl();
        httpclient.setCharset("UTF-8");
        NewSendMessage me = NewSendMessage.getInstance("http://sms.1xinxi.cn/asmx/smsservice.aspx",
                "jssxj",
                "CEE4D6CC34577FB24D1726F8AFEB",
                "私享家验证码",
                "pt");
        String[] dfdf = { "13852295723", "18551626910", "15150535995" };
        me.sendMessage(dfdf, "私享家验证码3333");
    }
}
