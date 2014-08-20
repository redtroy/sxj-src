package com.sxj.supervisor.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.model.function.FunctionModel;
import com.sxj.supervisor.service.function.IFunctionService;

@Controller
@RequestMapping("/basic")
public class BasicController extends BaseController{
    
	@Autowired
	private IFunctionService functionService;
	
	
	@RequestMapping("footer")
	public String ToFooter() {
		return "manage/footer";
	}

	@RequestMapping("head")
	public String ToHeader() {
		return "manage/head";
	}
    /**
     * 左侧菜单
     * @param map
     * @return
     */
	@RequestMapping("menu")
	public String ToMenu(ModelMap map) {
        List<FunctionModel> list=functionService.queryFunctions();
        map.put("list", list);
		return "manage/menu";
	}
	
}


