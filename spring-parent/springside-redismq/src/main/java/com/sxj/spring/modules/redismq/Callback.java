package com.sxj.spring.modules.redismq;

public interface Callback {
    public void onMessage(String message);
}
