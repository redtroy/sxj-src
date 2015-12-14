package com.sxj.science.website.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.science.entity.export.ProjectEntity;
import com.sxj.science.model.ItemModel;
import com.sxj.science.model.ProjectQuery;
import com.sxj.science.service.IAloneOptimService;
import com.sxj.science.service.IProjectService;
import com.sxj.util.common.StringUtils;

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
    
    @RequestMapping("logout")
    public void logout(HttpServletResponse response, HttpSession session)
            throws IOException
    {
        session.setAttribute("memberNo", null);
        response.sendRedirect("http://www.menchuang.org.cn/logout.htm");
    }
    
    @RequestMapping("index")
    public String ToIndex(ProjectQuery query, HttpSession session, ModelMap map)
    {
        if (StringUtils.isNotEmpty(query.getMemberNo()))
        {
            session.setAttribute("memberNo", query.getMemberNo());
            session.setAttribute("memberName", query.getMemberName());
        }
        query.setPagable(true);
        query.setShowCount(20);
        query.setIsShow(1);
        query.setMemberNo(getLoginInfo(session));
        List<ProjectEntity> list = projectService.query(query);
        for (ProjectEntity projectEntity : list)
        {
            List<ItemModel> items = projectService.queryItems(projectEntity.getId());
            if (items.size() > 0)
            {
                projectEntity.setState(2);
            }
            else
            {
                projectEntity.setState(0);
            }
            
            for (ItemModel item : items)
            {
                if (item.getState() == 0)
                {
                    projectEntity.setState(0);
                }
                if (item.getState() == 1)
                {
                    projectEntity.setState(1);
                    break;
                }
            }
        }
        
        Integer count = projectService.queryFileCount(query);
        if (count == null)
        {
            count = 0;
        }
        map.put("projectList", list);
        map.put("query", query);
        map.put("maxCount", count);
        return INDEX;
        
    }
}
