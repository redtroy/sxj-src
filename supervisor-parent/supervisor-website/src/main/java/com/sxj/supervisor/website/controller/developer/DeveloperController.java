package com.sxj.supervisor.website.controller.developer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.entity.developers.DevelopersEntity;
import com.sxj.supervisor.entity.system.AreaEntity;
import  com.sxj.supervisor.website.controller.BaseController;
import com.sxj.supervisor.service.developer.IDevelopersService;
import com.sxj.supervisor.service.system.IAreaService;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;



@Controller
@RequestMapping("/developer")
public class DeveloperController extends BaseController {
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
	public String developerList(DevelopersEntity entity, ModelMap map) throws WebException{
    	try{
	    	List<DevelopersEntity> list = developerService.queryDeveloperList(entity);
	    	 List<AreaEntity> cityList = areaService.getChildrenAreas("32");
            map.put("list", list);
            map.put("cityList", cityList);
            map.put("query", entity);
			return "site/developer/developers";
    	}catch(Exception e){
            SxjLogger.error("查询开发商信息错误", e, this.getClass());
            throw new WebException("查询开商发信息错误");
    	}
	}
}