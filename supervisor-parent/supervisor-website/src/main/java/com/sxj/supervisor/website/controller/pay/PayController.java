package com.sxj.supervisor.website.controller.pay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.redis.core.RQueue;
import com.sxj.redis.core.collections.RedisCollections;
import com.sxj.redis.core.pubsub.RedisTopics;
import com.sxj.supervisor.entity.pay.PayRecordEntity;
import com.sxj.supervisor.enu.contract.PayModeEnum;
import com.sxj.supervisor.enu.contract.PayStageEnum;
import com.sxj.supervisor.model.comet.MessageChannel;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.model.contract.ContractPayModel;
import com.sxj.supervisor.model.login.SupervisorPrincipal;
import com.sxj.supervisor.service.contract.IContractPayService;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.supervisor.website.controller.BaseController;
import com.sxj.util.LoginToken;
import com.sxj.util.comet.CometServiceImpl;
import com.sxj.util.common.EncryptUtil;
import com.sxj.util.common.ISxjHttpClient;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/pay")
public class PayController extends BaseController {
	@Autowired
	private IContractPayService payService;

	/**
	 * 合同service
	 */
	@Autowired
	private IContractService contractService;

	@Autowired
	private RedisCollections collections;

	@Autowired
	private ISxjHttpClient httpClient;

	@Autowired
	private RedisTopics redisTopics;

	@Value("${httpclient.financeUrl}")
	private String webUrl;

	/**
	 * 获取付款信息列表
	 * 
	 * @param map
	 * @param query
	 * @param session
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("paylist")
	public String paylist(ModelMap map, String del, ContractPayModel query,
			HttpServletRequest request, HttpSession session)
			throws WebException {
		try {
			if (query != null) {
				query.setPagable(true);
				if (!"".equals(query.getState()) && query.getState() != null) {
					String[] states = query.getState().split("_");
					if ("M".equals(states[0])) {
						query.setPayMode(states[1]);
					} else if ("S".equals(states[0])) {
						query.setState(states[1]);
					}
				}
			}
			SupervisorPrincipal info = getLoginInfo(session);
			String memberNo = info.getMember().getMemberNo();
			query.setMemberNo(memberNo);
			query.setMemberType(info.getMember().getType().getId().toString());
			PayModeEnum[] payMode = PayModeEnum.values();
			PayStageEnum[] payState = PayStageEnum.values();
			List<PayRecordEntity> list = new ArrayList<PayRecordEntity>();
			if (info.getMember().getType().getId() == 0) {
				list = payService.queryPayListA(query);
				map.put("state", "a");
			} else {
				list = payService.queryPayListB(query);
				map.put("state", "b");
			}
			map.put("list", list);
			String channelName = MessageChannel.WEBSITE_PAY_MESSAGE + memberNo;
			if ("1".equals(del)) {
				CometServiceImpl.setCount(channelName, 0l);
			}
			map.put("payState", payState);
			map.put("payMode", payMode);
			map.put("query", query);
			map.put("channelName", channelName);
			// 注册监听

			registChannel(channelName);
		} catch (Exception e) {
			throw new WebException(e.getMessage());
		}
		return "site/pay/pay";
	}

	@RequestMapping("cleanChannel")
	public @ResponseBody Map<String, String> cleanChannel(String channelName)
			throws WebException {
		try {
			Map<String, String> map = new HashMap<String, String>();
			CometServiceImpl.setCount(channelName, 0l);
			map.put("isOk", "ok");
			return map;
		} catch (Exception e) {
			SxjLogger.error("清除频道错误", e, this.getClass());
			throw new WebException("清除频道错误");
		}

	}

	/**
	 * 根据合同号获取详细信息
	 * 
	 * @param model
	 * @param contractNo
	 * @param recordNo
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("info")
	public String queryContractInfo(ModelMap model, String contractNo, String id)
			throws WebException {
		try {
			ContractModel contract = contractService
					.getContractModelByContractNo(contractNo);
			ContractModel contractModel = new ContractModel();
			if (contract.getContract() != null) {
				contractModel = contractService.getContract(contract
						.getContract().getId());
			}
			model.put("contractModel", contractModel);
			model.put("recordNo", id);
			if (contractModel.getContract().getType().getId() == 0) {
				return "site/record/contract-info-zhaobiao";
			} else {
				return "site/record/contract-info";
			}
		} catch (Exception e) {
			SxjLogger.error("查询合同信息错误", e, this.getClass());
			throw new WebException("查询合同信息错误");
		}
	}

	/**
	 * 甲方付款
	 */
	@RequestMapping("pay")
	public @ResponseBody Map<String, String> pay(String id, Double payReal)
			throws WebException {
		try {
			PayRecordEntity pay = payService.getPayRecordEntity(id);
			Map<String, String> map = new HashMap<String, String>();
			pay.setPayReal(payReal);
			if (pay.getState().equals(PayStageEnum.STAGE1)) {
				String flag = payService.updateState(pay);
				if (flag.equals("ok")) {
					// 甲方
					/*
					 * CometServiceImpl.subCount(MessageChannel.WEBSITE_PAY_MESSAGE
					 * + pay.getMemberNoA()); MessageChannel.initTopic()
					 * .publish( MessageChannel.WEBSITE_PAY_MESSAGE +
					 * pay.getMemberNoA());
					 */
					// 乙方
					CometServiceImpl
							.takeCount(MessageChannel.WEBSITE_PAY_MESSAGE
									+ pay.getMemberNoB());
					redisTopics.getTopic(MessageChannel.TOPIC_NAME).publish(
							MessageChannel.WEBSITE_PAY_MESSAGE
									+ pay.getMemberNoB());
					map.put("isOk", "ok");
				} else {
					map.put("isOk", "false");
				}
			}
			return map;
		} catch (Exception e) {
			throw new WebException("甲方付款错误");
		}
	}

	/**
	 * 乙方确认付款
	 */
	@RequestMapping("pay_ok")
	public @ResponseBody Map<String, String> pay_ok(String id)
			throws WebException {
		try {
			PayRecordEntity pay = payService.getPayRecordEntity(id);
			Map<String, String> map = new HashMap<String, String>();
			if (pay.getState().equals(PayStageEnum.STAGE2)) {
				String flag = payService.updateState(pay);
				if (flag.equals("ok")) {
					// PayRecordEntity pay = payService.getPayRecordEntity(id);
					// 乙方
					/*
					 * CometServiceImpl.subCount(MessageChannel.WEBSITE_PAY_MESSAGE
					 * + pay.getMemberNoB()); MessageChannel.initTopic()
					 * .publish( MessageChannel.WEBSITE_PAY_MESSAGE +
					 * pay.getMemberNoB());
					 */
					map.put("isOk", "ok");
				} else {
					map.put("isOk", "false");
				}
			}
			return map;
		} catch (Exception e) {
			throw new WebException("乙方确认付款错误");
		}
	}

	/**
	 * 测试
	 * 
	 * @throws WebException
	 */
	@RequestMapping("tofinance")
	public String tofinance(String payId, HttpServletRequest request,
			HttpSession session) throws WebException {
		try {
			SupervisorPrincipal loginInfo = getLoginInfo(session);
			if (loginInfo == null) {
				return LOGIN;
			}
			PayRecordEntity pay = payService.getPayRecordEntity(payId);
			if (pay == null) {
				throw new WebException("付款记录不存在");
			}
			HashMap<String, Object> map = new HashMap<>();

			map.put("memberNo_A", pay.getMemberNoA());
			map.put("payNo", pay.getPayNo());
			map.put("contractNo", pay.getContractNo());
			map.put("batchNo", pay.getBatchNo());
			map.put("payAmount", pay.getPayAmount());
			map.put("content", pay.getContent());

			// String payjson = JsonMapper.nonDefaultMapper().toJson(map);
			// String state = httpClient.postJson(
			// webUrl + "/finance/getModel.htm", payjson);
			// Map<String, String> jmap =
			// JsonMapper.nonDefaultMapper().fromJson(
			// state, HashMap.class);
			// if ("0".equals(jmap.get("flag"))) {
			// SxjLogger.info("-------" + state, this.getClass());
			// throw new WebException("融资请求失败！");
			// }
			RQueue<Map<String, Object>> queue = collections.getQueue(payId);
			queue.clear();
			queue.offer(map);
			// queue.expireAt(60000);// 设置失效时间
			LoginToken loginToken = new LoginToken();
			loginToken.setMemberNo(loginInfo.getMember().getMemberNo());
			loginToken.setMemberName(loginInfo.getMember().getName());
			loginToken.setPassword(loginInfo.getMember().getPassword());

			return "redirect:" + webUrl + "/to_login.htm?payId=" + payId
					+ "&member=" + loginToken.getMemberNo() + "&token="
					+ EncryptUtil.md5Hex(loginToken.toString());
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new WebException(e.getMessage());
		}
	}

	/**
	 * 更改融资状态
	 */
	@RequestMapping("changeState")
	public @ResponseBody Map<String, String> changeState(String payNo,
			String state) throws WebException {
		Map<String, String> map = new HashMap<String, String>();
		try {
			String flag = payService.updateMode(payNo, state);
			map.put("flag", flag);
		} catch (Exception e) {
			SxjLogger.error("更改状态出错", e, this.getClass());
			map.put("flag", "0");
		}
		return map;
	}
}
