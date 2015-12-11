package com.supervisor.sms.impl.xinxi1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.supervisor.sms.HttpSmsSender;
import com.supervisor.sms.MobileException;
import com.supervisor.sms.SendStatus;
import com.sxj.spring.modules.util.StringUtils;

public class Xinxi1Sender extends HttpSmsSender
{
    private String url;
    
    private String name;
    
    private String pwd;
    
    private String type;
    
    private String sign;
    
    protected SendStatus buildSendStatus(String response)
    {
        String[] codes = response.split(",");
        SendStatus status = new SendStatus();
        status.setStatus(codes[0]);
        status.setMessageCount(Integer.parseInt(codes[3]));
        status.setMessageId(codes[1]);
        status.setMessage(codes[5]);
        return status;
    }
    
    @Override
    public void report(String sendId) throws MobileException
    {
        // TODO Auto-generated method stub
        
    }
    
    public String getUrl()
    {
        return url;
    }
    
    public void setUrl(String url)
    {
        this.url = url;
    }
    
    public String getPwd()
    {
        return pwd;
    }
    
    public void setPwd(String pwd)
    {
        this.pwd = pwd;
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public String getSign()
    {
        return sign;
    }
    
    public void setSign(String sign)
    {
        this.sign = sign;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    @Override
    protected String sendSingle(String mobileNo, String content)
            throws MobileException
    {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("pwd", pwd);
        params.put("mobile", mobileNo);
        params.put("content", content);
        params.put("stime", "");
        params.put("sign", sign);
        params.put("type", type);
        params.put("extno", "");
        try
        {
            String response = getHttpClient().post(url, params);
            return response;
        }
        catch (IOException e)
        {
            throw new MobileException("短信发送失败", e);
        }
    }
    
    @Override
    protected String[] sendBatch(String content, String[] mobileNos,
            boolean oneByOne) throws MobileException
    {
        if (mobileNos == null || mobileNos.length < 1)
            throw new MobileException("发送手机号不能为空");
        if (StringUtils.isBlank(content))
            throw new MobileException("短信内容不能为空");
        List<String> result = new ArrayList<>();
        if (!oneByOne)
        {
            StringBuilder sb = new StringBuilder();
            for (String mobileNo : mobileNos)
                sb.append(mobileNo).append(",");
            sb.deleteCharAt(sb.length() - 1);
            result.add(sendSingle(sb.toString(), content));
            return result.toArray(new String[result.size()]);
        }
        else
        {
            for (String mobileNo : mobileNos)
            {
                result.add(sendSingle(mobileNo, content));
            }
            return result.toArray(new String[result.size()]);
        }
    }
    
}
