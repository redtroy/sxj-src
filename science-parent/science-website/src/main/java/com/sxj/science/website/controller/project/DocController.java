package com.sxj.science.website.controller.project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.science.entity.export.DocEntity;
import com.sxj.science.entity.export.ItemEntity;
import com.sxj.science.entity.export.ProjectEntity;
import com.sxj.science.model.DocModel;
import com.sxj.science.model.DocQuery;
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
    public String toAdd(String projectId, String itemId, String series,
            ModelMap map)
    {
        ProjectEntity project = projectService.getProject(projectId);
        ItemEntity item = projectService.getItemById(itemId);
        map.put("projectId", projectId);
        map.put("itemId", itemId);
        if (project != null)
        {
            map.put("projectName", project.getName());
        }
        if (item != null)
        {
            map.put("itemName", item.getName());
        }
        map.put("memberNo", "E00001");
        if (StringUtils.isEmpty(series))
        {
            DocQuery query = new DocQuery();
            query.setItemId(itemId);
            query.setIsShow(1);
            List<DocModel> docList = docService.queryDocModel(query);
            if (docList != null && docList.size() > 0)
            {
                map.put("docList", docList);
                return "site/editmtable";
            }
        }
        else
        {
            DocQuery query = new DocQuery();
            query.setSeries(series);
            query.setItemId(itemId);
            query.setIsShow(1);
            List<DocModel> docList = docService.queryDocModel(query);
            if (docList != null && docList.size() > 0)
            {
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
            map.put("docId", doc.getId());
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
    
    @RequestMapping("/removeDoc")
    public @ResponseBody Map<String, Object> removeDoc(String docId)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            DocEntity doc = docService.getDoc(docId);
            docService.editDoc(doc);
            map.put("isOK", true);
        }
        catch (Exception e)
        {
            SxjLogger.error("删除下料单错误", e, this.getClass());
            map.put("isOK", false);
            map.put("error", e.getMessage());
        }
        return map;
    }
    
    @RequestMapping("/editDoc")
    public @ResponseBody Map<String, Object> editDoc(
            @ModelAttribute DocModel doc)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            docService.editDocModel(doc);
            map.put("docId", doc.getId());
            map.put("isOK", true);
        }
        catch (Exception e)
        {
            SxjLogger.error("修改下料单错误", e, this.getClass());
            map.put("isOK", false);
            map.put("error", e.getMessage());
        }
        return map;
    }
    
    @RequestMapping("/toEdit")
    public String toEdit(String projectId, String itemId, String[] docIds,
            ModelMap map)
    {
        ProjectEntity project = projectService.getProject(projectId);
        ItemEntity item = projectService.getItemById(itemId);
        map.put("projectId", projectId);
        map.put("itemId", itemId);
        if (project != null)
        {
            map.put("projectName", project.getName());
        }
        if (item != null)
        {
            map.put("itemName", item.getName());
        }
        map.put("memberNo", "E00001");
        
        DocQuery query = new DocQuery();
        query.setIds(docIds);
        List<DocModel> docList = docService.queryDocModel(query);
        map.put("docList", docList);
        return "site/editmtable";
    }
    
    @RequestMapping("/query")
    public String query(String projectId, String itemId, String windowCode,
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
        
        DocQuery query = new DocQuery();
        query.setWindowCode(windowCode);
        query.setItemId(itemId);
        query.setIsShow(1);
        List<DocModel> docList = docService.queryDocModel(query);
        map.put("docList", docList);
        return "site/editmtable";
    }
}
