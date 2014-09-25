package com.sxj.supervisor.manage.controller.rfid.lableManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.entity.rfid.ref.LogisticsRefEntity;
import com.sxj.supervisor.enu.rfid.ref.AssociationTypesEnum;
import com.sxj.supervisor.enu.rfid.ref.AuditStateEnum;
import com.sxj.supervisor.enu.rfid.window.RfidTypeEnum;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.rfid.ref.LogisticsRefQuery;
import com.sxj.supervisor.service.rfid.ref.ILogisticsRefService;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

/**
 * 物流标签管理
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/rfid/lableManage")
public class LableManageController extends BaseController {

	@Autowired
	private ILogisticsRefService refService;

	@RequestMapping("lable_list")
	public String lable_list(ModelMap map, LogisticsRefQuery query)
			throws WebException {
		try {
			if (query != null) {
				query.setPagable(true);
			}
			AssociationTypesEnum[] types = AssociationTypesEnum.values();
			AuditStateEnum[] states = AuditStateEnum.values();
			RfidTypeEnum[] rfidtypes = RfidTypeEnum.values();
			LogisticsRefEntity model = new LogisticsRefEntity();
			model.setType(AssociationTypesEnum.APPLY);
			List<LogisticsRefEntity> list = new ArrayList<LogisticsRefEntity>();
			list.add(model);
			map.put("types", types);
			map.put("states", states);
			map.put("rfidtypes", rfidtypes);
			map.put("query", query);
			map.put("list", list);

		} catch (Exception e) {
			SxjLogger.error("查询错误", e, this.getClass());
			throw new WebException("查询错误");
		}
		return "manage/rfid/gysrfid-link/gysrfid-link";
	}

	@RequestMapping("del")
	public @ResponseBody Map<String, String> del(String id) throws WebException {
		try {
			Map<String, String> map = new HashMap<String, String>();
			refService.del(id);
			map.put("isOk", "ok");
			return map;
		} catch (Exception e) {
			SxjLogger.error("删除错误", e, this.getClass());
			throw new WebException("删除错误");
		}
	}

	/**
	 * 审核状态 更改
	 * 
	 * @param ref
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("review")
	public @ResponseBody Map<String, String> review(LogisticsRefEntity ref)
			throws WebException {
		try {
			Map<String, String> map = new HashMap<String, String>();
			refService.update(ref);
			map.put("isOk", "ok");
			return map;
		} catch (Exception e) {
			SxjLogger.error("审核状态更改错误", e, this.getClass());
			throw new WebException("审核状态更改错误");
		}
	}
}
