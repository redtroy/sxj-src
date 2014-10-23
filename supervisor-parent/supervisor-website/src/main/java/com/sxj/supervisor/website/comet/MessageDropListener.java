package com.sxj.supervisor.website.comet;

import org.comet4j.core.event.DropEvent;
import org.comet4j.core.listener.DropListener;

import com.sxj.supervisor.website.comet.record.RecordThread;

public class MessageDropListener extends DropListener
{
    
    @Override
    public boolean handleEvent(DropEvent arg0)
    {
        RecordThread.getInstance().descrCounter();
        return true;
    }
    
}
