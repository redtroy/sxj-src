package com.sxj.science.website.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.science.entity.export.ProjectEntity;
import com.sxj.science.model.ProjectQuery;
import com.sxj.science.service.IAloneOptimService;
import com.sxj.science.service.IProjectService;

@Controller
public class BasicController extends BaseController
{
    
    @Autowired
    private IProjectService projectService;
    
    @Autowired
    private IAloneOptimService optimService;
    
    @RequestMapping("error")
    public String ToError()
    {
        return "site/500";
    }
    
    @RequestMapping("404")
    public String To404()
    {
        return "site/404";
    }
    
    @RequestMapping("index")
    public String ToIndex(ProjectQuery query, HttpSession session, ModelMap map)
    {
        query.setPagable(true);
        query.setShowCount(20);
        List<ProjectEntity> list = projectService.query(query);
        Integer count = projectService.queryFileCount(query);
        if (count == null)
        {
            count = 0;
        }
        session.setAttribute("memberNo", query.getMemberNo());
        session.setAttribute("memberName", query.getMemberName());
        map.put("projectList", list);
        map.put("query", query);
        map.put("maxCount", count);
        return INDEX;
        
    }
}
