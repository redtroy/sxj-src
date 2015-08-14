package com.sxj.supervisor.manage.controller.openapi;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.spring.modules.mapper.JsonMapper;
import com.sxj.supervisor.entity.developers.DevelopersEntity;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.open.ApiModel;
import com.sxj.supervisor.service.developers.IDevelopersService;
import com.sxj.supervisor.service.member.IMemberService;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/api")
public class OpenApiController extends BaseController
{
    @Autowired
    private IMemberService memberService;
    
    @Autowired
    private IDevelopersService developerService;
    
    /**
     * 开发商列表页面
     * @param entity
     * @return
     */
    @RequestMapping("/developers")
    public @ResponseBody String developerList(DevelopersEntity query,
            HttpServletResponse response) throws WebException
    {
        try
        {
            List<ApiModel> list = developerService.apiQueryDevelopers(query);
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print(JsonMapper.nonEmptyMapper().toJson(list));
            out.flush();
            out.close();
        }
        catch (Exception e)
        {
            SxjLogger.error("查询开发商信息错误", e, this.getClass());
            throw new WebException("查询开发商信息错误");
        }
        return null;
    }
    
    /**
     * 会员
     * @param entity
     * @return
     */
    @RequestMapping("/member")
    public @ResponseBody String memberList(String name, String type,String city,
            HttpServletResponse response) throws WebException
    {
        try
        {
            List<ApiModel> list = memberService.apiQueryMembers(name, type,city);
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print(JsonMapper.nonEmptyMapper().toJson(list));
            out.flush();
            out.close();
        }
        catch (Exception e)
        {
            SxjLogger.error("查询会员信息错误", e, this.getClass());
            throw new WebException("查询会员信息错误");
        }
        return null;
    }
}
