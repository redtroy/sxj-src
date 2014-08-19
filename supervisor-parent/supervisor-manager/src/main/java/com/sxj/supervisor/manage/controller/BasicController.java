package com.sxj.supervisor.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.service.function.IFunctionService;

@Controller
@RequestMapping("/basic")
public class BasicController extends BaseController{
    
	@Autowired
	private IFunctionService IFS;
	
	
	@RequestMapping("footer")
	public String ToFooter() {
		return "manage/footer";
	}

	@RequestMapping("head")
	public String ToHeader() {
		return "manage/head";
	}

	@RequestMapping("menu")
	public String ToMenu(ModelMap map) {
       // List<FunctionModel> list=IFS.queryFunctions();
        map.put("aaaa", 555555);
		return "manage/menu";
	}
}
