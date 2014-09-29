package com.sxj.supervisor.manage.controller.rfid.windowRef;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.entity.rfid.windowRef.WindowRefEntity;
import com.sxj.supervisor.enu.rfid.ref.AuditStateEnum;
import com.sxj.supervisor.enu.rfid.window.WindowTypeEnum;
import com.sxj.supervisor.enu.rfid.windowRef.LinkStateEnum;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.contract.ContractBatchModel;
import com.sxj.supervisor.model.rfid.windowRef.WindowRefQuery;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.supervisor.service.rfid.windowRef.IWindowRfidRefService;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/rfid/window-link")
public class WindowRefController extends BaseController {
	@Autowired
	private IWindowRfidRefService windowRefService;
	
	@Autowired
	private IContractService contractService;
	/**
	 * 查询列表
	 * @param query
	 * @param model
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("query")
	public String queryWindowRfid(WindowRefQuery query, ModelMap model)
			throws WebException {
		try {
			query.setPagable(true);
			List<WindowRefEntity> winList = windowRefService.queryWindowRfidRef(query);
			AuditStateEnum[] state = AuditStateEnum.values();
			LinkStateEnum[] type = LinkStateEnum.values();
			WindowTypeEnum[] winType = WindowTypeEnum.values();
			model.put("state", state);
			model.put("type", type);
			model.put("winType", winType);
			model.put("query", query);
			model.put("winList", winList);
			return "manage/rfid/windowRef/window-link";
		} catch (Exception e) {
			SxjLogger.error("查询门窗RFID关联错误", e, this.getClass());
			throw new WebException("查询门窗RFID关联错误");
		}
	}
	/**
	 * 删除
	 * @param id
	 * @param model
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("delete")
	public @ResponseBody Map<String, String> delete(String id, ModelMap model)
			throws WebException {
		try {
			WindowRefEntity win = new WindowRefEntity();
			win.setId(id);
			win.setDelState(true);
			windowRefService.updateWindowRfidRef(win);
			Map<String, String> map = new HashMap<String, String>();
			map.put("isOK", "ok");
			return map;
		} catch (Exception e) {
			SxjLogger.error("删除门窗RFID关联错误", e, this.getClass());
			throw new WebException("删除门窗RFID关联错误");
		}
	}
	/**
	 * 审核
	 * @param id
	 * @param model
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("audit")
	public @ResponseBody Map<String, String> audit(String id, ModelMap model)
			throws WebException {
		try {
			WindowRefEntity win = new WindowRefEntity();
			win.setId(id);
			win.setState(AuditStateEnum.noapproval);
			windowRefService.updateWindowRfidRef(win);
			Map<String, String> map = new HashMap<String, String>();
			map.put("isOK", "ok");
			return map;
		} catch (Exception e) {
			SxjLogger.error("停用门窗RFID错误", e, this.getClass());
			throw new WebException("停用门窗RFID错误");
		}
	}
	/**
	 * 详情
	 * @param id
	 * @param model
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("info")
	public String getInfo(String id,int type, ModelMap model)
			throws WebException {
		try {
			WindowRefEntity win = windowRefService.getWindowRfidRef(id);
			WindowTypeEnum[] windowType = WindowTypeEnum.values();
			model.put("type", windowType);
			model.put("win", win);
			model.put("id", id);
			if(type==0){
				return "manage/rfid/windowRef/window-link-info";
			}else if(type==1){
				return "manage/rfid/windowRef/rfid-modify";
			}else{
				return "manage/rfid/windowRef/window-rfid-modify";
			}
			
		} catch (Exception e) {
			SxjLogger.error("获取门窗RFID错误", e, this.getClass());
			throw new WebException("获取门窗RFID错误");
		}
	}
	
	/**
	 * 修改
	 * @param id
	 * @param model
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("saveModify")
	public @ResponseBody Map<String, String> saveModify(WindowRefEntity win)
			throws WebException {
		try {
			windowRefService.updateWindowRfidRef(win);
			Map<String, String> map = new HashMap<String, String>();
			map.put("isOK", "ok");
			return map;
		} catch (Exception e) {
			SxjLogger.error("修改门窗RFID错误", e, this.getClass());
			throw new WebException("修改门窗RFID错误");
		}
	}
	@RequestMapping("contractBatch")
	public String getContractBatch(ModelMap model, String contractNo,String rfidNo,String id)
			throws WebException {
		try {
			List<ContractBatchModel> conBatch=contractService.getContractBatch(contractNo, rfidNo);
			model.put("conBatch", conBatch);
			model.put("id", id);
			model.put("contractNo", contractNo);
			return "manage/rfid/windowRef/contract-batch";
		} catch (Exception e) {
			SxjLogger.error("查询合同信息错误", e, this.getClass());
			throw new WebException("查询合同信息错误");
		}
	}
}