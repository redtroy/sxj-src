<<<<<<< HEAD
package com.sxj.supervisor.manage.login;

import com.sxj.redis.advance.core.MessageListener;
import com.sxj.redis.advance.topic.RedisTopics;

public class SupervisorManagerMessageListener
{
    private SupervisorManagerShiroRealm realm;
    
    private RedisTopics topics;
    
    public SupervisorManagerMessageListener()
    {
        topics.getTopic("").addListener(new MessageListener<Object>()
        {
            @Override
            public void onMessage(Object msg)
            {
                System.out.println(msg);
            }
        });
    }
    
    public void setRealm(SupervisorManagerShiroRealm realm)
    {
        this.realm = realm;
    }
    
    public void setTopics(RedisTopics topics)
    {
        this.topics = topics;
    }
    
}
=======
package com.sxj.supervisor.manage.login;

import com.sxj.redis.advance.core.MessageListener;
import com.sxj.redis.advance.topic.RedisTopics;

public class SupervisorManagerMessageListener
{
    private SupervisorManagerShiroRealm realm;
    
    private RedisTopics topics;
    
    public SupervisorManagerMessageListener()
    {
        topics.getTopic("").addListener(new MessageListener<Object>()
        {
            @Override
            public void onMessage(Object msg)
            {
                System.out.println(msg);
            }
        });
    }
    
    public void setRealm(SupervisorManagerShiroRealm realm)
    {
        this.realm = realm;
    }
    
    public void setTopics(RedisTopics topics)
    {
        this.topics = topics;
    }
    
}
>>>>>>> cb7e17a58b6eb6b575ffea075e60859951e1c03e
