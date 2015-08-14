package com.sxj.supervisor.website.controller.message;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import third.rewrite.fastdfs.NameValuePair;
import third.rewrite.fastdfs.service.IStorageClientService;

import com.sxj.supervisor.entity.gather.WindDoorEntity;
import com.sxj.supervisor.service.tasks.IWindDoorService;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("market")
public class WindDoorInfoController
{
    
    @Autowired
    private IWindDoorService iwds;
    
    @Autowired
    private IStorageClientService storageClientService;
    
    @RequestMapping("info")
    public String info(ModelMap map, String id) throws WebException
    {
        try
        {
            WindDoorEntity wde = iwds.getInfoById(id);
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
        return "site/message/invite-info";
    }
}
