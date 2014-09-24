package com.sxj.supervisor.manage.controller.rfid.lableManage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.util.exception.WebException;

/**
 * 物流标签管理
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/rfid/lableManage")
public class LableManageController extends BaseController {

	@RequestMapping("lable_list")
	public String lable_list(ModelMap map) throws WebException {
		try {

		} catch (Exception e) {
			// TODO: handle exception
		}
		return "manage/rfid/gysrfid-link/gysrfid-link";
	}
}
