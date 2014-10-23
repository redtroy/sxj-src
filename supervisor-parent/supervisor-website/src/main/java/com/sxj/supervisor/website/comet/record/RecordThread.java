package com.sxj.supervisor.website.comet.record;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.comet4j.core.CometContext;
import org.comet4j.core.CometEngine;

import com.sxj.cache.manager.HierarchicalCacheManager;
import com.sxj.supervisor.website.comet.MessageThread;
import com.sxj.util.logger.SxjLogger;

public class RecordThread extends MessageThread
{
    
    private static int period = 2;
    
    private static int delay = 3;
    
    private static volatile RecordThread _self = null;
    
    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    
    public static final String REDIS_KEY_PREFIX = "record_push_message_";
    
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
        List<String> channels = CometContext.getInstance().getAppModules();
        if (getCounter().get() > 0 && channels.size() > 0)
        {
            for (String channel : channels)
            {
                String key = REDIS_KEY_PREFIX + channel;
                List<String> cache = (List<String>) HierarchicalCacheManager.get(2,
                        "comet_record",
                        key);
                SxjLogger.debug("Sending Message to Comet Client:" + cache,
                        getClass());
                if (cache != null)
                    getEngine().sendToAll(channel, cache);
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
    
    public static void main(String... strings)
    {
        
        Set<String> set = new HashSet<String>();
        set.add("1");
        set.add("1");
        System.out.println(set.size());
    }
}
