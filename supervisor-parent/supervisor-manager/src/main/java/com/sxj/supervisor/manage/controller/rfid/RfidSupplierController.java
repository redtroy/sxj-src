package com.sxj.supervisor.manage.controller.rfid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.service.rfid.base.IRfidSupplierService;

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
	public String supplier_list() {

		return "manage/rfid/supplier/supplier";
	}

	/**
	 * 增加供应商页面
	 */
	@RequestMapping("add_supplier")
	public String add_supplier() {
		return "manage/rfid/supplier/supplier-add";
	}
}
