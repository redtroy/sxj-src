package com.sxj.supervisor.manage.controller.rfid.purchase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.entity.rfid.purchase.RfidPurchaseEntity;
import com.sxj.supervisor.enu.rfid.purchase.DeliveryStateEnum;
import com.sxj.supervisor.enu.rfid.purchase.ImportStateEnum;
import com.sxj.supervisor.enu.rfid.purchase.PayStateEnum;
import com.sxj.supervisor.enu.rfid.window.RfidTypeEnum;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.rfid.purchase.PurchaseRfidQuery;
import com.sxj.supervisor.service.rfid.purchase.IPurchaseRfidService;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/rfid/purchase")
public class PurchaseRfidController extends BaseController{
	@Autowired
	private IPurchaseRfidService purchaseRfidService;
	
	/**
	 * 查询列表
	 * @param query
	 * @param model
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("query")
	public String queryWindowRfid(PurchaseRfidQuery query, ModelMap model)
			throws WebException {
		try {
			query.setPagable(true);
			List<RfidPurchaseEntity> list = purchaseRfidService.queryPurchase(query);
			DeliveryStateEnum[] delivery = DeliveryStateEnum.values();
			ImportStateEnum[] importState = ImportStateEnum.values();
			PayStateEnum[] payState = PayStateEnum.values();
			RfidTypeEnum[] type = RfidTypeEnum.values();
			model.put("delivery", delivery);
			model.put("importState", importState);
			model.put("payState", payState);
			model.put("type", type);
			model.put("query", query);
			model.put("list", list);
			return "manage/rfid/purchase/purchase-list";
		} catch (Exception e) {
			SxjLogger.error("查询采购单错误", e, this.getClass());
			throw new WebException("查询采购单错误");
		}
	}
}
