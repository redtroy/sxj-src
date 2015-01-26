package com.sxj.supervisor.website.controller.rfid.window;

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
import com.sxj.supervisor.enu.record.ContractTypeEnum;
import com.sxj.supervisor.enu.rfid.RfidTypeEnum;
import com.sxj.supervisor.enu.rfid.apply.ReceiptStateEnum;
import com.sxj.supervisor.enu.rfid.applymanager.M_PayStateEnum;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.model.login.SupervisorPrincipal;
import com.sxj.supervisor.model.rfid.app.RfidApplicationQuery;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.supervisor.service.rfid.app.IRfidApplicationService;
import com.sxj.supervisor.website.controller.BaseController;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/rfid/window")
public class ApplyWindowRfidController extends BaseController {
	@Autowired
	private IRfidApplicationService applyService;

	@Autowired
	private IContractService contractService;

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
		return "site/rfid/window/apply/apply-list";
	}

	/**
	 * 删除
	 */
	@RequestMapping("del_apply")
	public @ResponseBody Map<String, String> del(String id) throws WebException {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Boolean flag = applyService.delApp(id);
			if (flag) {
				map.put("flag", "ok");
			} else {
				map.put("flag", "no");
				map.put("error", "删除失败");
			}
		} catch (Exception e) {
			SxjLogger.error("物流标签申请管理列表删除错误", e, this.getClass());
			map.put("flag", "no");
			map.put("error", e.getMessage());
		}
		return map;
	}

	/**
	 * 认证标签申请状态
	 * 
	 * @param map
	 * @param session
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("to_apply")
	public String applyMencorder_view(ModelMap map, HttpSession session)
			throws WebException {
		try {
			MemberEntity member = getLoginInfo(session).getMember();
			String type = RfidTypeEnum.getName(member.getType().getId());
			map.put("member", member);
			map.put("type", type);
		} catch (Exception e) {
			SxjLogger.error("认证标签申请页面错误", e, this.getClass());
			throw new WebException("认证标签申请页面错误");
		}
		return "site/rfid/window/apply/apply-rfid";
	}

	/**
	 * 提交认证标签申请
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
	 * 验证申请单是否存在
	 * 
	 * @param apply
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("checkApply")
	public @ResponseBody Map<String, String> checkApply(String id)
			throws WebException {
		Map<String, String> map = new HashMap<String, String>();
		try {
			RfidApplicationEntity apply = applyService.getApplicationInfo(id);
			if (apply == null) {
				throw new WebException("申请单不存在！");
			}
			if (apply.getDelstate()) {
				throw new WebException("申请单不存在！");
			}
			map.put("isOk", "ok");
		} catch (Exception e) {
			SxjLogger.error("验证RFID申请单错误", e, this.getClass());
			map.put("error", e.getMessage());
		}
		return map;
	}

	/**
	 * 根据合同号检查合同，做合同匹配认证，数量认证
	 */
	@RequestMapping("check_contract")
	public @ResponseBody Map<String, String> check_contract(String param,
			HttpSession session) throws WebException {
		Map<String, String> map = new HashMap<String, String>();
		try {
			ContractModel cm = contractService
					.getContractModelByContractNo(param);
			if (cm == null) {
				map.put("status", "n");
				map.put("info", "没有找到相匹配的合同");
				return map;
			}
			if (!cm.getContract().getType().equals(ContractTypeEnum.BIDDING)) {
				map.put("status", "n");
				map.put("info", "合同不是招标合同");
				return map;
			}
			String memberIdA = cm.getContract().getMemberIdA();
			String memberIdB = cm.getContract().getMemberIdB();
			SupervisorPrincipal userInfo = getLoginInfo(session);
			String memberId = userInfo.getMember().getMemberNo();
			if (!memberId.equals(memberIdA) && !memberId.equals(memberIdB)) {
				map.put("status", "n");
				map.put("info", "该合同号不属于当前会员");
				return map;
			}

			map.put("status", "y");

		} catch (Exception e) {
			map.put("status", "n");
			map.put("info", "获取合同错误");
		}
		return map;
	}
}
