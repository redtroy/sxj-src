package com.sxj.supervisor.manage.controller.projectmessage;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import third.rewrite.fastdfs.NameValuePair;
import third.rewrite.fastdfs.service.IStorageClientService;

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
    
    @Autowired
    private IStorageClientService storageClientService;
    
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
            wd.setPublishFirm("私享家");
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
    
    @RequestMapping("info")
    public String info(ModelMap map, String id) throws WebException
    {
        try
        {
            WindDoorEntity wde = windService.getInfoById(id);
            if (wde.getAdjunctPath().length() > 1)
            {
                String[] fj = wde.getAdjunctPath().split(",");
                List<Map<String, String>> list = new ArrayList<Map<String, String>>();
                for (int i = 0; i < fj.length; i++)
                {
                    String group = fj[i].substring(0, fj[i].indexOf("/"));
                    String path = fj[i].substring(fj[i].indexOf("/") + 1,
                            fj[i].length());
                    Map<String, String> fjmap = new HashMap<String, String>();
                    NameValuePair[] metaList = storageClientService.getMetadata(group,
                            path);
                    String fjname = metaList[0].getValue();
                    fjmap.put("fileName", URLDecoder.decode(fjname, "utf-8"));
                    fjmap.put("filePath", fj[i]);
                    list.add(fjmap);
                }
                map.put("fileList", list);
            }
            map.put("model", wde);
            
        }
        catch (Exception e)
        {
            SxjLogger.error("查询门窗工程详细信息出错", e, this.getClass());
            throw new WebException(e.getMessage());
        }
        return "manage/projectManage/projectinfo";
    }
}
