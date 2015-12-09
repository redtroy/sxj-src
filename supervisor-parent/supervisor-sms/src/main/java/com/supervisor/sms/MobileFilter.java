package com.supervisor.sms;

public interface MobileFilter
{
    
    public abstract String[] filter(Sender sender, String... mobiles)
            throws MobileException;
            
    public abstract boolean filter(Sender sender, String mobile);
}
