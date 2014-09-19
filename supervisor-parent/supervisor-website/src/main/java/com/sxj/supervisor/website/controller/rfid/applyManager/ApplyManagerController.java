package com.sxj.supervisor.website.controller.rfid.applyManager;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.model.rfid.app.RfidApplicationQuery;
import com.sxj.supervisor.website.controller.BaseController;

@Controller
@RequestMapping("/rfid/applyManager")
public class ApplyManagerController extends BaseController {
	/**
	 * 物流标签申请管理列表
	 * 
	 * @param map
	 * @param query
	 * @return
	 */
	@RequestMapping("applyManager_list")
	public String applyManager_list(ModelMap map, RfidApplicationQuery query) {
		return "site/rfid/applyManager/gysordermgr";
	}
}
