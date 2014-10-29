package com.sxj.supervisor.website.controller.rfid.startmrfid;

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
import com.sxj.supervisor.enu.rfid.window.WindowTypeEnum;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.model.contract.ContractQuery;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.supervisor.website.controller.BaseController;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/rfid")
public class StartmrfidController extends BaseController {
	@Autowired
	private IContractService contractService;

	@RequestMapping("startmrfid_list")
	public String startmrfid_list(ModelMap map) {
		WindowTypeEnum[] type = WindowTypeEnum.values();
		map.put("type", type);
		return "site/rfid/startmrfid/startmrfid";
	}

	@RequestMapping("query")
	public @ResponseBody Map<Object, Object> query(ContractQuery query)
			throws WebException {
		try {
			List<ContractModel> list = contractService.queryContracts(query);
			Map<Object, Object> map = new HashMap<Object, Object>();
			if (list.size() > 0) {
				map.put("isOk", "ok");
				map.put("list", list);
			} else {
				map.put("isOk", "false");
			}
			return map;
		} catch (Exception e) {
			SxjLogger.error("查询合同信息错误", e, this.getClass());
			throw new WebException("查询合同信息错误");
		}
	}

	@RequestMapping("query_contractNo")
	public @ResponseBody Map<Object, Object> query_contractNo(String contractNo)
			throws WebException {
		try {
			Map<Object, Object> map = new HashMap<Object, Object>();
			ContractModel cm = contractService
					.getContractModelByContractNo(contractNo);
			if (cm != null) {
				map.put("isOk", "ok");
				map.put("cm", cm);
			} else {
				map.put("isOk", "false");
			}
			return map;
		} catch (Exception e) {
			SxjLogger.error("根据合同号查询合同信息错误", e, this.getClass());
			throw new WebException("根据合同号查询合同信息错误");
		}
	}

	/**
	 * 通过合同号联想查询
	 */
	@RequestMapping("lx_query")
	public @ResponseBody Map<Object, Object> lx_query(String keyword)
			throws WebException {
		try {
			Map<Object, Object> map = new HashMap<Object, Object>();
			List<ContractEntity> list = contractService
					.getContractByRefContractNo(keyword);
			List<Map<String, String>> addlist = new ArrayList<Map<String, String>>();
			for (ContractEntity contractEntity : list) {
				Map<String, String> cmap = new HashMap<String, String>();
				cmap.put("title", contractEntity.getRefContractNo());
				addlist.add(cmap);
			}
			map.put("data", addlist);
			return map;
		} catch (Exception e) {
			SxjLogger.error("联想查询错误", e, this.getClass());
			throw new WebException("联想查询错误");
		}
	}
}
