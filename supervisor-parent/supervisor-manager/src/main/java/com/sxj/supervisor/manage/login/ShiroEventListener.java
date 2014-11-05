package com.sxj.supervisor.manage.login;

import java.util.List;

import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import com.sxj.redis.advance.RedisCollections;
import com.sxj.redis.advance.core.MessageListener;
import com.sxj.redis.advance.core.RTopic;
import com.sxj.redis.advance.topic.RedisTopics;
import com.sxj.spring.modules.security.shiro.ShiroRedisCacheManager;
import com.sxj.util.Constraints;

public class ShiroEventListener implements BeanFactoryPostProcessor
{
    private static RedisTopics topics;
    
    private static RedisCollections collections;
    
    private static ShiroRedisCacheManager cacheManager;
    
    @Override
    public void postProcessBeanFactory(
            final ConfigurableListableBeanFactory beanFactory)
            throws BeansException
    {
        topics = beanFactory.getBean(RedisTopics.class);
        collections = beanFactory.getBean(RedisCollections.class);
        cacheManager = beanFactory.getBean(SupervisorShiroRedisCacheManager.class);
        RTopic<Object> topic = topics.getTopic(Constraints.MANAGER_CHANNEL_NAME);
        topic.addListener(new MessageListener<Object>()
        {
            
            @Override
            public void onMessage(Object msg)
            {
                List<PrincipalCollection> principals = (List<PrincipalCollection>) collections.getMap(Constraints.SHIRO_MAP_KEY)
                        .get((String) msg);
                Cache<Object, Object> cache = cacheManager.getCache(Constraints.MANAGER_CACHE_NAME);
                //                Object object = cache.get(principals);
                //                cache.put(principals, new SimpleAuthorizationInfo());
                //
                for (PrincipalCollection principal : principals)
                    cache.put(principal, new SimpleAuthorizationInfo());
                System.out.println(principals.size());
            }
        });
        
    }
}
