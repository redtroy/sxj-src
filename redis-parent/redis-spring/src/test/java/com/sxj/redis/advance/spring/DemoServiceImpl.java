package com.sxj.redis.advance.spring;

import org.springframework.stereotype.Service;

import com.sxj.redis.advance.spring.annotation.Lock;

@Service
public class DemoServiceImpl
{
    @Lock(timeOut = 10000)
    public void sayHello(String hello)
    {
        System.out.println(hello);
    }
}
