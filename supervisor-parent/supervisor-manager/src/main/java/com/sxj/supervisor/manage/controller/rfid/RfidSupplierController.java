package com.sxj.supervisor.manage.controller.rfid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.entity.rfid.base.RfidSupplierEntity;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.rfid.base.RfidSupplierQuery;
import com.sxj.supervisor.service.rfid.base.IRfidSupplierService;
import com.sxj.util.exception.WebException;

@Controller
@RequestMapping("/rfid/supplier")
public class RfidSupplierController extends BaseController {
	@Autowired
	IRfidSupplierService supplierService;

	/**
	 * 进入供应商管理页面
	 * 
	 * @return
	 */
	@RequestMapping("supplier_list")
	public String supplier_list(ModelMap map, RfidSupplierQuery query)
			throws WebException {
		try {
			if (query != null) {
				query.setPagable(true);
			}
			List<RfidSupplierEntity> list = supplierService
					.querySupplier(query);
			map.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "manage/rfid/supplier/supplier";
	}

	/**
	 * 增加供应商页面
	 */
	@RequestMapping("add_supplier")
	public String add_supplier() throws WebException {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "manage/rfid/supplier/supplier-add";
	}
}
