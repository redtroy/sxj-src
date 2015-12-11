package com.supervisor.sms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.supervisor.sms.impl.BlackListFilter;
import com.supervisor.sms.impl.CompositeFilter;
import com.supervisor.sms.impl.WhiteListFilter;

public class ChannelManager
{
    //    private static String config = "classpath:/sms.properties";
    
    private static final String PREFIX = "com.sxj.sms.sender";
    
    private static Map<String, Sender> senders = new HashMap<>();
    
    private static Map<String, List<String>> whiteList = new HashMap<>();
    
    private static Map<String, List<String>> blackList = new HashMap<>();
    
    private static final String ALL = "*";
    
    private static ChannelManager manager;
    
    private ChannelManager()
    {
        super();
    }
    
    public static ChannelManager getInstance(String config)
    {
        if (manager != null)
            return manager;
        try
        {
            Properties properties = new Properties();
            properties.load(ClassLoaderUtil.getResourceAsStream(config));
            Enumeration<Object> keys = properties.keys();
            Sender sender = null;
            while (keys.hasMoreElements())
            {
                String key = (String) keys.nextElement();
                String name = key.substring(0, key.lastIndexOf("."));
                String param = key.substring(key.lastIndexOf(".") + 1);
                if (param.equals("whitelist"))
                {
                    List<String> list = whiteList.get(name);
                    if (list == null)
                    {
                        list = new ArrayList<>();
                        whiteList.put(name, list);
                    }
                    if (StringUtils.isNotEmpty(properties.getProperty(key)))
                    {
                        list.addAll(Arrays.asList(
                                properties.getProperty(key).split(",")));
                        //                        list.add(properties.getProperty(key));
                    }
                    continue;
                }
                if (param.equals("blacklist"))
                {
                    List<String> list = blackList.get(name);
                    if (list == null)
                    {
                        list = new ArrayList<>();
                        blackList.put(name, list);
                    }
                    if (StringUtils.isNotEmpty(properties.getProperty(key)))
                    {
                        list.addAll(Arrays.asList(
                                properties.getProperty(key).split(",")));
                    }
                    continue;
                }
                if (senders.get(name) == null)
                {
                    sender = (Sender) Class.forName(name).newInstance();
                    if (sender instanceof HttpSmsSender)
                    {
                        CompositeFilter composite = initCompositeFilter();
                        ((HttpSmsSender) sender).setMobileFilter(composite);
                    }
                }
                else
                    sender = (Sender) senders.get(name);
                Reflections.setFieldValue(sender, param, properties.get(key));
                senders.put(name, sender);
            }
            System.out.println(senders);
            
        }
        catch (IOException | InstantiationException | IllegalAccessException
                | ClassNotFoundException e)
        {
            throw new MobileException(e);
        }
        return new ChannelManager();
    }
    
    private static CompositeFilter initCompositeFilter()
    {
        MobileFilter white = new WhiteListFilter();
        MobileFilter black = new BlackListFilter();
        CompositeFilter composite = new CompositeFilter();
        composite.addMobileFilter(white);
        composite.addMobileFilter(black);
        return composite;
    }
    
    public Sender getSender(String name)
    {
        return senders.get(name);
    }
    
    private static boolean isEmptyList(List list)
    {
        return list == null || list.size() < 1;
    }
    
    public static boolean isWhite(Sender sender, String mobile)
    {
        List<String> whites = whiteList.get(ALL);
        List<String> senderWhites = whiteList.get(sender.getClass().getName());
        return ((isEmptyList(whites)) ? true : whites.contains(mobile))
                && (isEmptyList(senderWhites) ? true
                        : senderWhites.contains(mobile));
    }
    
    public static boolean isBlack(Sender sender, String mobile)
    {
        List<String> blacks = blackList.get(ALL);
        List<String> senderBlacks = blackList.get(sender.getClass().getName());
        return ((isEmptyList(blacks)) ? false : blacks.contains(mobile))
                || (isEmptyList(senderBlacks) ? false
                        : senderBlacks.contains(mobile));
    }
    
    public static void main(String... args)
    {
        //        SendStatus sendBatch = ChannelManager.getInstance()
        //                .getSender("com.supervisor.sms.impl.xinxi1.Xinxi1Sender")
        //                .sendBatch(new String[] { "18118808098", "13852295723" },
        //                        "贵公司有份合同号CT1的合同信息待确认，请登录私享家绿色门窗平台查看详情！",
        //                        true);
        //        SendStatus sendBatch = ChannelManager.getInstance()
        //                .getSender("com.supervisor.sms.impl.c123.C123Sender")
        //                .send("18118808098", "aaaaaa(平台注册验证码，10分钟有效)");
        //        SendStatus sendBatch = ChannelManager.getInstance()
        //                .getSender("com.supervisor.sms.impl.c123.C123Sender")
        //                .sendBatch(new String[] { "18118808098", "13852295723" },
        //                        "bbbbbb(平台注册验证码，10分钟有效)",
        //                        false);
        SendStatus sendBatch = ChannelManager
                .getInstance("classpath:/sms.properties")
                .getSender("com.supervisor.sms.impl.zucp.ZucpSender")
                .sendBatch(new String[] { "18118808098", "13852295723" },
                        "aaaaaa(平台注册验证码，10分钟有效)",
                        false);
        System.out.println(sendBatch.toString());
        
    }
}
