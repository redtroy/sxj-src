package com.sxj.supervisor.website.controller.rfid.apply;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.entity.rfid.apply.RfidApplicationEntity;
import com.sxj.supervisor.enu.rfid.apply.RfidTypeEnum;
import com.sxj.supervisor.service.rfid.app.IRfidApplicationService;
import com.sxj.supervisor.website.controller.BaseController;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/rfid/apply")
public class ApplyController extends BaseController {
	@Autowired
	private IRfidApplicationService applyService;

	/**
	 * 物流标签申请页面
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("applyView")
	public String applyView(ModelMap map, HttpSession session)
			throws WebException {
		try {
			MemberEntity member = getLoginInfo(session).getMember();
			String type = RfidTypeEnum.getName(member.getType().getId());
			map.put("member", member);
			map.put("type", type);
		} catch (Exception e) {
			SxjLogger.error("查询物流标签申请页面错误", e, this.getClass());
			throw new WebException("查询物流标签申请页面错误");
		}
		return "site/rfid/rfidApply/new-gysorder";
	}

	/**
	 * 提交物流标签申请
	 */
	@RequestMapping("apply")
	public @ResponseBody Map<String, String> apply(RfidApplicationEntity app)
			throws WebException {
		Map<String, String> map = new HashMap<String, String>();
		try {
			applyService.addApp(app);
			map.put("isOk", "ok");
		} catch (Exception e) {
			SxjLogger.error("提交物流标签申请", e, this.getClass());
			throw new WebException("提交物流标签申请错误");
		}
		return map;
	}

}
