package com.sxj.supervisor.website.controller.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.entity.gather.WindDoorEntity;
import com.sxj.supervisor.service.tasks.IWindDoorService;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("market")
public class WindDoorInfoController {

	@Autowired
	private IWindDoorService iwds;

	@RequestMapping("info")
	public String info(ModelMap map, String id) throws WebException {
		try {
			WindDoorEntity wde = iwds
					.getInfoById("1xBkR9UNnt6W2jUT5b7VgFkuNUgNd4wF");
			map.put("model", wde);
		} catch (Exception e) {
			SxjLogger.error("查询门窗工程详细信息出错", e, this.getClass());
			throw new WebException(e.getMessage());
		}
		return "site/message/invite-info";
	}
}
