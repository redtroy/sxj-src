package com.sxj.supervisor.service.sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;

public class SMSTest
{
    
    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }
    
    @After
    public void tearDown() throws Exception
    {
    }
    
    @Test
    public void test() throws IOException
    {
        String content = "私享家验证码11111111";
        String sign = "私享家验证码";
        
        // 创建StringBuffer对象用来操作字符串
        StringBuffer sb = new StringBuffer(
                "http://sms.1xinxi.cn/asmx/smsservice.aspx?");
        
        // 向StringBuffer追加用户名
        sb.append("name=jssxj");
        
        // 向StringBuffer追加密码（登陆网页版，在管理中心--基本资料--接口密码，是28位的）
        sb.append("&pwd=CEE4D6CC34577FB24D1726F8AFEB");
        
        // 向StringBuffer追加手机号码
        sb.append("&mobile=18915909935");
        
        // 向StringBuffer追加消息内容转URL标准码
        sb.append("&content=" + URLEncoder.encode(content));
        
        //追加发送时间，可为空，为空为及时发送
        sb.append("&stime=");
        
        //加签名
        sb.append("&sign=" + URLEncoder.encode(sign));
        
        //type为固定值pt  extno为扩展码，必须为数字 可为空
        sb.append("&type=pt&extno=");
        // 创建url对象
        URL url = new URL(sb.toString());
        
        // 打开url连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        
        // 设置url请求方式 ‘get’ 或者 ‘post’
        connection.setRequestMethod("POST");
        
        // 发送
        BufferedReader in = new BufferedReader(new InputStreamReader(
                url.openStream()));
        
        // 返回发送结果
        String inputline = in.readLine();
        
        // 返回结果为‘0，20140009090990,1，提交成功’ 发送成功   具体见说明文档
        System.out.println(inputline);
    }
    
}
