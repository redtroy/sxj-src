package com.sxj.supervisor.manage.controller.rfid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.entity.rfid.base.RfidSupplierEntity;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.rfid.base.RfidSupplierQuery;
import com.sxj.supervisor.service.rfid.base.IRfidSupplierService;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

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
			map.put("query", query);
		} catch (Exception e) {
			SxjLogger.error("查询供应商管理错误", e, this.getClass());
			throw new WebException("查询供应商管理错误");
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

	/**
	 * 保存供应商
	 */
	@RequestMapping("save_supplier")
	public @ResponseBody Map save_supplier(RfidSupplierEntity supplier)
			throws WebException {
		Map<String, String> map = new HashMap<String, String>();
		try {
			supplierService.add(supplier);
			map.put("isOk", "ok");
		} catch (Exception e) {
			SxjLogger.error("保存供应商错误", e, this.getClass());
			throw new WebException("保存供应商错误");
		}
		return map;
	}

	/**
	 * 删除供应商
	 * 
	 */
	@RequestMapping("del_supplier")
	public @ResponseBody Map del_supplier(String id) throws WebException {
		Map<String, String> map = new HashMap<String, String>();
		try {
			supplierService.delSupplier(id);
			map.put("isOk", "ok");
		} catch (Exception e) {
			SxjLogger.error("删除供应商错误", e, this.getClass());
			throw new WebException("删除供应商错误");
		}
		return map;
	}

	/**
	 * 编辑供应商页面
	 */
	@RequestMapping("edit_supplier")
	public String edit_supplier(ModelMap map, String id) throws WebException {
		try {
			RfidSupplierEntity supplier = supplierService.getSupplier(id);
			map.put("supplier", supplier);
		} catch (Exception e) {
			SxjLogger.error("编辑供应商页面错误", e, this.getClass());
			throw new WebException("编辑供应商页面错误");
		}
		return "manage/rfid/supplier/supplier-edit";
	}

	/**
	 * 更新供应商
	 */
	@RequestMapping("update_supplier")
	public @ResponseBody Map update_supplier(RfidSupplierEntity supplier)
			throws WebException {
		Map<String, String> map = new HashMap<String, String>();
		try {
			supplierService.modifySupplier(supplier);
			map.put("isOk", "ok");
		} catch (Exception e) {
			SxjLogger.error("删除供应商错误", e, this.getClass());
			throw new WebException("删除供应商错误");
		}
		return map;
	}
}
