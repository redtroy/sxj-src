package com.sxj.statemachine.component.impl;

import com.sxj.statemachine.component.PersonImpl;
import com.sxj.statemachine.component.Student;

public class StudentImpl extends PersonImpl implements Student {
    
    private String school;
    
    @Override
    public void setSchool(String school) {
        this.school = school;
    }

    @Override
    public String getSchool() {
        return school;
    }

}
