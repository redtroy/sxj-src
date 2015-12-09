package com.supervisor.sms.impl.zucp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.supervisor.sms.HttpSmsSender;
import com.supervisor.sms.MobileException;
import com.supervisor.sms.SendStatus;

public class ZucpSender extends HttpSmsSender
{
    private String serviceURL;
    
    private String sn;
    
    private String pwd;
    
    private String ext = "";
    
    private String stime = "";
    
    private String rrid = "";
    
    @Override
    public void report(String sendId) throws MobileException
    {
        // TODO Auto-generated method stub
        
    }
    
    protected SendStatus buildSendStatus(String response)
    {
        Pattern pattern = Pattern
                .compile("<mdSmsSend_uResult>(.*)</mdSmsSend_uResult>");
        Matcher matcher = pattern.matcher(response);
        SendStatus status = new SendStatus();
        String result;
        while (matcher.find())
        {
            result = matcher.group(1);
            String message = getErrorMsg(result);
            if (message.equals(result))
            {
                status.setMessageId(result);
                status.setMessage("操作成功");
                status.setMessageCount(1);
            }
            else
            {
                status.setMessage(message);
                status.setMessageCount(0);
            }
        }
        return status;
        
    }
    
    private String getErrorMsg(String result)
    {
        switch (result)
        {
            case "1":
                return "没有需要取得的数据";
            case "-1":
                return "重复注册";
            case "-2":
                return "帐号/密码不正确";
            case "-4":
                return "余额不足支持本次发送";
            case "-5":
                return "数据格式错误";
            case "-6":
                return "参数有误";
            case "-7":
                return "权限受限";
            case "-8":
                return "流量控制错误";
            case "-9":
                return "扩展码权限错误";
            case "-10":
                return "内容长度长";
            case "-11":
                return "内部数据库错误";
            case "-12":
                return "序列号状态错误";
            case "-13":
                return "没有提交增值内容";
            case "-14":
                return "服务器写文件失败";
            case "-15":
                return "文件内容base64编码错误";
            case "-16":
                return "返回报告库参数错误";
            case "-17":
                return "没有权限";
            case "-18":
                return "上次提交没有等待返回不能继续提交";
            case "-19":
                return "禁止同时使用多个接口地址";
            case "-20":
                return "相同手机号，相同内容重复提交";
            case "-21":
                return "Ip鉴权失败";
                
        }
        return result;
    }
    
    private String buildSoapXml(String mobileNo, String content)
    {
        try
        {
            content = URLEncoder.encode(content, "utf8");
            String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
            xml += "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
            xml += "<soap:Body>";
            xml += "<mdSmsSend_u xmlns=\"http://tempuri.org/\">";
            xml += "<sn>" + sn + "</sn>";
            xml += "<pwd>"
                    + EncryptUtil.md5Hex(this.sn + this.pwd).toUpperCase()
                    + "</pwd>";
            xml += "<mobile>" + mobileNo + "</mobile>";
            xml += "<content>" + content + "</content>";
            xml += "<ext>" + ext + "</ext>";
            xml += "<stime>" + stime + "</stime>";
            xml += "<rrid>" + rrid + "</rrid>";
            xml += "</mdSmsSend_u>";
            xml += "</soap:Body>";
            xml += "</soap:Envelope>";
            return xml;
        }
        catch (UnsupportedEncodingException e)
        {
            throw new MobileException(e);
        }
    }
    
    public String getServiceURL()
    {
        return serviceURL;
    }
    
    public void setServiceURL(String serviceURL)
    {
        this.serviceURL = serviceURL;
    }
    
    public String getSn()
    {
        return sn;
    }
    
    public void setSn(String sn)
    {
        this.sn = sn;
    }
    
    public String getPwd()
    {
        return pwd;
    }
    
    public void setPwd(String pwd)
    {
        this.pwd = pwd;
    }
    
    public String getExt()
    {
        return ext;
    }
    
    public void setExt(String ext)
    {
        this.ext = ext;
    }
    
    public String getStime()
    {
        return stime;
    }
    
    public void setStime(String stime)
    {
        this.stime = stime;
    }
    
    public String getRrid()
    {
        return rrid;
    }
    
    public void setRrid(String rrid)
    {
        this.rrid = rrid;
    }
    
    @Override
    protected String sendSingle(String mobileNo, String content)
            throws MobileException
    {
        String soapAction = "http://tempuri.org/mdSmsSend_u";
        try
        {
            String soapXml = buildSoapXml(mobileNo, content);
            System.out.println(soapXml);
            String response = getHttpClient().postSoapXml(serviceURL,
                    soapXml,
                    soapAction);
            return response;
        }
        catch (Exception e)
        {
            throw new MobileException(e);
        }
    }
    
    @Override
    protected String[] sendBatch(String content, String[] mobileNos,
            boolean oneByOne) throws MobileException
    {
        List<String> result = new ArrayList<>();
        if (oneByOne)
        {
            SendStatus status = new SendStatus();
            for (String mobileNo : mobileNos)
            {
                result.add(sendSingle(mobileNo, content));
            }
            return result.toArray(new String[result.size()]);
        }
        else
        {
            StringBuilder sb = new StringBuilder();
            for (String mobileNo : mobileNos)
            {
                sb.append(mobileNo);
                sb.append(",");
            }
            result.add(sendSingle(sb.substring(0, sb.length() - 1), content));
            return result.toArray(new String[result.size()]);
        }
    }
    
}
