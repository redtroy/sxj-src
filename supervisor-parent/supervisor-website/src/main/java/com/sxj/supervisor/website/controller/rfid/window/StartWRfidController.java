package com.sxj.supervisor.website.controller.rfid.window;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.entity.contract.ContractEntity;
import com.sxj.supervisor.enu.record.ContractTypeEnum;
import com.sxj.supervisor.enu.rfid.window.WindowTypeEnum;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.model.contract.ContractQuery;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.supervisor.service.rfid.window.IWindowRfidService;
import com.sxj.supervisor.website.controller.BaseController;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/rfid/window")
public class StartWRfidController extends BaseController {
	@Autowired
	private IWindowRfidService windowRfidService;

	@Autowired
	private IContractService contractService;

	/**
	 * 跳转标签启用
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("startmrfid_list")
	public String startmrfid_list(ModelMap map) {
		WindowTypeEnum[] type = WindowTypeEnum.values();
		map.put("type", type);
		return "site/rfid/window/startmrfid";
	}

	@RequestMapping("queryRefContract")
	public @ResponseBody Map<Object, Object> query(ContractQuery query, Long num)
			throws WebException {
		Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			ContractModel contract = contractService
					.getContractModelByContractNo(query.getRefContractNo());
			if (contract == null) {
				throw new WebException("招标合同不存在");
			}
			if (!contract.getContract().getType()
					.equals(ContractTypeEnum.bidding)) {
				throw new WebException("此合同不是招标合同");
			}
			float itemQuantity = contract.getContract().getItemQuantity();
			float hasStartQuantity = contract.getContract().getUseQuantity();

			if (hasStartQuantity >= itemQuantity) {
				throw new WebException("此招标合同已经全部启用完毕");
			}
			if (num > (itemQuantity - hasStartQuantity)) {
				throw new WebException("此招标合同未启用数量为："
						+ (itemQuantity - hasStartQuantity));
			}
			List<ContractModel> list = contractService.queryContracts(query);
			if (list.size() > 0) {
				String[] bq = windowRfidService.getMaxRfidNo(
						query.getRefContractNo(), num);
				map.put("isOk", "ok");
				map.put("bq", bq);
				map.put("list", list);
			} else {
				map.put("isOk", "false");
				map.put("error", "此招标合同没有关联的采购合同");
			}
			return map;
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			map.put("error", e.getMessage());
		}
		return map;
	}

	@RequestMapping("query_contractNo")
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

		} catch (Exception e) {
			SxjLogger.error("根据合同号查询合同信息错误", e, this.getClass());
			map.put("error", e.getMessage());
		}
		return map;
	}

	/**
	 * 通过合同号联想查询
	 */
	@RequestMapping("lx_query")
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
	 * 启用标签
	 */
	@RequestMapping("start_lable")
	public @ResponseBody Map<Object, Object> start_lable(Integer startNum,
			String refContractNo, String minRfid, String maxRfid, String gRfid,
			String lRfid, WindowTypeEnum windowType) throws WebException {
		Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			contractService.startWindowRfid(startNum, refContractNo, minRfid,
					maxRfid, gRfid, lRfid, windowType);
			map.put("isOk", "ok");
		} catch (Exception e) {
			SxjLogger.error("启用标签错误", e, this.getClass());
			map.put("error", e.getMessage());
		}
		return map;
	}
}
