package com.supervisor.sms.impl.c123;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.supervisor.sms.HttpSmsSender;
import com.supervisor.sms.MobileException;
import com.supervisor.sms.SendStatus;
import com.sxj.spring.modules.util.StringUtils;

public class C123Sender extends HttpSmsSender
{
    private String sOpenUrl;
    
    private String sDataUrl;
    
    private static String sendUrl;
    
    private static String batchUrl;
    
    private static int nCgid = 0;
    
    private static int nCsid = 0;
    
    // 接口帐号
    private String account;
    
    // 接口密钥
    private String authkey;
    
    // 通道组编号
    private String cgid;
    
    // 默认使用的签名编号(未指定签名编号时传此值到服务器)
    private String csid;
    
    @Override
    public void report(String sendId) throws MobileException
    {
        // TODO Auto-generated method stub
        
    }
    
    private String sendBatch(String[] mobileNos, String content)
    {
        StringBuilder sb = new StringBuilder();
        for (String mobileNo : mobileNos)
            sb.append(mobileNo).append(",");
        sb.deleteCharAt(sb.length() - 1);
        Map<String, String> params = new HashMap<>();
        params.put("action", "sendBatch");
        params.put("ac", account);
        params.put("authkey", authkey);
        params.put("m", sb.toString());
        params.put("c", content);
        if ((Integer.parseInt(cgid) > 0) || (nCgid > 0))
            params.put("cgid",
                    String.valueOf(Integer.parseInt(cgid) > 0 ? cgid : nCgid));
        if ((Integer.parseInt(csid) > 0) || (nCsid > 0))
            params.put("csid",
                    String.valueOf(Integer.parseInt(csid) > 0 ? csid : nCsid));
        // params.put("type", type);
        //params.put("extno", "");
        try
        {
            String response = getHttpClient().post(sOpenUrl, params);
            System.out.println(response);
            return response;
        }
        catch (IOException e)
        {
            throw new MobileException("短信发送失败", e);
        }
    }
    
    private String getErrorMsg(String result)
    {
        switch (result)
        {
            case "1":
                return "操作成功";
            case "0":
                return "帐户格式不正确";
            case "-1":
                return "服务器拒绝";
            case "-2":
                return "密钥不正确";
            case "-3":
                return "密钥已锁定";
            case "-4":
                return "参数不正确";
            case "-5":
                return "无此帐户";
            case "-6":
                return "帐户已锁定或已过期";
            case "-7":
                return "帐户未开启接口发送";
            case "-8":
                return "不可使用该通道组";
            case "-9":
                return "帐户余额不足";
            case "-10":
                return "内部错误";
            case "-11":
                return "扣费失败";
        }
        return "未知错误";
    }
    
    protected SendStatus buildSendStatus(String xml)
    {
        StringReader read = new StringReader(xml);
        
        InputSource source = new InputSource(read);
        
        SAXBuilder builder = new SAXBuilder();
        try
        {
            List<SendStatus> listSendState = new ArrayList<>();
            Document doc = builder.build(source);
            Element root = doc.getRootElement();
            if (root != null)
            {
                Attribute result = root.getAttribute("result");
                
                if (result != null)
                {
                    if (parseInt(result.getValue(), -102) > 0)
                    {
                        List<Element> listItem = root.getChildren("Item");
                        for (Element item : listItem)
                        {
                            SendStatus t = new SendStatus();
                            //                            t.setId(parseInt(item.getAttributeValue("id"), 0));
                            t.setMessageId(item.getAttributeValue("msgid"));
                            t.setMessageCount(parseInt(
                                    item.getAttributeValue("total"), 0));
                            //                            t.setMobile(item.getAttributeValue("mobile"));
                            //                            t.setStatus(parseInt(
                            //                                    item.getAttributeValue("result"), 0));
                            //                            t.setDetail(item.getAttributeValue("return"));
                            listSendState.add(t);
                        }
                    }
                    
                    if (listSendState.size() == 1)
                    {
                        SendStatus sendStatus = listSendState.get(0);
                        sendStatus.setMessage(getErrorMsg(result.getValue()));
                        return sendStatus;
                    }
                    else
                    {
                        SendStatus status = new SendStatus();
                        status.setMessage(getErrorMsg(result.getValue()));
                        status.setStatuses(listSendState);
                        return status;
                    }
                    
                }
            }
            return null;
        }
        catch (JDOMException localJDOMException)
        {
        }
        catch (IOException localIOException)
        {
        }
        return null;
    }
    
    public static int parseInt(String str, int def)
    {
        try
        {
            return Integer.parseInt(str);
        }
        catch (Exception e)
        {
        }
        return def;
    }
    
    private String getSendUrl()
    {
        if (StringUtils.isBlank(sendUrl))
            sendUrl = sOpenUrl + "?action=sendOnce";
        return sendUrl;
    }
    
    private String getBatchUrl()
    {
        if (StringUtils.isBlank(batchUrl))
            batchUrl = sOpenUrl + "?action=sendBatch";
        return batchUrl;
    }
    
    public String getsOpenUrl()
    {
        return sOpenUrl;
    }
    
    public void setsOpenUrl(String sOpenUrl)
    {
        this.sOpenUrl = sOpenUrl;
    }
    
    public String getsDataUrl()
    {
        return sDataUrl;
    }
    
    public void setsDataUrl(String sDataUrl)
    {
        this.sDataUrl = sDataUrl;
    }
    
    public String getAccount()
    {
        return account;
    }
    
    public void setAccount(String account)
    {
        this.account = account;
    }
    
    public String getAuthkey()
    {
        return authkey;
    }
    
    public void setAuthkey(String authkey)
    {
        this.authkey = authkey;
    }
    
    public String getCgid()
    {
        return cgid;
    }
    
    public void setCgid(String cgid)
    {
        this.cgid = cgid;
    }
    
    public String getCsid()
    {
        return csid;
    }
    
    public void setCsid(String csid)
    {
        this.csid = csid;
    }
    
    public static void main(String[] args)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 2; i++)
        {
            sb.append("a");
            sb.append("{|}");
        }
        System.out.println(sb.substring(0, sb.length() - 3));
    }
    
    @Override
    protected String sendSingle(String mobileNo, String content)
            throws MobileException
    {
        Map<String, String> params = new HashMap<>();
        params.put("action", "sendOnce");
        params.put("ac", account);
        params.put("authkey", authkey);
        params.put("m", mobileNo);
        params.put("c", content);
        if ((Integer.parseInt(cgid) > 0) || (nCgid > 0))
            params.put("cgid",
                    String.valueOf(Integer.parseInt(cgid) > 0 ? cgid : nCgid));
        if ((Integer.parseInt(csid) > 0) || (nCsid > 0))
            params.put("csid",
                    String.valueOf(Integer.parseInt(csid) > 0 ? csid : nCsid));
        // params.put("type", type);
        //params.put("extno", "");
        try
        {
            String response = getHttpClient().post(getSendUrl(), params);
            System.out.println(response);
            return response;
        }
        catch (IOException e)
        {
            throw new MobileException("短信发送失败", e);
        }
    }
    
    /* (non-Javadoc)
     * @see com.supervisor.sms.Sender#sendBatch(java.lang.String[], java.lang.String, boolean)
     * if oneByOne==false,表示对每个手机分别发送一条短信，多条content必须用{|}进行分割
     * if oneByOne==true,表示对同一条短信发送到不同手机
     */
    @Override
    protected String[] sendBatch(String content, String[] mobileNos,
            boolean oneByOne) throws MobileException
    {
        List<String> result = new ArrayList<>();
        if (oneByOne)
        {
            for (String mobileNo : mobileNos)
            {
                result.add(sendSingle(mobileNo, content));
            }
            return result.toArray(new String[result.size()]);
        }
        else
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mobileNos.length; i++)
            {
                sb.append(content);
                sb.append("{|}");
            }
            result.add(sendBatch(mobileNos, content));
            return result.toArray(new String[result.size()]);
        }
    }
    
}
