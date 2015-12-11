package com.supervisor.sms.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.supervisor.sms.MobileException;
import com.supervisor.sms.MobileFilter;
import com.supervisor.sms.Sender;

public class CompositeFilter implements MobileFilter
{
    List<MobileFilter> chain = new ArrayList<>();
    
    @Override
    public String[] filter(Sender sender, String... mobiles)
            throws MobileException
    {
        Set<String> s = new HashSet<>();
        for (MobileFilter filter : chain)
        {
            String[] result = filter.filter(sender, mobiles);
            s.addAll(Arrays.asList(result));
        }
        return s.toArray(new String[s.size()]);
    }
    
    public void addMobileFilter(MobileFilter filter)
    {
        chain.add(filter);
    }
    
    @Override
    public boolean filter(Sender sender, String mobile)
    {
        boolean result = true;
        for (MobileFilter filter : chain)
        {
            result = result && filter.filter(sender, mobile);
        }
        return result;
    }
    
}
