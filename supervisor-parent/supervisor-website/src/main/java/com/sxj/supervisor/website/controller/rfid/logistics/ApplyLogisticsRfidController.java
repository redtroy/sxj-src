package com.sxj.supervisor.website.controller.rfid.logistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.entity.rfid.apply.RfidApplicationEntity;
import com.sxj.supervisor.enu.rfid.RfidTypeEnum;
import com.sxj.supervisor.enu.rfid.apply.ReceiptStateEnum;
import com.sxj.supervisor.enu.rfid.applyManager.M_PayStateEnum;
import com.sxj.supervisor.model.rfid.app.RfidApplicationQuery;
import com.sxj.supervisor.service.rfid.app.IRfidApplicationService;
import com.sxj.supervisor.website.controller.BaseController;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/rfid/logistics")
public class ApplyLogisticsRfidController extends BaseController {
	@Autowired
	private IRfidApplicationService applyService;

	/**
	 * 物流标签申请页面
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("to_apply")
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
		return "site/rfid/logistics/apply/apply";
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

	/**
	 * 物流标签申请管理列表
	 * 
	 * @param map
	 * @param query
	 * @return
	 */
	@RequestMapping("apply_list")
	public String applyManager_list(ModelMap map, RfidApplicationQuery query,
			HttpSession session) throws WebException {
		try {
			if (query != null) {
				query.setPagable(true);
			}
			MemberEntity member = getLoginInfo(session).getMember();
			query.setMemberNo(member.getMemberNo());
			List<RfidApplicationEntity> list = applyService.query(query);
			ReceiptStateEnum[] receipt_states = ReceiptStateEnum.values();
			M_PayStateEnum[] m_pay_states = M_PayStateEnum.values();
			map.put("receipt_states", receipt_states);
			map.put("m_pay_states", m_pay_states);
			map.put("list", list);
			map.put("query", query);
		} catch (Exception e) {
			SxjLogger.error("物流标签申请管理列表错误", e, this.getClass());
			throw new WebException("物流标签申请管理列表错误");
		}
		return "site/rfid/logistics/apply/apply-list";
	}

	/**
	 * 删除
	 */
	@RequestMapping("del_apply")
	public @ResponseBody Map<String, String> del(String id, String applyNo)
			throws WebException {
		try {
			Map<String, String> map = new HashMap<String, String>();
			Boolean flag = applyService.delApp(id, applyNo);
			if (flag) {
				map.put("flag", "ok");
			} else {
				map.put("flag", "no");
			}
			return map;
		} catch (Exception e) {
			SxjLogger.error("物流标签申请管理列表删除错误", e, this.getClass());
			throw new WebException("物流标签申请管理列表删除错误");
		}
	}

}
