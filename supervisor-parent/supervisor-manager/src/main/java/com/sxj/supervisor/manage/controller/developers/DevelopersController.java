package com.sxj.supervisor.manage.controller.developers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.entity.developers.DevelopersEntity;
import com.sxj.supervisor.entity.system.AreaEntity;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.service.developers.IDevelopersService;
import com.sxj.supervisor.service.system.IAreaService;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/developers")
public class DevelopersController extends BaseController
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
	public String developerList(DevelopersEntity entity, ModelMap map) throws WebException{
    	try{
    		if (entity != null)
            {
    			entity.setPagable(true);
            }
	    	List<DevelopersEntity> list = developerService.queryDeveloperList(entity);
	    	 List<AreaEntity> cityList = areaService.getChildrenAreas("32");
            map.put("list", list);
            map.put("cityList", cityList);
            map.put("query", entity);
			return "manage/developers/developers";
    	}catch(Exception e){
            SxjLogger.error("查询开发商信息错误", e, this.getClass());
            throw new WebException("查询开商发信息错误");
    	}
	}
    @RequestMapping("/toEdit")
    public String toEdit(String id, ModelMap map) throws WebException{
    	 List<AreaEntity> cityList = areaService.getChildrenAreas("32");
    	 map.put("cityList", cityList);
        if (StringUtils.isEmpty(id)) {
            return "manage/developers/add-deve";
        } else {
        	DevelopersEntity info = developerService.getDeveloper(id);
            map.put("info", info);
            return "manage/developers/add-deve";
        }
        
    }
    @RequestMapping("edit")
	public @ResponseBody Map<String, String> addDevelopers(String id,String name,String city,String url,String address,String telPhone)
			throws WebException {
    	DevelopersEntity entity = new DevelopersEntity();
    	entity.setName(name);
    	entity.setCity(city);
    	entity.setUrl(url);
    	entity.setAddress(address);
    	entity.setTelPhone(telPhone);
    	entity.setEntryTime(new Date());
    	try {
			if(null==id||id.isEmpty()){
				developerService.addDeveloper(entity);
			}else{
				entity.setId(id);
				developerService.updateDeveloper(entity);
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("isOK", "ok");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebException(e);
		}
	}
    @RequestMapping("delete")
    public @ResponseBody Map<String, String> delDevelopers(String id)
    		throws WebException {
    	try {
    		developerService.delDeveloper(id);;
    		Map<String, String> map = new HashMap<String, String>();
    		map.put("isOK", "ok");
    		return map;
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new WebException(e);
    	}
    }
}
