package com.sxj.util.message;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sxj.util.common.EncryptUtil;
import com.sxj.util.common.ISxjHttpClient;
import com.sxj.util.common.SxjHttpClientImpl;
import com.sxj.util.exception.SystemException;

public class NewNewSendMessage
{
    
    private ISxjHttpClient httpclient;
    
    private String serviceURL = "http://sdk.entinfo.cn:8060/webservice.asmx";
    
    private String sn = "DXX-WSS-11M-06110";
    
    private String pwd = "502503";
    
    private String ext = "";
    
    private String stime = "";
    
    private String rrid = "";
    
    private NewNewSendMessage()
    {
        if (httpclient == null)
        {
            httpclient = new SxjHttpClientImpl();
        }
        
    }
    
    public static NewNewSendMessage getInstance()
    {
        return new NewNewSendMessage();
    }
    
    public String sendMessage(String mobiles, String content)
            throws SystemException, UnsupportedEncodingException
    {
        String result = "";
        content = URLEncoder.encode(content, "utf8");
        pwd = EncryptUtil.md5(pwd).toUpperCase();
        String soapAction = "http://tempuri.org/mdSmsSend_u";
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        xml += "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
        xml += "<soap:Body>";
        xml += "<mdSmsSend_u xmlns=\"http://tempuri.org/\">";
        xml += "<sn>" + sn + "</sn>";
        xml += "<pwd>" + pwd + "</pwd>";
        xml += "<mobile>" + mobiles + "</mobile>";
        xml += "<content>" + content + "</content>";
        xml += "<ext>" + ext + "</ext>";
        xml += "<stime>" + stime + "</stime>";
        xml += "<rrid>" + rrid + "</rrid>";
        xml += "</mdSmsSend_u>";
        xml += "</soap:Body>";
        xml += "</soap:Envelope>";
        URL url;
        try
        {
            url = new URL(serviceURL);
            
            URLConnection connection = url.openConnection();
            HttpURLConnection httpconn = (HttpURLConnection) connection;
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            bout.write(xml.getBytes("GBK"));
            //如果您的系统是utf-8,这里请改成bout.write(xml.getBytes("GBK"));
            
            byte[] b = bout.toByteArray();
            httpconn.setRequestProperty("Content-Length",
                    String.valueOf(b.length));
            httpconn.setRequestProperty("Content-Type",
                    "text/xml; charset=gb2312");
            httpconn.setRequestProperty("SOAPAction", soapAction);
            httpconn.setRequestMethod("POST");
            httpconn.setDoInput(true);
            httpconn.setDoOutput(true);
            
            OutputStream out = httpconn.getOutputStream();
            out.write(b);
            out.close();
            
            InputStreamReader isr = new InputStreamReader(
                    httpconn.getInputStream());
            BufferedReader in = new BufferedReader(isr);
            String inputLine;
            while (null != (inputLine = in.readLine()))
            {
                Pattern pattern = Pattern.compile("<mdSmsSend_uResult>(.*)</mdSmsSend_uResult>");
                Matcher matcher = pattern.matcher(inputLine);
                while (matcher.find())
                {
                    result = matcher.group(1);
                }
            }
            return result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
        
    }
    
    /*
     * 方法名称：mdSmsSend_u 
     * 功    能：发送短信 ,传多个手机号就是群发，一个手机号就是单条提交
     * 参    数：mobile,content,ext,stime,rrid(手机号，URL_UT8编码内容，扩展码，定时时间，唯一标识)
     * 返 回 值：唯一标识，如果不填写rrid将返回系统生成的
     */
    public String sendMessage(String[] mobiles, String content, String ext,
            String stime, String rrid)
    {
        String result = "";
        String soapAction = "http://tempuri.org/mdSmsSend_u";
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        xml += "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
        xml += "<soap:Body>";
        xml += "<mdSmsSend_u xmlns=\"http://tempuri.org/\">";
        xml += "<sn>" + sn + "</sn>";
        xml += "<pwd>" + pwd + "</pwd>";
        xml += "<mobile>" + mobiles[0] + "</mobile>";
        xml += "<content>" + content + "</content>";
        xml += "<ext>" + ext + "</ext>";
        xml += "<stime>" + stime + "</stime>";
        xml += "<rrid>" + rrid + "</rrid>";
        xml += "</mdSmsSend_u>";
        xml += "</soap:Body>";
        xml += "</soap:Envelope>";
        //System.out.println(sn+"  "+pwd+"  "+mobile+"  "+content);
        URL url;
        try
        {
            url = new URL(serviceURL);
            
            URLConnection connection = url.openConnection();
            HttpURLConnection httpconn = (HttpURLConnection) connection;
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            bout.write(xml.getBytes("GBK"));
            //如果您的系统是utf-8,这里请改成bout.write(xml.getBytes("GBK"));
            
            byte[] b = bout.toByteArray();
            httpconn.setRequestProperty("Content-Length",
                    String.valueOf(b.length));
            httpconn.setRequestProperty("Content-Type",
                    "text/xml; charset=gb2312");
            httpconn.setRequestProperty("SOAPAction", soapAction);
            httpconn.setRequestMethod("POST");
            httpconn.setDoInput(true);
            httpconn.setDoOutput(true);
            
            OutputStream out = httpconn.getOutputStream();
            out.write(b);
            out.close();
            
            InputStreamReader isr = new InputStreamReader(
                    httpconn.getInputStream());
            BufferedReader in = new BufferedReader(isr);
            String inputLine;
            while (null != (inputLine = in.readLine()))
            {
                Pattern pattern = Pattern.compile("<mdSmsSend_uResult>(.*)</mdSmsSend_uResult>");
                Matcher matcher = pattern.matcher(inputLine);
                while (matcher.find())
                {
                    result = matcher.group(1);
                }
            }
            return result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }
    
    public static void main(String[] args) throws SystemException,
            UnsupportedEncodingException
    {
        SxjHttpClientImpl httpclient = new SxjHttpClientImpl();
        httpclient.setCharset("UTF-8");
        NewNewSendMessage me = NewNewSendMessage.getInstance();
        String[] dfdf = { "13852295723", "18551626910", "13852295723" };
        me.sendMessage("13852295723", "私享家验证码3333");
    }
}
