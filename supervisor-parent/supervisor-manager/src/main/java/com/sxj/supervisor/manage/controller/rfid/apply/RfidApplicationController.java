package com.sxj.supervisor.manage.controller.rfid.apply;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.entity.rfid.apply.RfidApplicationEntity;
import com.sxj.supervisor.enu.rfid.apply.PayStateEnum;
import com.sxj.supervisor.enu.rfid.apply.ReceiptStateEnum;
import com.sxj.supervisor.enu.rfid.apply.RfidTypeEnum;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.rfid.app.RfidApplicationQuery;
import com.sxj.supervisor.service.rfid.app.IRfidApplicationService;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/rfid/apply")
public class RfidApplicationController extends BaseController {
	@Autowired
	IRfidApplicationService sppService;

	@RequestMapping("appList")
	public String appList(ModelMap map, RfidApplicationQuery query)
			throws WebException {
		try {
			List<RfidApplicationEntity> list = sppService.query(query);
			PayStateEnum[] paystates = PayStateEnum.values();
			ReceiptStateEnum[] receiptStates = ReceiptStateEnum.values();
			RfidTypeEnum[] types = RfidTypeEnum.values();
			map.put("list", list);
			map.put("paystates", paystates);
			map.put("receiptStates", receiptStates);
			map.put("types", types);

		} catch (Exception e) {
			SxjLogger.error("申请单查询错误", e, this.getClass());
			throw new WebException("申请单查询错误");
		}
		return "manage/rfid/order/order";
	}
}
