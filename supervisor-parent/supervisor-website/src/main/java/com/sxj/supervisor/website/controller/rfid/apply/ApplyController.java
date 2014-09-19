package com.sxj.supervisor.website.controller.rfid.apply;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.website.controller.BaseController;

@Controller
@RequestMapping("/rfid/apply")
public class ApplyController extends BaseController {

	/**
	 * 物流标签申请页面
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("applyView")
	public String applyView(ModelMap map) {
		return "site/rfid/rfidApply/new-gysorder";
	}
}
