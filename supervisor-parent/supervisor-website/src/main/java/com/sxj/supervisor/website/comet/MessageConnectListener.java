package com.sxj.supervisor.website.comet;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;

import org.comet4j.core.CometEngine;
import org.comet4j.core.event.ConnectEvent;
import org.comet4j.core.listener.ConnectListener;

import com.sxj.supervisor.website.comet.record.RecordThread;

public class MessageConnectListener extends ConnectListener
{
    
    @Override
    public boolean handleEvent(ConnectEvent arg0)
    {
        RecordThread.getInstance().incrCounter();
        return true;
    }
}
