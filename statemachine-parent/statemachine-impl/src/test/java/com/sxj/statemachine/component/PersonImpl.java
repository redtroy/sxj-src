package com.sxj.statemachine.component;

public class PersonImpl implements Person {
    private String name;
    
    protected PersonImpl() {
    }
    
    protected PersonImpl(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
