package com.sxj.supervisor.website.controller.record;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.redis.service.comet.CometServiceImpl;
import com.sxj.supervisor.entity.contract.ContractBatchEntity;
import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.entity.record.RecordEntity;
import com.sxj.supervisor.enu.record.ContractTypeEnum;
import com.sxj.supervisor.enu.record.RecordConfirmStateEnum;
import com.sxj.supervisor.enu.record.RecordFlagEnum;
import com.sxj.supervisor.enu.record.RecordStateEnum;
import com.sxj.supervisor.enu.record.RecordTypeEnum;
import com.sxj.supervisor.model.comet.MessageChannel;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.model.login.SupervisorPrincipal;
import com.sxj.supervisor.model.record.RecordQuery;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.supervisor.service.member.IMemberService;
import com.sxj.supervisor.service.record.IRecordService;
import com.sxj.supervisor.website.controller.BaseController;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/record")
public class RecordController extends BaseController {

	/**
	 * getRecordState 合同service
	 */
	@Autowired
	private IContractService contractService;

	/**
	 * 备案service
	 */
	@Autowired
	private IRecordService recordService;

	@Autowired
	private IMemberService memberService;

	@RequestMapping("/query")
	public String to_query(ModelMap map, HttpSession session,
			HttpServletRequest request, RecordQuery query) throws WebException {
		try {
			query.setPagable(true);
			RecordConfirmStateEnum[] rse = RecordConfirmStateEnum.values();// 备案状态
			SupervisorPrincipal userBean = (SupervisorPrincipal) session
					.getAttribute("userinfo");
			query.setApplyId(userBean.getMember().getMemberNo());
			query.setSortColumn("RECORD_NO");
			query.setSort("DESC");
			List<RecordEntity> list = recordService.queryRecord(query);
			map.put("recordlist", list);
			map.put("confirmState", rse);
			map.put("query", query);
			map.put("type", userBean.getMember().getType().getId());
			String channelName = MessageChannel.WEBSITE_RECORD_MESSAGE
					+ userBean.getMember().getMemberNo();
			map.put("channelName", channelName);
			// 注册监听
			registChannel(channelName);
			return "site/record/contract-list";
		} catch (Exception e) {
			SxjLogger.error("查询合同信息错误", e, this.getClass());
			throw new WebException("查询合同信息错误");
		}
	}

	@RequestMapping("info")
	public String queryContractInfo(ModelMap model, String contractNo,
			String recordNo) throws WebException {
		try {
			ContractModel contract = contractService
					.getContractModelByContractNo(contractNo);
			ContractModel contractModel = new ContractModel();
			if (contract.getContract() != null) {
				contractModel = contractService.getContract(contract
						.getContract().getId());
			}
			model.put("contractModel", contractModel);
			model.put("recordNo", recordNo);
			if (contractModel.getContract().getType().getId() == 0) {
				return "site/record/contract-info-zhaobiao";
			} else {
				return "site/record/contract-info";
			}
			// contractModel.getContract().getImgPath().split(",");
		} catch (Exception e) {
			SxjLogger.error("查询合同信息错误", e, this.getClass());
			throw new WebException("查询合同信息错误");
		}
	}

	/**
	 * 跳转申请合同
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("/to_apply")
	public String to_apply(ModelMap map, HttpSession session) {
		SupervisorPrincipal userBean = (SupervisorPrincipal) session
				.getAttribute("userinfo");
		map.put("type", userBean.getMember().getType().getId());
		map.put("name", userBean.getMember().getName());// name
		map.put("id", userBean.getMember().getMemberNo());
		map.put("state", userBean.getMember().getCheckState());
		return "site/record/apply-record";
	}

	/**
	 * 申请采购备案合同
	 * 
	 * @param record
	 * @param map
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("/AcgApplyRecord")
	public @ResponseBody Map<String, String> cgApplyRecord(RecordEntity record,
			HttpSession session) throws WebException {
		try {
			Map<String, String> map = new HashMap<String, String>();
			SupervisorPrincipal userBean = (SupervisorPrincipal) session
					.getAttribute("userinfo");
			if (userBean.getMember().getCheckState().getId() == 2) {
				MemberEntity member = memberService.memberInfo(record
						.getMemberIdB());

				record.setApplyId(userBean.getMember().getMemberNo());
				record.setApplyName(userBean.getMember().getName());
				record.setState(RecordStateEnum.noBinding);
				record.setType(RecordTypeEnum.contract);
				record.setApplyDate(new Date());
				record.setDelState(false);
				if (member.getType().getId() == 1) {
					record.setContractType(ContractTypeEnum.glass);// 合同类型
				} else if (member.getType().getId() == 2) {
					record.setContractType(ContractTypeEnum.extrusions);// 合同类型
				}

				record.setFlag(RecordFlagEnum.A);
				record.setConfirmState(RecordConfirmStateEnum.accepted);
				recordService.addRecord(record);
				map.put("isOK", "ok");
				map.put("recordNo", record.getRecordNo());
			} else {
				map.put("isOK", "no");
			}

			return map;
		} catch (Exception e) {
			throw new WebException(e);
		}
	}

	/**
	 * 申请招标备案合同
	 * 
	 * @param record
	 * @param map
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("/zbApplyRecord")
	public @ResponseBody Map<String, String> zbApplyRecord(RecordEntity record,
			HttpSession session) throws WebException {
		try {
			Map<String, String> map = new HashMap<String, String>();
			SupervisorPrincipal userBean = (SupervisorPrincipal) session
					.getAttribute("userinfo");
			if (userBean.getMember().getCheckState().getId() == 2) {
				record.setApplyId(userBean.getMember().getMemberNo());
				record.setApplyName(userBean.getMember().getName());
				record.setState(RecordStateEnum.noBinding);
				record.setType(RecordTypeEnum.contract);
				record.setApplyDate(new Date());
				record.setDelState(false);
				record.setConfirmState(RecordConfirmStateEnum.accepted);
				record.setContractType(ContractTypeEnum.bidding);// 合同类型
				record.setFlag(RecordFlagEnum.B);
				recordService.addRecord(record);
				map.put("isOK", "ok");
				map.put("recordNo", record.getRecordNo());
			} else {
				map.put("isOK", "no");
			}
			return map;
		} catch (Exception e) {
			throw new WebException(e);
		}
	}

	/**
	 * 乙方申请采购备案合同
	 * 
	 * @param record
	 * @param map
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("/BzbApplyRecord")
	public @ResponseBody Map<String, String> BzbApplyRecord(
			RecordEntity record, HttpSession session) throws WebException {
		try {
			Map<String, String> map = new HashMap<String, String>();
			SupervisorPrincipal userBean = (SupervisorPrincipal) session
					.getAttribute("userinfo");
			if (userBean.getMember().getCheckState().getId() == 2) {
				MemberEntity member = memberService.memberInfo(record
						.getMemberIdB());
				record.setApplyId(userBean.getMember().getMemberNo());
				record.setApplyName(userBean.getMember().getName());
				record.setState(RecordStateEnum.noBinding);
				record.setType(RecordTypeEnum.contract);
				record.setApplyDate(new Date());
				record.setDelState(false);
				if (member.getType().getId() == 1) {
					record.setContractType(ContractTypeEnum.glass);// 合同类型
				} else if (member.getType().getId() == 2) {
					record.setContractType(ContractTypeEnum.extrusions);// 合同类型
				}
				record.setFlag(RecordFlagEnum.B);
				record.setConfirmState(RecordConfirmStateEnum.accepted);
				recordService.addRecord(record);
				map.put("isOK", "ok");
				map.put("recordNo", record.getRecordNo());
			} else {
				map.put("isOK", "no");
			}
			return map;
		} catch (Exception e) {
			throw new WebException(e);
		}
	}

	@RequestMapping("/to_modify")
	public String to_modify(String recordId, ModelMap map, HttpSession session)
			throws WebException {
		try {
			RecordEntity record = recordService.getRecord(recordId);
			SupervisorPrincipal member = (SupervisorPrincipal) session
					.getAttribute("userinfo");
			if (record.getType().getId() == 2) {
				List<ContractBatchEntity> batch = recordService
						.getBatchList(recordId);
				map.put("batch", batch);
			}
			map.put("record", record);// 备案类型
			map.put("type", member.getMember().getType().getId());// 会员类型
			return "site/record/edit-record";
		} catch (Exception e) {
			throw new WebException(e);
		}
	}

	@RequestMapping("/modifyRecord")
	public @ResponseBody Map<String, String> modifyRecord(String recordId,
			String imgPath, String RFID, String flag) throws WebException {
		try {
			RecordEntity record = new RecordEntity();
			if (recordId != "" && recordId != null) {
				record = recordService.getRecord(recordId);
				record.setId("");
			}
			if (imgPath != "" && imgPath != null) {
				record.setImgPath(imgPath);
			}
			if (RFID != "" && RFID != null) {
				record.setRfidNo(RFID);
			}
			if (flag.equals("1")) {
				record.setType(RecordTypeEnum.change);
				record.setState(RecordStateEnum.nochange);
			} else if (flag.equals("2")) {
				record.setType(RecordTypeEnum.supplement);
				record.setState(RecordStateEnum.nosupplement);
			}
			record.setConfirmState(RecordConfirmStateEnum.accepted);
			recordService.saveRecord(record);
			Map<String, String> map = new HashMap<String, String>();
			map.put("isOK", "ok");
			return map;
		} catch (Exception e) {
			throw new WebException(e);
		}
	}

	@RequestMapping("/modify")
	public @ResponseBody Map<String, String> modify(RecordEntity record)
			throws WebException {
		try {
			recordService.modifyRecord(record);
			Map<String, String> map = new HashMap<String, String>();
			map.put("isOK", "ok");
			return map;
		} catch (Exception e) {
			throw new WebException(e);
		}
	}

	/**
	 * 根据ID删除备案
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delRecord")
	public @ResponseBody Map<String, String> delRecord(String id) {
		Map<String, String> map = new HashMap<String, String>();
		recordService.deleteRecord(id);
		map.put("isOK", "ok");
		return map;
	}

	/**
	 * 跳转确认合同
	 * 
	 * @param model
	 * @param contractNo
	 * @param recordNo
	 * @param session
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("confirm")
	public String confirm(ModelMap model, String contractNo, String recordId,
			HttpSession session, String message) throws WebException {
		try {
			SupervisorPrincipal member = (SupervisorPrincipal) session
					.getAttribute("userinfo");
			ContractModel contract = contractService
					.getContractModelByContractNo(contractNo);
			ContractModel contractModel = new ContractModel();
			if (contract.getContract() != null) {
				contractModel = contractService.getContract(contract
						.getContract().getId());
			}
			RecordEntity record = recordService.getRecord(recordId);
			model.put("contractModel", contractModel);
			model.put("recordId", recordId);
			model.put("message", message);
			model.put("title", record.getType().getId());
			model.put("type", member.getMember().getType().getId());
			return "site/record/contract-confirm";
		} catch (Exception e) {
			SxjLogger.error("查询合同信息错误", e, this.getClass());
			throw new WebException("查询合同信息错误");
		}
	}

	/**
	 * 跳转确认合同
	 * 
	 * @param model
	 * @param contractNo
	 * @param recordNo
	 * @param session
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("confirm-kfs")
	public String confirmkfs(ModelMap model, String contractNo,
			String recordId, HttpSession session, String message)
			throws WebException {
		try {

			SupervisorPrincipal member = (SupervisorPrincipal) session
					.getAttribute("userinfo");
			ContractModel contract = contractService
					.getContractModelByContractNo(contractNo);
			ContractModel contractModel = new ContractModel();
			if (contract.getContract() != null) {
				contractModel = contractService.getContract(contract
						.getContract().getId());
			}
			model.put("contractModel", contractModel);
			model.put("recordId", recordId);
			model.put("message", message);
			model.put("type", member.getMember().getType().getId());
			return "site/record/cont-developers";
		} catch (Exception e) {
			SxjLogger.error("查询合同信息错误", e, this.getClass());
			throw new WebException("查询合同信息错误");
		}
	}

	@RequestMapping("confirmRecord")
	public @ResponseBody Map<String, String> confirmRecord(String recordId,
			String contractId, HttpSession session) throws WebException {
		try {
			Map<String, String> map = new HashMap<String, String>();
			SupervisorPrincipal member = (SupervisorPrincipal) session
					.getAttribute("userinfo");
			if (member.getMember().getType().getId() == 0) {
				recordService.modifyState(contractId, recordId,
						RecordConfirmStateEnum.confirmedA);
			} else {
				recordService.modifyState(contractId, recordId,
						RecordConfirmStateEnum.confirmedB);
			}
			String key = MessageChannel.WEBSITE_RECORD_MESSAGE
					+ member.getMember().getMemberNo();
			List<String> messageList = CometServiceImpl.get(key);
			if (messageList != null && messageList.size() > 0) {
				for (int i = 0; i < messageList.size(); i++) {
					String message = messageList.get(i);
					if (message.contains(recordId)) {
						CometServiceImpl.remove(key, message);
					}
				}
			}
			map.put("isOK", "ok");
			return map;
		} catch (Exception e) {
			SxjLogger.error("确认备案信息错误", e, this.getClass());
			throw new WebException("确认备案信息错误");
		}
	}

	/**
	 * 获取备案批次
	 * 
	 * @param recordId
	 * @param contractId
	 * @param session
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("getBatch")
	public @ResponseBody Map<String, String> getBatch(String recordId)
			throws WebException {
		try {
			Map<String, String> map = new HashMap<String, String>();
			String batch = recordService.getBatch(recordId);
			if (batch != "") {
				map.put("batch", batch);
			} else {
				map.put("batch", "");
			}
			return map;
		} catch (Exception e) {
			SxjLogger.error("确认备案信息错误", e, this.getClass());
			throw new WebException("确认备案信息错误");
		}
	}

	@RequestMapping("getRfid")
	public @ResponseBody Map<String, String> getRfid(String batchId)
			throws WebException {
		try {
			Map<String, String> map = new HashMap<String, String>();
			String rfid = recordService.getRfid(batchId);
			if (rfid != "") {
				map.put("rfid", rfid);
			} else {
				map.put("rfid", "");
			}
			return map;
		} catch (Exception e) {
			SxjLogger.error("确认备案信息错误", e, this.getClass());
			throw new WebException("确认备案信息错误");
		}
	}

	@RequestMapping("getContract")
	public @ResponseBody Map<String, String> getContract(String contractNo,
			HttpSession session) throws WebException {
		try {
			Map<String, String> map = new HashMap<String, String>();
			SupervisorPrincipal member = (SupervisorPrincipal) session
					.getAttribute("userinfo");
			int size = contractService.getContractByZhaobiaoContractNo(
					contractNo.trim(), member.getMember());
			if (size == 0) {
				map.put("isOK", "no");
			} else {
				map.put("isOK", "ok");
			}

			return map;
		} catch (Exception e) {
			SxjLogger.error("确认备案信息错误", e, this.getClass());
			throw new WebException("确认备案信息错误");
		}
	}

	/**
	 * 备案是否可修改
	 * 
	 * @param contractNo
	 * @param session
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("getRecordState")
	public @ResponseBody Map<String, String> getRecordState(String id,
			HttpSession session) throws WebException {
		try {
			Map<String, String> map = new HashMap<String, String>();
			RecordEntity re = recordService.getRecord(id);
			if (re != null) {
				if (re.getType().getId() == 0) {
					if (StringUtils.isEmpty(re.getContractNo())) {
						map.put("isOK", "ok");
					} else {
						map.put("isOK", "no");
					}
				} else if (re.getType().getId() == 1) {
					if (re.getState().getId() == 2) {
						map.put("isOK", "ok");
					} else {
						map.put("isOK", "no");
					}
				} else if (re.getType().getId() == 2) {
					if (re.getState().getId() == 4) {
						map.put("isOK", "ok");
					} else {
						map.put("isOK", "no");
					}
				}
			} else {
				map.put("isOK", "del");
			}
			return map;
		} catch (Exception e) {
			SxjLogger.error("确认备案信息错误", e, this.getClass());
			throw new WebException("确认备案信息错误");
		}
	}

	@RequestMapping("getMember")
	public @ResponseBody Map<String, String> getMember(String memberName,
			HttpSession session) throws WebException {
		try {
			Map<String, String> map = new HashMap<String, String>();
			MemberEntity member = memberService.getMemberByName(memberName);
			if (member != null) {
				map.put("memberNo", member.getMemberNo());
			} else {
				map.put("memberNo", "");
			}
			return map;
		} catch (Exception e) {
			SxjLogger.error("查询会员信息错误", e, this.getClass());
			throw new WebException("查询会员信息错误");
		}
	}
}
