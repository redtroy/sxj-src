package com.sxj.supervisor.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.supervisor.sms.ChannelManager;
import com.supervisor.sms.Sender;
import com.supervisor.sms.impl.xinxi1.Xinxi1Sender;

@Configuration
public class SmsConfiguration
{
    private static ChannelManager channelManager;
    
    {
        if (channelManager == null)
            channelManager = ChannelManager
                    .getInstance("classpath:config/sms.properties");
    }
    
    @Bean(name = "xinxi1Sender")
    public Sender getXinxi1Sender()
    {
        return channelManager.getSender(Xinxi1Sender.class.getName());
    }
}
