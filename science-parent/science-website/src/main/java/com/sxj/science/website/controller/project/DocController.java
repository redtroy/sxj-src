package com.sxj.science.website.controller.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.science.entity.export.ProjectEntity;
import com.sxj.science.model.DocModel;
import com.sxj.science.service.IDocService;
import com.sxj.science.service.IProjectService;
import com.sxj.science.website.controller.BaseController;
import com.sxj.util.common.StringUtils;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/doc")
public class DocController extends BaseController
{
    
    @Autowired
    private IDocService docService;
    
    @Autowired
    private IProjectService projectService;
    
    @RequestMapping("/toAdd")
    public String toAdd(String projectId, String itemId, String docId,
            ModelMap map)
    {
        ProjectEntity project = projectService.getProject(projectId);
        map.put("projectId", projectId);
        map.put("itemId", itemId);
        if (project != null)
        {
            map.put("projectName", project.getName());
        }
        map.put("memberNo", "E00001");
        if (StringUtils.isEmpty(docId))
        {
            List<DocModel> docList = docService.queryDocModel(itemId);
            if (docList != null && docList.size() > 0)
            {
                map.put("docList", docList);
                return "site/editmtable";
            }
        }
        else
        {
            DocModel docModel = docService.getDocModel(docId);
            if (docModel != null)
            {
                List<DocModel> docList = new ArrayList<DocModel>();
                docList.add(docModel);
                map.put("docList", docList);
                return "site/editmtable";
            }
        }
        
        return "site/mtable";
    }
    
    @RequestMapping("/addDoc")
    public @ResponseBody Map<String, Object> addDoc(@ModelAttribute DocModel doc)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            docService.addDocModel(doc);
            map.put("isOK", true);
        }
        catch (Exception e)
        {
            SxjLogger.error("添加下料单错误", e, this.getClass());
            map.put("isOK", false);
            map.put("error", e.getMessage());
        }
        return map;
    }
    
    @RequestMapping("/toEdit")
    public String toAdd(String projectId, String itemId, String[] docIds,
            ModelMap map)
    {
        ProjectEntity project = projectService.getProject(projectId);
        map.put("projectId", projectId);
        map.put("itemId", itemId);
        if (project != null)
        {
            map.put("projectName", project.getName());
        }
        map.put("memberNo", "E00001");
        List<DocModel> docList = docService.queryDocModel(docIds);
        map.put("docList", docList);
        return "site/editmtable";
    }
}
