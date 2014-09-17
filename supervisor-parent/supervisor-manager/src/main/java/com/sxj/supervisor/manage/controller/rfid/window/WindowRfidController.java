package com.sxj.supervisor.manage.controller.rfid.window;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.entity.rfid.window.WindowRfidEntity;
import com.sxj.supervisor.enu.rfid.window.LabelProgressEnum;
import com.sxj.supervisor.enu.rfid.window.RfidStateEnum;
import com.sxj.supervisor.enu.rfid.window.WindowTypeEnum;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.rfid.window.WindowRfidQuery;
import com.sxj.supervisor.service.rfid.window.IWindowRfidService;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/rfid/window")
public class WindowRfidController extends BaseController {
	@Autowired
	private IWindowRfidService windowRfidService;

	@RequestMapping("query")
	public String queryWindowRfid(WindowRfidQuery query, ModelMap model)
			throws WebException {
		try {
			query.setPagable(true);
			List<WindowRfidEntity> winList = windowRfidService
					.queryWindowRfid(query);
			LabelProgressEnum[] Label = LabelProgressEnum.values();
			RfidStateEnum[] rfid = RfidStateEnum.values();
			WindowTypeEnum[] winType = WindowTypeEnum.values();
			model.put("Label", Label);
			model.put("rfid", rfid);
			model.put("winType", winType);
			model.put("query", query);
			model.put("winList", winList);
			return "manage/rfid/window/windowRfid-list";
		} catch (Exception e) {
			SxjLogger.error("查询门窗RFID错误", e, this.getClass());
			throw new WebException("查询门窗RFID错误");
		}
	}
	@RequestMapping("delete")
	public @ResponseBody Map<String, String> delete(String id, ModelMap model)
			throws WebException {
		try {
			WindowRfidEntity win = new WindowRfidEntity();
			win.setId(id);
			win.setRfidState(RfidStateEnum.delete);
			windowRfidService.updateWindowRfid(win);
			Map<String, String> map = new HashMap<String, String>();
			map.put("isOK", "ok");
			return map;
		} catch (Exception e) {
			SxjLogger.error("删除门窗RFID错误", e, this.getClass());
			throw new WebException("删除门窗RFID错误");
		}
	}
}
