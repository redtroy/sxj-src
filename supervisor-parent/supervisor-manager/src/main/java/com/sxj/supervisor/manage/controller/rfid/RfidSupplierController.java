package com.sxj.supervisor.manage.controller.rfid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.manage.controller.BaseController;

@Controller
@RequestMapping("/rfid/supplier")
public class RfidSupplierController extends BaseController {
	@Autowired
	// IRfidSupplierService supplierService;
	@RequestMapping("supplier_list")
	public String supplier_list() {

		return "site/rfid/supplier/supplier";
	}

}
