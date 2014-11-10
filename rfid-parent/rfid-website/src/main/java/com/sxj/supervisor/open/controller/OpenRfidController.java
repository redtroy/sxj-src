package com.sxj.supervisor.open.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.entity.contract.ContractBatchEntity;
import com.sxj.supervisor.entity.contract.ContractEntity;
import com.sxj.supervisor.entity.member.AccountEntity;
import com.sxj.supervisor.enu.member.AccountStatesEnum;
import com.sxj.supervisor.model.contract.BatchItemModel;
import com.sxj.supervisor.model.contract.ContractBatchModel;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.model.login.SupervisorPrincipal;
import com.sxj.supervisor.rfid.login.SupervisorShiroRedisCache;
import com.sxj.supervisor.rfid.login.SupervisorSiteToken;
import com.sxj.supervisor.service.open.member.IAccountService;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.WebException;

@Controller
@RequestMapping("/rfid")
public class OpenRfidController {
	@Autowired
	private IAccountService accountService;

	/**
	 * 登陆
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "login")
	public int login(String userId, String password, HttpSession session)
			throws WebException {
		SupervisorSiteToken token = null;
		SupervisorPrincipal userBean = null;
		AccountEntity account = null;
		if (StringUtils.isNotEmpty(userId)) {
			account = accountService.getAccountByAccountNo(userId);
			if (account == null) {
				return 0;
			}
			if (AccountStatesEnum.stop.equals(account.getState())) {
				return 0;
			}

			userBean = new SupervisorPrincipal();
			userBean.setAccount(account);
			token = new SupervisorSiteToken(userBean, password);
		} else {
			return 0;
		}
		Subject currentUser = SecurityUtils.getSubject();
		try {
			currentUser.login(token);
			PrincipalCollection principals = currentUser.getPrincipals();
			if (userBean.getAccount() != null) {
				SupervisorShiroRedisCache.addToMap(userBean.getAccount()
						.getId(), principals);
			}
		} catch (AuthenticationException e) {
			return -1;

		}
		if (currentUser.isAuthenticated()) {
			session.setAttribute("userinfo", userBean);
			if (account != null) {
				accountService.edit_Login(account.getId());
			}
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * 注销
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "logout")
	public int logout(String userId) {
		try {
			Subject currentUser = SecurityUtils.getSubject();
			currentUser.logout();
			return 0;
		} catch (Exception e) {
			return 1;
		}
	}

	/**
	 * 获取批次信息
	 * 
	 * @param rfidNo
	 * @return
	 */
	@RequestMapping(value = "info/batch/{rfidNo}")
	public @ResponseBody Map<String, Object> getRfidBatchInfo(
			@PathVariable String rfidNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		ContractModel model = new ContractModel();
		ContractEntity contract = new ContractEntity();
		contract.setContractNo("CT000001");
		model.setContract(contract);
		List<ContractBatchModel> batchList = new ArrayList<>();

		ContractBatchModel batch1 = new ContractBatchModel();
		ContractBatchEntity batchEn1 = new ContractBatchEntity();
		List<BatchItemModel> batchItems1 = new ArrayList<BatchItemModel>();
		BatchItemModel item1 = new BatchItemModel();
		item1.setProductModel("中空玻璃0001");
		item1.setQuantity(new Float("2211.22"));
		batchItems1.add(item1);

		BatchItemModel item2 = new BatchItemModel();
		item2.setProductModel("中空玻璃0002");
		item2.setQuantity(new Float("1000"));
		batchItems1.add(item2);

		batchEn1.setBatchNo("1");
		batch1.setBatch(batchEn1);
		batch1.setBatchItems(batchItems1);

		ContractBatchModel batch2 = new ContractBatchModel();
		ContractBatchEntity batchEn2 = new ContractBatchEntity();
		List<BatchItemModel> batchItems2 = new ArrayList<BatchItemModel>();
		BatchItemModel item11 = new BatchItemModel();
		item11.setProductModel("型材玻璃0001");
		item11.setQuantity(new Float("1111.11"));
		batchItems2.add(item11);

		BatchItemModel item12 = new BatchItemModel();
		item12.setProductModel("型材玻璃0002");
		item12.setQuantity(new Float("2000"));
		batchItems2.add(item12);

		batchEn2.setBatchNo("2");
		batch2.setBatch(batchEn2);
		batch2.setBatchItems(batchItems2);

		batchList.add(batch1);
		batchList.add(batch2);

		model.setBatchList(batchList);
		map.put("model", model);
		return map;
	}

	/**
	 * 获取合同规格信息
	 * 
	 * @param rfidNo
	 * @return
	 */
	@RequestMapping(value = "info/contract/{rfidNo}")
	public @ResponseBody Map<String, Object> getRfidContractInfo(
			@PathVariable String rfidNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contratcNo", "CT000001");
		map.put("rfidNo", "AAAA00001");
		map.put("winType", "C120 60*120");
		return map;
	}

	/**
	 * 发货
	 * 
	 * @param rfidNo
	 * @return
	 */
	@RequestMapping(value = "send/{rfidNo}")
	public @ResponseBody Map<String, Object> sendGoods(
			@PathVariable String rfidNo) {
		return null;

	}

	/**
	 * 验收
	 * 
	 * @param rfidNo
	 * @return
	 */
	@RequestMapping(value = "check/{rfidNo}")
	public @ResponseBody Map<String, Object> checkAndAccept(
			@PathVariable String rfidNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", 1);
		map.put("contractNo", "CT1410250001");
		map.put("batchNo", "00001");
		return map;
	}

	/**
	 * 质检
	 * 
	 * @param rfidNo
	 * @return
	 */
	@RequestMapping(value = "test/{rfidNo}")
	public @ResponseBody Map<String, Object> testRfid(
			@PathVariable String rfidNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractNo", "CT1410250001");
		String[] batchNos = new String[2];
		batchNos[0] = "AAAA0001";
		batchNos[1] = "AAAA0002";
		map.put("batchNo", batchNos);
		return map;

	}

	/**
	 * 安装
	 * 
	 * @param rfidNo
	 * @return
	 */
	@RequestMapping(value = "setup/{rfidNo}")
	public @ResponseBody Map<String, Object> setupRfid(
			@PathVariable String rfidNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", 1);
		map.put("contractNo", "CT1410250001");
		map.put("batchNo", "00001");
		return map;

	}

}
