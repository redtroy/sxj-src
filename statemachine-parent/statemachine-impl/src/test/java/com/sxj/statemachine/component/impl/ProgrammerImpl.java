package com.sxj.statemachine.component.impl;

import javax.annotation.PostConstruct;

import com.sxj.statemachine.component.PersonImpl;
import com.sxj.statemachine.component.Programmer;

public class ProgrammerImpl extends PersonImpl implements Programmer {
    
    private String lang;
    
    @PostConstruct
    public void postConstruct() {
        setName("Henry");
        lang = "Java";
    }

    @Override
    public String getLanguage() {
        return lang;
    }
}
