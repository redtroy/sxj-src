package com.sxj.supervisor.website.comet.record;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.comet4j.core.CometConnection;
import org.comet4j.core.CometEngine;

import com.sxj.cache.manager.HierarchicalCacheManager;
import com.sxj.supervisor.website.comet.MessageChannel;
import com.sxj.supervisor.website.comet.MessageThread;
import com.sxj.util.common.StringUtils;
import com.sxj.util.logger.SxjLogger;

public class RecordThread extends MessageThread
{
    
    private List<String> oldMessage;
    
    private static int period = 2;
    
    private static int delay = 3;
    
    private static volatile RecordThread _self = null;
    
    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    
    private RecordThread(CometEngine engine)
    {
        super(engine);
        // TODO Auto-generated constructor stub
    }
    
    public static RecordThread newInstance(CometEngine engine)
    {
        if (_self == null)
        {
            _self = new RecordThread(engine);
            
            return _self;
        }
        return _self;
    }
    
    public static RecordThread getInstance()
    {
        if (_self == null)
            throw new RuntimeException("No instance exists!!!");
        return _self;
    }
    
    public void schedule()
    {
        scheduler.scheduleAtFixedRate(_self,
                getDelay(),
                getPeriod(),
                TimeUnit.SECONDS);
    }
    
    public void shutdown()
    {
        descrCounter();
        //        if (getCounter().get() <= 0)
        //            scheduler.shutdown();
    }
    
    @Override
    public void run()
    {
        // 获取消息内容
        if (getCounter().get() > 0)
        {
            if (oldMessage == null)
            {
                oldMessage = new ArrayList<String>();
            }
            List<String> messageList = null;
            String key = "record_push_message_a_";
            if (StringUtils.isNotEmpty(getParam()))
            {
                key = key + getParam();
            }
            Object cache = HierarchicalCacheManager.get(2, "comet_record", key);
            if (cache instanceof ArrayList)
            {
                messageList = (List<String>) cache;
            }
            if (messageList != null)
            {
                // System.out.println("--------------------" +
                // messageList.size());
            }
            List<CometConnection> connections = getEngine().getConnections();
            for (CometConnection connection : connections)
            {
                System.out.println(connection.getRequest().getSession().getId());
            }
            SxjLogger.info("key=" + key, this.getClass());
            SxjLogger.info("##############" + messageList, this.getClass());
            // System.out.println("##############" + messageList);
            if (messageList != null)
            {
                for (Iterator<String> iterator = messageList.iterator(); iterator.hasNext();)
                {
                    String message = iterator.next();
                    SxjLogger.info("------------" + message, this.getClass());
                    boolean flag = oldMessage.contains(message + key);
                    SxjLogger.info("=============" + flag, this.getClass());
                    // 开始发送
                    if (!flag)
                    {
                        SxjLogger.info("+++++++++++++++" + flag,
                                this.getClass());
                        getEngine().sendToAll(MessageChannel.RECORD_MESSAGE_A,
                                message);
                        oldMessage.add(message + key);
                    }
                    // iterator.remove();
                    // HierarchicalCacheManager.set(2, "comet_record",
                    // key, messageList);
                }
                // HierarchicalCacheManager.evict(2, "comet_record",
                // "record_id");
            }
        }
        
    }
    
    public int getPeriod()
    {
        return period;
    }
    
    public void setPeriod(int period)
    {
        this.period = period;
    }
    
    public int getDelay()
    {
        return delay;
    }
    
    public void setDelay(int delay)
    {
        this.delay = delay;
    }
    
}
