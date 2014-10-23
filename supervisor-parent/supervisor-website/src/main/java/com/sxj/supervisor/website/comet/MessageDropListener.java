package com.sxj.supervisor.website.comet;

import org.comet4j.core.event.DropEvent;
import org.comet4j.core.listener.DropListener;

public class MessageDropListener extends DropListener
{
    
    @Override
    public boolean handleEvent(DropEvent arg0)
    {
        MessageThread.getInstance().descrCounter();
        return true;
    }
    
}
