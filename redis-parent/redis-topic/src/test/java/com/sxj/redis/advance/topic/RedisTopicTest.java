package com.sxj.redis.advance.topic;

import java.util.concurrent.CountDownLatch;

import org.junit.Assert;
import org.junit.Test;

import com.sxj.redis.advance.core.MessageListener;
import com.sxj.redis.advance.core.RTopic;

public class RedisTopicTest
{
    
    public static class Message
    {
        
        private String name;
        
        public Message()
        {
        }
        
        public Message(String name)
        {
            this.name = name;
        }
        
        public String getName()
        {
            return name;
        }
        
        @Override
        public boolean equals(Object obj)
        {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Message other = (Message) obj;
            if (name == null)
            {
                if (other.name != null)
                    return false;
            }
            else if (!name.equals(other.name))
                return false;
            return true;
        }
        
    }
    
    public void testUnsubscribe() throws InterruptedException
    {
        final CountDownLatch messageRecieved = new CountDownLatch(1);
        
        RedisTopics redis = RedisTopics.create();
        RTopic<Message> topic1 = redis.getTopic("topic1");
        int listenerId = topic1.addListener(new MessageListener<Message>()
        {
            @Override
            public void onMessage(Message msg)
            {
                Assert.fail();
            }
        });
        topic1.addListener(new MessageListener<Message>()
        {
            @Override
            public void onMessage(Message msg)
            {
                Assert.assertEquals(new Message("123"), msg);
                messageRecieved.countDown();
            }
        });
        topic1.removeListener(listenerId);
        
        topic1 = redis.getTopic("topic1");
        topic1.publish(new Message("123"));
        
        messageRecieved.await();
        
        redis.shutdown();
    }
    
    public void testLazyUnsubscribe() throws InterruptedException
    {
        final CountDownLatch messageRecieved = new CountDownLatch(1);
        
        RedisTopics redis = RedisTopics.create();
        RTopic<Message> topic1 = redis.getTopic("topic");
        int listenerId = topic1.addListener(new MessageListener<Message>()
        {
            @Override
            public void onMessage(Message msg)
            {
                Assert.fail();
            }
        });
        Thread.sleep(1000);
        topic1.removeListener(listenerId);
        Thread.sleep(1000);
        
        RedisTopics redis2 = RedisTopics.create();
        RTopic<Message> topic2 = redis2.getTopic("topic");
        topic2.addListener(new MessageListener<Message>()
        {
            @Override
            public void onMessage(Message msg)
            {
                Assert.assertEquals(new Message("123"), msg);
                messageRecieved.countDown();
            }
        });
        topic2.publish(new Message("123"));
        
        messageRecieved.await();
        
        redis.shutdown();
        redis2.shutdown();
    }
    
    @Test
    public void test() throws InterruptedException
    {
        
        RedisTopics redis = RedisTopics.create();
        RTopic<Message> topic1 = redis.getTopic("topic");
        topic1.addListener(new MessageListener<Message>()
        {
            @Override
            public void onMessage(Message msg)
            {
                System.out.println("topic1" + msg.getName());
            }
        });
        
        RedisTopics redis2 = RedisTopics.create();
        RTopic<Message> topic2 = redis2.getTopic("topic");
        topic2.addListener(new MessageListener<Message>()
        {
            @Override
            public void onMessage(Message msg)
            {
                System.out.println("topic2" + msg.getName());
            }
        });
        topic2.publish(new Message("测试消息"));
        while (true)
        {
            // System.out.println();
        }
        //        redis.shutdown();
        //        redis2.shutdown();
    }
    
    public void testListenerRemove() throws InterruptedException
    {
        RedisTopics redis = RedisTopics.create();
        RTopic<Message> topic1 = redis.getTopic("topic");
        int id = topic1.addListener(new MessageListener<Message>()
        {
            @Override
            public void onMessage(Message msg)
            {
                Assert.fail();
            }
        });
        
        RedisTopics redis2 = RedisTopics.create();
        RTopic<Message> topic2 = redis2.getTopic("topic");
        topic1.removeListener(id);
        topic2.publish(new Message("123"));
        
        Thread.sleep(1000);
        
        redis.shutdown();
        redis2.shutdown();
    }
    
}
