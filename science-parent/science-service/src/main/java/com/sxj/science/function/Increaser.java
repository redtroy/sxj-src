package com.sxj.science.function;

public class Increaser
{
    private int number = 0;
    
    public int increase()
    {
        return ++number;
    }
    
    public void reset()
    {
        number = 0;
    }
    
}
