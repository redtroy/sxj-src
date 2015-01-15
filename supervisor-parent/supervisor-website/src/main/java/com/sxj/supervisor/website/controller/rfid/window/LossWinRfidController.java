package com.sxj.supervisor.website.controller.rfid.window;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.entity.contract.ContractEntity;
import com.sxj.supervisor.entity.contract.ContractItemEntity;
import com.sxj.supervisor.entity.rfid.window.WindowRfidEntity;
import com.sxj.supervisor.enu.record.ContractTypeEnum;
import com.sxj.supervisor.enu.rfid.RfidStateEnum;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.model.contract.ContractQuery;
import com.sxj.supervisor.model.contract.ContractReplenishModel;
import com.sxj.supervisor.model.contract.ReplenishBatchModel;
import com.sxj.supervisor.model.login.SupervisorPrincipal;
import com.sxj.supervisor.model.rfid.window.WindowRfidQuery;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.supervisor.service.rfid.window.IWindowRfidService;
import com.sxj.supervisor.website.controller.BaseController;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/rfid/window")
public class LossWinRfidController extends BaseController {

	@Autowired
	private IWindowRfidService windowRfidService;

	@Autowired
	private IContractService contractService;

	@RequestMapping("to_loss")
	public String lossMen(ModelMap map) {
		return "site/rfid/window/lossrfid";
	}

	@RequestMapping("queryLossContract")
	public @ResponseBody Map<Object, Object> query(ContractQuery query,
			Long num, HttpSession session) throws WebException {
		Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			SupervisorPrincipal userInfo = getLoginInfo(session);
			ContractModel contract = contractService
					.getContractModelByContractNo(query.getRefContractNo());
			if (contract == null) {
				throw new WebException("招标合同不存在");
			}
			if (!contract.getContract().getType()
					.equals(ContractTypeEnum.bidding)) {
				throw new WebException("此合同不是招标合同");
			}
			if (!userInfo.getMember().getMemberNo()
					.equals(contract.getContract().getMemberIdB())) {
				throw new WebException("此合同属于其他会员");
			}
			float hasStartQuantity = contract.getContract().getUseQuantity();
			float unStartQuantity = 0f;
			if (num > hasStartQuantity) {
				throw new WebException("此招标合同可以补损的最大数量为："
						+ (long) hasStartQuantity);
			}

			WindowRfidQuery winQuery = new WindowRfidQuery();
			winQuery.setContractNo(query.getRefContractNo());
			winQuery.setRfidState(RfidStateEnum.UN_USED.getId());
			List<WindowRfidEntity> winList = windowRfidService
					.queryWindowRfid(winQuery);
			if (winList != null) {
				unStartQuantity = unStartQuantity + winList.size();
			}
			winQuery.setRfidState(RfidStateEnum.DISABLE.getId());
			winList = windowRfidService.queryWindowRfid(winQuery);
			if (winList != null) {
				unStartQuantity = unStartQuantity + winList.size();
			}
			if (unStartQuantity < num) {
				throw new WebException("此招标合同未启用的RFID数量为："
						+ (long) unStartQuantity + "，请申请足够的RFID数量");
			}
			List<ContractModel> list = contractService.queryContracts(query);
			if (list.size() > 0) {
				// String[] bq = windowRfidService.getLossMaxRfidNo(
				// query.getRefContractNo(), num);
				// map.put("bq", bq);
				map.put("isOk", "ok");
				map.put("list", list);
			} else {
				map.put("isOk", "false");
			}
			return map;
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			map.put("error", e.getMessage());
		}
		return map;
	}

	@RequestMapping("queryLossRfid")
	public @ResponseBody Map<Object, Object> queryLossRfid(String rfidNo,
			String refContractNo) throws WebException {
		Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			if (StringUtils.isEmpty(rfidNo)) {
				throw new ServiceException("请输入被补损的RFID号");
			}
			WindowRfidEntity oldRfid = windowRfidService
					.getWindowRfidByNo(rfidNo);
			if (oldRfid == null) {
				throw new ServiceException("该标签不是认证标签");
			}
			if (!oldRfid.getRfidState().equals(RfidStateEnum.USED)) {
				throw new ServiceException("该被补损RFID不是已使用状态");
			}
			if (!oldRfid.getContractNo().equals(refContractNo)) {
				throw new ServiceException("该被补损RFID不属于该招标合同");
			}
			map.put("isOk", "ok");
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			map.put("error", e.getMessage());
		}
		return map;
	}

	@RequestMapping("query_loss_contractNo")
	public @ResponseBody Map<Object, Object> query_contractNo(String contractNo)
			throws WebException {
		Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			ContractModel cm = contractService
					.getContractModelByContractNo(contractNo);
			if (cm != null) {
				map.put("isOk", "ok");
				map.put("cm", cm);
			} else {
				map.put("isOk", "false");
			}
			List<ReplenishBatchModel> itemList = new ArrayList<>();
			List<ContractReplenishModel> replenishList = cm.getReplenishList();
			if (replenishList != null && replenishList.size() > 0) {
				for (ContractReplenishModel replenishModel : replenishList) {
					List<ReplenishBatchModel> item = replenishModel
							.getBatchItems();
					if (item == null) {
						continue;
					}
					itemList.addAll(item);
				}
			}
			map.put("batchItems", itemList);
		} catch (Exception e) {
			SxjLogger.error("根据合同号查询合同信息错误", e, this.getClass());
			map.put("error", e.getMessage());
		}
		return map;
	}

	/**
	 * 通过合同号联想查询
	 */
	@RequestMapping("lx_loss_query")
	public @ResponseBody Map<Object, Object> lx_query(String keyword)
			throws WebException {
		Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			List<ContractEntity> list = contractService
					.getContractByRefContractNo(keyword);
			List<Map<String, String>> addlist = new ArrayList<Map<String, String>>();
			for (ContractEntity contractEntity : list) {
				Map<String, String> cmap = new HashMap<String, String>();
				cmap.put("title", contractEntity.getRefContractNo());
				addlist.add(cmap);
			}
			map.put("data", addlist);

		} catch (Exception e) {
			SxjLogger.error("联想查询错误", e, this.getClass());
			map.put("error", e.getMessage());
		}
		return map;
	}

	/**
	 * 补损标签
	 */
	@RequestMapping("start_loss_lable")
	public @ResponseBody Map<Object, Object> start_lable(String refContractNo,
			Integer count, String gRfid, String lRfid, String[] addRfid)
			throws WebException {
		Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			ContractModel refContract = contractService
					.getContractModelByContractNo(refContractNo);
			if (refContract == null) {
				throw new WebException("招标合同不存在");
			}
			List<ContractItemEntity> items = refContract.getItemList();
			if (items == null || items.size() == 0) {
				throw new WebException("招标合同条目不存在");
			}
			// WindowRfidQuery query = new WindowRfidQuery();
			// for (String rfid : addRfid) {
			// query.setRfidNo(rfid);
			// List<WindowRfidEntity> list = windowRfidService
			// .queryWindowRfid(query);
			// if (list.size() == 1) {
			// if (list.get(0).getRfidState().getId() == 1) {
			// continue;
			// } else {
			// throw new WebException(rfid + "补损标签状态错误");
			// }
			// } else {
			// throw new WebException(rfid + "补损标签不存在，或者重复");
			// }
			// }
			// float quantity = 0f;
			// for (Iterator<ContractItemEntity> iterator = items.iterator();
			// iterator
			// .hasNext();) {
			// ContractItemEntity item = iterator.next();
			// if (item == null) {
			// continue;
			// }
			// quantity = quantity + item.getQuantity();
			// }
			windowRfidService.lossWindowRfid(refContractNo, count, gRfid,
					lRfid, addRfid);
			map.put("isOk", "ok");
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			map.put("error", e.getMessage());
		}
		return map;
	}
}
