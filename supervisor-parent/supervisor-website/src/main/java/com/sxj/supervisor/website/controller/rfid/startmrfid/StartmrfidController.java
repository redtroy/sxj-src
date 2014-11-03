package com.sxj.supervisor.website.controller.rfid.startmrfid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.entity.contract.ContractEntity;
import com.sxj.supervisor.entity.contract.ContractItemEntity;
import com.sxj.supervisor.enu.rfid.window.WindowTypeEnum;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.model.contract.ContractQuery;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.supervisor.service.rfid.window.IWindowRfidService;
import com.sxj.supervisor.website.controller.BaseController;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/rfid")
public class StartmrfidController extends BaseController {
	@Autowired
	private IContractService contractService;

	@Autowired
	private IWindowRfidService wind;

	@RequestMapping("startmrfid_list")
	public String startmrfid_list(ModelMap map) {
		WindowTypeEnum[] type = WindowTypeEnum.values();
		map.put("type", type);
		return "site/rfid/startmrfid/startmrfid";
	}

	@RequestMapping("query")
	public @ResponseBody Map<Object, Object> query(ContractQuery query, Long num)
			throws WebException {
		try {
			List<ContractModel> list = contractService.queryContracts(query);
			Map<Object, Object> map = new HashMap<Object, Object>();
			if (list.size() > 0) {
				String[] bq = wind.getMaxRfidNo(query.getRefContractNo(), num);
				map.put("isOk", "ok");
				map.put("bq", bq);
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

	/**
	 * 启用标签
	 */
	@RequestMapping("start_lable")
	public @ResponseBody Map<Object, Object> start_lable(String refContractNo,
			String minRfid, String maxRfid, String gRfid, String lRfid,
			WindowTypeEnum windowType) throws WebException {
		try {
			Map<Object, Object> map = new HashMap<Object, Object>();
			ContractModel refContract = contractService
					.getContractModelByContractNo(refContractNo);
			if (refContract == null) {
				map.put("isOk", "no");
				return map;
			}
			List<ContractItemEntity> items = refContract.getItemList();
			if (items == null || items.size() == 0) {
				map.put("isOk", "no");
				return map;
			}
			float quantity = 0f;
			for (Iterator<ContractItemEntity> iterator = items.iterator(); iterator
					.hasNext();) {
				ContractItemEntity item = iterator.next();
				if (item == null) {
					continue;
				}
				quantity = quantity + item.getQuantity();

			}
			long count = Long.parseLong(quantity + "");
			wind.startWindowRfid(count, refContractNo, minRfid, maxRfid, gRfid,
					lRfid, windowType);
			map.put("isOk", "ok");
			return map;
		} catch (Exception e) {
			SxjLogger.error("启用标签错误", e, this.getClass());
			throw new WebException("启用标签错误");
		}
	}
}
