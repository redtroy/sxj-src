package com.sxj.supervisor.service.statemachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.member.IMemberDao;

@Service
public class DemoService
{
    @Autowired
    IMemberDao dao;
    
    @Transactional(propagation = Propagation.REQUIRED)
    public void doSomething()
    {
        
    }
}
