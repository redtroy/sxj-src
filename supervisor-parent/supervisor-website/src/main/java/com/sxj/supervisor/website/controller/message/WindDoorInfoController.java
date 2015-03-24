package com.sxj.supervisor.website.controller.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.entity.gather.WindDoorEntity;
import com.sxj.supervisor.service.tasks.IWindDoorService;
import com.sxj.util.exception.WebException;

@Controller
@RequestMapping("market")
public class WindDoorInfoController {

	@Autowired
	private IWindDoorService iwds;

	@RequestMapping("info")
	public String info(ModelMap map, String id) throws WebException {
		try {
			WindDoorEntity wde = iwds
					.getInfoById("4eQqt4e6G1FencQOtA5mr4JfsCxZoYhO");
			map.put("model", wde);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "site/message/invite-info";
	}
}
