package com.sxj.supervisor.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.supervisor.sms.ChannelManager;
import com.supervisor.sms.HttpSmsSender;
import com.supervisor.sms.Sender;
import com.supervisor.sms.impl.WhiteListFilter;
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
    
    /**仅发送白名单
     * @return
     */
    @Bean(name = "xinxi1Sender")
    public Sender getXinxi1Sender()
    {
        Sender sender = channelManager.getSender(Xinxi1Sender.class.getName());
        ((HttpSmsSender) sender).setMobileFilter(new WhiteListFilter());
        return sender;
    }
}
