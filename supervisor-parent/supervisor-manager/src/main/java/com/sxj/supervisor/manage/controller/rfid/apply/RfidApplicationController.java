package com.sxj.supervisor.manage.controller.rfid.apply;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.cache.manager.HierarchicalCacheManager;
import com.sxj.supervisor.entity.rfid.apply.RfidApplicationEntity;
import com.sxj.supervisor.enu.rfid.apply.PayStateEnum;
import com.sxj.supervisor.enu.rfid.apply.ReceiptStateEnum;
import com.sxj.supervisor.enu.rfid.apply.RfidTypeEnum;
import com.sxj.supervisor.manage.comet.MessageChannel;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.rfid.app.RfidApplicationQuery;
import com.sxj.supervisor.service.rfid.app.IRfidApplicationService;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/rfid/apply")
public class RfidApplicationController extends BaseController {
	@Autowired
	IRfidApplicationService sppService;

	/**
	 * 获取申请单列表
	 * 
	 * @param map
	 * @param query
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("appList")
	public String appList(ModelMap map, RfidApplicationQuery query)
			throws WebException {
		try {
			if (query != null) {
				query.setPagable(true);
			}
			List<RfidApplicationEntity> list = sppService.query(query);
			PayStateEnum[] paystates = PayStateEnum.values();
			ReceiptStateEnum[] receiptStates = ReceiptStateEnum.values();
			RfidTypeEnum[] types = RfidTypeEnum.values();
			map.put("list", list);
			map.put("paystates", paystates);
			map.put("receiptStates", receiptStates);
			map.put("types", types);
			map.put("query", query);
			if (StringUtils.isNotEmpty(query.getIsDelMes())) {
				HierarchicalCacheManager.evict(2, "comet_message",
						MessageChannel.RFID_APPLY_MESSAGE);
			}
			registChannel(MessageChannel.RFID_APPLY_MESSAGE);
		} catch (Exception e) {
			SxjLogger.error("申请单查询错误", e, this.getClass());
			throw new WebException("申请单查询错误");
		}
		return "manage/rfid/order/order";
	}

	/**
	 * 修改申请单
	 * 
	 * @param apply
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("edit_apply")
	public @ResponseBody Map<String, String> edit(RfidApplicationEntity apply)
			throws WebException {
		Map<String, String> map = new HashMap<String, String>();
		try {
			sppService.updateApp(apply);
			map.put("isOk", "ok");
		} catch (Exception e) {
			SxjLogger.error("修改RFID申请单错误", e, this.getClass());
			throw new WebException("修改RFID申请单错误");
		}
		return map;
	}

	/**
	 * 删除申请单
	 * 
	 * @param apply
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("del_apply")
	public @ResponseBody Map<String, String> del(String id) throws WebException {
		Map<String, String> map = new HashMap<String, String>();
		try {
			sppService.delApp(id, null);
			map.put("isOk", "ok");
		} catch (Exception e) {
			SxjLogger.error("修改RFID申请单错误", e, this.getClass());
			throw new WebException("修改RFID申请单错误");
		}
		return map;
	}
}
