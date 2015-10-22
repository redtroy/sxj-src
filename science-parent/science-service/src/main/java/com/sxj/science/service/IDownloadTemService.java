package com.sxj.science.service;

import java.util.List;

import com.sxj.science.entity.export.WindowTypeEntity;

public interface IDownloadTemService
{
    
    public List<WindowTypeEntity> queryWindowType(WindowTypeEntity query);
    
    public List<WindowTypeEntity> searchWindowType(WindowTypeEntity query);
    
    public List<WindowTypeEntity> getWindowTypeByIds(String idArr);
    
    public WindowTypeEntity getType(String id);
    
    public List<WindowTypeEntity> openQueryWindowType(WindowTypeEntity query);
    
    public void addWindowType(WindowTypeEntity query);
    
    public void delCountTem(String id);

    public List<WindowTypeEntity> autoWindowType(WindowTypeEntity query);

    public WindowTypeEntity getWindowTypeById(String id);

    public void updateWindowType(WindowTypeEntity windowType);
    
}
