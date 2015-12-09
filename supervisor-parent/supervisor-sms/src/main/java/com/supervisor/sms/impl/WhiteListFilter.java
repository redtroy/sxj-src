package com.supervisor.sms.impl;

import java.util.ArrayList;
import java.util.List;

import com.supervisor.sms.ChannelManager;
import com.supervisor.sms.MobileException;
import com.supervisor.sms.MobileFilter;
import com.supervisor.sms.Sender;

public class WhiteListFilter implements MobileFilter
{
    
    @Override
    public String[] filter(Sender sender, String... mobiles)
            throws MobileException
    {
        List<String> m = new ArrayList<>();
        for (String mobile : mobiles)
        {
            if (ChannelManager.isWhite(sender, mobile))
                m.add(mobile);
        }
        return m.toArray(new String[m.size()]);
    }
    
    @Override
    public boolean filter(Sender sender, String mobile)
    {
        return ChannelManager.isWhite(sender, mobile);
    }
    
}
