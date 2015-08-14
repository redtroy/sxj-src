package com.sxj.supervisor.service.tasks;

import java.util.List;

import com.sxj.supervisor.entity.gather.WindDoorEntity;
import com.sxj.supervisor.model.tasks.WindDoorModel;

public interface IWindDoorService
{
    
    /**
     * 门窗采集
     */
    public void WindDoorGather();
    
    /**
     * 获取信息
     */
    public WindDoorEntity getInfoById(String id);
    
    /**
     * 导入word HTML 模版
     */
    public void insertWordHtml(WindDoorEntity wind);
    
    /**
     * 查询采集列表
     */
    public List<WindDoorEntity> query(WindDoorModel query);
}
