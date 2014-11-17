package com.sxj.supervisor.manage.comet;

import org.comet4j.core.event.ConnectEvent;
import org.comet4j.core.listener.ConnectListener;

public class MessageConnectListener extends ConnectListener
{
    
    @Override
    public boolean handleEvent(ConnectEvent arg0)
    {
        MessageThread.getInstance().incrCounter();
        return true;
    }
}
