package com.sxj.supervisor.manage.controller.projectmessage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.entity.gather.WindDoorEntity;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.tasks.WindDoorModel;
import com.sxj.supervisor.service.tasks.IWindDoorService;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@RequestMapping("projectMessage")
@Controller
public class ProjectMessageManagerController extends BaseController
{
    @Autowired
    private IWindDoorService windService;
    
    @RequestMapping("uploadPage")
    public String uploadPage() throws WebException
    {
        return "manage/projectManage/projectinfopub";
    }
    
    @RequestMapping("saveUpload")
    public @ResponseBody String saveUpload(WindDoorEntity wd)
            throws WebException
    {
        try
        {
            windService.insertWordHtml(wd);
        }
        catch (Exception e)
        {
            SxjLogger.error("保存上传信息失败", e.getClass());
            e.printStackTrace();
        }
        return "ok";
    }
    
    @RequestMapping("woodList")
    public String woodList(ModelMap map, WindDoorModel query)
            throws WebException
    {
        try
        {
            if (query != null)
            {
                query.setPagable(true);
            }
            List<WindDoorEntity> list = windService.query(query);
            map.put("list", list);
            map.put("query", query);
        }
        catch (Exception e)
        {
            SxjLogger.error("查询信息失败", e.getClass());
            e.printStackTrace();
        }
        return "manage/projectManage/projectlistman";
    }
    
    @RequestMapping("checkState")
    public @ResponseBody String checkState(String id, Integer state)
            throws WebException
    {
        try
        {
            windService.checkState(id, state);
        }
        catch (Exception e)
        {
            SxjLogger.error("更改状态失败", e.getClass());
            return "false";
        }
        return "ok";
    }
}
