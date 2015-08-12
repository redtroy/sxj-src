package com.sxj.supervisor.manage.controller.gov;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.entity.gov.GovEntity;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.service.gov.IGovService;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/govlist")
public class GovListController extends BaseController
{
    @Autowired
    private IGovService govService;
    
    /**
     * 政务信息列表页面
     * @param entity
     * @return
     */
    @RequestMapping("/query")
    public String queryList(GovEntity entity, ModelMap map) throws WebException
    {
        try
        {
            if (entity != null)
            {
                entity.setPagable(true);
            }
            List<GovEntity> list = govService.queryGovList(entity);
            map.put("list", list);
            map.put("query", entity);
            return "manage/govlist/govlistman";
        }
        catch (Exception e)
        {
            SxjLogger.error("查询政务信息错误", e, this.getClass());
            throw new WebException("查询政务信息错误");
        }
    }
    
    /**
     * 跳转到政务信息添加页面
     * @param entity
     * @return
     */
    @RequestMapping("/pub")
    public String pub(ModelMap map) throws WebException
    {
        return "manage/govlist/govinfopub";
    }
    
    @RequestMapping("edit")
    public @ResponseBody Map<String, String> addGov(GovEntity entity)
            throws WebException
    {
        entity.setCreateDate(new Date());
        try
        {
            govService.addGov(entity);
            Map<String, String> map = new HashMap<String, String>();
            map.put("isOK", "ok");
            return map;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            SxjLogger.error("查询政务信息错误", e, this.getClass());
            throw new WebException("查询政务发信息错误");
        }
    }
}
