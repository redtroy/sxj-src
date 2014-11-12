package com.sxj.supervisor.manage.controller.rfid.pricemgr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.entity.rfid.sale.RfidPriceEntity;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.service.rfid.sale.IRfidPriceService;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/rfid/pricemgr")
public class PricemgrController extends BaseController {

	@Autowired
	private IRfidPriceService priceService;

	/**
	 * RFID销售价格管理
	 * 
	 * @return
	 */
	@RequestMapping("pricemgr_view")
	public String pricemgr_view(ModelMap map) throws WebException {
		try {
			List<RfidPriceEntity> list = priceService.queryPrice();
			if (list.size() > 0) {
				RfidPriceEntity price = list.get(0);
				map.put("model", price);
			} else {
				map.put("model", null);
			}
		} catch (Exception e) {
			SxjLogger.error("RFID销售价格管理错误", e, this.getClass());
			throw new WebException("RFID销售价格管理错误");
		}
		return "manage/rfid/pricemgr/pricemgr";
	}

	/**
	 * 新增价格
	 */
	@RequestMapping("add")
	public @ResponseBody Map<String, String> add(Double windowPrice,
			Double logisticsPrice) throws WebException {
		try {
			RfidPriceEntity price = new RfidPriceEntity();
			Map<String, String> map = new HashMap<String, String>();
			price.setWindowPrice(windowPrice);
			price.setLogisticsPrice(logisticsPrice);
			priceService.addPrice(price);
			map.put("isOk", "ok");
			return map;
		} catch (Exception e) {
			SxjLogger.error("新增价格错误", e, this.getClass());
			throw new WebException("新增价格错误");
		}
	}

	/**
	 * 更新价格
	 */
	@RequestMapping("update")
	public @ResponseBody Map<String, String> update(Double windowPrice,
			Double logisticsPrice, String id) throws WebException {
		try {
			RfidPriceEntity price = new RfidPriceEntity();
			Map<String, String> map = new HashMap<String, String>();
			price.setWindowPrice(windowPrice);
			price.setLogisticsPrice(logisticsPrice);
			price.setId(id);
			priceService.modifyPrice(price);
			map.put("isOk", "ok");
			return map;
		} catch (Exception e) {
			SxjLogger.error("更新价格错误", e, this.getClass());
			throw new WebException("更新价格错误");
		}
	}

}
