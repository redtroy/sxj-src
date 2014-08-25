package com.sxj.supervisor.manage.controller;

import java.beans.PropertyEditorSupport;

public class EnumPropertyEditorSupport<E extends Enum<E>> extends
        PropertyEditorSupport
{
    private Class<E> clzz;
    
    public EnumPropertyEditorSupport(Class<E> clzz)
    {
        super();
        this.clzz = clzz;
    }
    
    @Override
    public void setAsText(String text) throws IllegalArgumentException
    {
        if (text != null && text.length() > 0)
            setValue(Enum.valueOf(clzz, text));
    }
    
}
