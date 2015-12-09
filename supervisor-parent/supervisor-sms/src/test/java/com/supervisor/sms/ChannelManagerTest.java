package com.supervisor.sms;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.supervisor.sms.impl.c123.C123Sender;
import com.supervisor.sms.impl.xinxi1.Xinxi1Sender;
import com.supervisor.sms.impl.zucp.ZucpSender;
import com.sxj.freemarker.FreemarkerTool;
import com.sxj.spring.modules.util.Identities;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class ChannelManagerTest
{
    ChannelManager manager;
    
    String content = "(平台注册验证码，10分钟有效)";
    
    @Before
    public void before()
    {
        manager = ChannelManager.getInstance("classpath:/sms.properties");
    }
    
    public void testXinxi1SendSingle()
    {
        //        System.out.println(Xinxi1Sender.class.getName());
        String vCode = Identities.randomNumber(6);
        Sender sender = manager.getSender(Xinxi1Sender.class.getName());
        Map<String, String> model = new HashMap<>();
        model.put("vCode", vCode);
        SendStatus status = sender.send("18118808098",
                FreemarkerTool.processTemplateToString("verification-message",
                        model));
        System.out.println(status);
        //        status = sender.sendBatch(new String[] { "18118808098", "13852295723" },
        //                Xinxi1Sender.class.getName() + "-----1" + content,
        //                true);
        //        System.out.println(status);
        //        status = sender.sendBatch(new String[] { "18118808098", "13852295723" },
        //                Xinxi1Sender.class.getName() + "-----2" + content,
        //                false);
        //        System.out.println(status);
    }
    
    @Test
    public void testC123Sender()
    {
        String vCode = Identities.randomNumber(6);
        Sender sender = manager.getSender(C123Sender.class.getName());
        Map<String, String> model = new HashMap<>();
        model.put("vCode", vCode);
        SendStatus status = sender.send("18118808098",
                FreemarkerTool.processTemplateToString("verification-message",
                        model));
        System.out.println(status);
        //        Sender sender = manager.getSender(C123Sender.class.getName());
        //        SendStatus status = sender.send("18118808098", "小熊是笨蛋");
        //        System.out.println(status);
        //        status = sender.sendBatch(new String[] { "18118808098", "13584006660" },
        //                "小熊是笨蛋",
        //                true);
        //        System.out.println(status);
        //        status = sender.sendBatch(new String[] { "18118808098", "13584006660" },
        //                "小熊是笨蛋",
        //                false);
        //        System.out.println(status);
    }
    
    public void testZucpSender()
    {
        Sender sender = manager.getSender(ZucpSender.class.getName());
        SendStatus status = sender.send("18118808098", "***你好【天天赚】");
        System.out.println(status);
        //        status = sender.sendBatch(new String[] { "18118808098", "13584006660" },
        //                "小熊是笨蛋【天天赚】",
        //                true);
        //        System.out.println(status);
        //        status = sender.sendBatch(new String[] { "18118808098", "13584006660" },
        //                "小熊是笨蛋【天天赚】",
        //                false);
        //        System.out.println(status);
    }
    
}
