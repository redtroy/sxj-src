package com.sxj.supervisor.website.controller.developers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sxj.spring.modules.mapper.JsonMapper;
import com.sxj.supervisor.entity.developers.DevelopersEntity;
import com.sxj.supervisor.entity.system.AreaEntity;
import com.sxj.supervisor.service.developers.IDevelopersService;
import com.sxj.supervisor.service.system.IAreaService;
import com.sxj.supervisor.website.controller.BaseController;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/developers")
public class DeveloperController extends BaseController
{
    @Autowired
    private IDevelopersService developerService;
    
    @Autowired
    private IAreaService areaService;
    
    /**
     * 开发商列表页面
     * @param entity
     * @return
     */
    @RequestMapping("/developers")
    public String developerList(DevelopersEntity query, ModelMap map,
            HttpServletRequest request) throws WebException
    {
        try
        {
            if (StringUtils.isEmpty(query.getCity()))
            {
                query.setCity("3201");
            }
            
            query.setPagable(true);
            query.setShowCount(20);
            List<DevelopersEntity> list = developerService.queryDeveloperList(query);
            List<AreaEntity> cityList = areaService.getChildrenAreas("32");
            map.put("list", list);
            map.put("cityList", cityList);
            map.put("query", query);
            return "site/developers/developers";
        }
        catch (Exception e)
        {
            SxjLogger.error("查询开发商信息错误", e, this.getClass());
            throw new WebException("查询开商发信息错误");
        }
    }
    
    private String getCity(HttpServletRequest request)
    {
        String city = "";
        String jsonstr = getResult(request);
        Map<String, Object> map1 = null;
        try
        {
            map1 = JsonMapper.nonEmptyMapper()
                    .getMapper()
                    .readValue(jsonstr,
                            new TypeReference<Map<String, Object>>()
                            {
                            });
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String city_id = (String) map1.get("city_id");
        if (!StringUtils.isEmpty(city))
        {
            city = city_id.substring(0, 4);
        }
        else
        {
            city = "3201";
        }
        return city;
    }
}