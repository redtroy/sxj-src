package com.sxj.supervisor.manage.controller.rfid.purchase;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.entity.rfid.apply.RfidApplicationEntity;
import com.sxj.supervisor.entity.rfid.base.RfidSupplierEntity;
import com.sxj.supervisor.entity.rfid.logistics.LogisticsRfidEntity;
import com.sxj.supervisor.entity.rfid.purchase.RfidPurchaseEntity;
import com.sxj.supervisor.entity.rfid.window.WindowRfidEntity;
import com.sxj.supervisor.enu.rfid.RfidTypeEnum;
import com.sxj.supervisor.enu.rfid.purchase.DeliveryStateEnum;
import com.sxj.supervisor.enu.rfid.purchase.ImportStateEnum;
import com.sxj.supervisor.enu.rfid.purchase.PayStateEnum;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.rfid.logistics.LogisticsRfidQuery;
import com.sxj.supervisor.model.rfid.purchase.PurchaseRfidQuery;
import com.sxj.supervisor.model.rfid.window.WindowRfidQuery;
import com.sxj.supervisor.service.member.IMemberService;
import com.sxj.supervisor.service.rfid.app.IRfidApplicationService;
import com.sxj.supervisor.service.rfid.base.IRfidSupplierService;
import com.sxj.supervisor.service.rfid.logistics.ILogisticsRfidService;
import com.sxj.supervisor.service.rfid.purchase.IPurchaseRfidService;
import com.sxj.supervisor.service.rfid.window.IWindowRfidService;
import com.sxj.util.common.FileUtil;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/rfid/purchase")
public class PurchaseRfidController extends BaseController {
	@Autowired
	private IPurchaseRfidService purchaseRfidService;

	@Autowired
	private IRfidApplicationService appService;

	@Autowired
	private IMemberService memberService;

	@Autowired
	private IRfidSupplierService supplierService;

	/**
	 * 门窗RFID
	 */
	@Autowired
	private IWindowRfidService windowRfidService;

	/**
	 * 物流RFID
	 */
	@Autowired
	private ILogisticsRfidService logisticsRfidService;

	/**
	 * 查询列表
	 * 
	 * @param query
	 * @param model
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("query")
	public String queryPurchaseRfid(PurchaseRfidQuery query, ModelMap model)
			throws WebException {
		try {
			query.setPagable(true);
			List<RfidPurchaseEntity> list = purchaseRfidService
					.queryPurchase(query);
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

	/**
	 * 删除
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("delete")
	public @ResponseBody Map<String, String> delete(String id, ModelMap model)
			throws WebException {
		Map<String, String> map = new HashMap<String, String>();
		try {
			purchaseRfidService.deletePurchase(id);
			map.put("isOK", "ok");
		} catch (Exception e) {
			SxjLogger.error("删除采购单错误", e, this.getClass());
			map.put("error", e.getMessage());
		}
		return map;
	}

	/**
	 * 确认付款
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("confirmPay")
	public @ResponseBody Map<String, String> confirmPay(String id,
			ModelMap model) throws WebException {
		try {
			purchaseRfidService.updatePayState(id, PayStateEnum.paid);
			Map<String, String> map = new HashMap<String, String>();
			map.put("isOK", "ok");
			return map;
		} catch (Exception e) {
			SxjLogger.error("确认付款错误", e, this.getClass());
			throw new WebException("确认付款错误");
		}
	}

	/**
	 * 确认收货
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("confirmReceipt")
	public @ResponseBody Map<String, String> confirmReceipt(String id,
			ModelMap model) throws WebException {
		Map<String, String> map = new HashMap<String, String>();
		try {
			purchaseRfidService.confirmReceipt(id);
			map.put("isOK", "ok");
		} catch (Exception e) {
			SxjLogger.error("确认收货错误", e, this.getClass());
			map.put("error", e.getMessage());
		}
		return map;
	}

	/**
	 * 确认发货
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("confirmDelivery")
	public @ResponseBody Map<String, String> confirmDelivery(String id,
			ModelMap model) throws WebException {
		Map<String, String> map = new HashMap<String, String>();
		try {
			purchaseRfidService.confirmDelivery(id);
			map.put("isOK", "ok");
			return map;
		} catch (Exception e) {
			SxjLogger.error("确认发货错误", e, this.getClass());
			map.put("error", e.getMessage());
		}
		return map;
	}

	/**
	 * 申请单详情
	 * 
	 * @param id
	 * @param applyNo
	 * @param model
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("purchaseInfo")
	public String getPurchase(String id, String applyNo, ModelMap model)
			throws WebException {
		try {
			RfidApplicationEntity app = appService.getApplication(applyNo);
			model.put("app", app);
			if (app != null) {
				MemberEntity member = memberService.memberInfo(app
						.getMemberNo());
				model.put("member", member);
			}
			model.put("id", id);
			return "manage/rfid/purchase/purchase-info";
		} catch (Exception e) {
			SxjLogger.error("查询采购单错误", e, this.getClass());
			throw new WebException("查询采购单错误");
		}
	}

	/**
	 * 修改采购单
	 * 
	 * @param purchase
	 * @param model
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("modify")
	public @ResponseBody Map<String, String> modify(
			RfidPurchaseEntity purchase, ModelMap model) throws WebException {
		Map<String, String> map = new HashMap<String, String>();
		try {
			purchaseRfidService.updatePurchase(purchase);
			map.put("isOK", "ok");
		} catch (Exception e) {
			SxjLogger.error("修改采购单错误", e, this.getClass());
			map.put("error", e.getMessage());
		}
		return map;
	}

	/**
	 * 跳转添加
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("toAdd")
	public String toAdd(String id, ModelMap model) throws WebException {
		try {
			RfidApplicationEntity app = appService.getApplicationInfo(id);
			model.put("app", app);
			return "manage/rfid/purchase/purchase-add";
		} catch (Exception e) {
			SxjLogger.error("查询采购单错误", e, this.getClass());
			throw new WebException("查询采购单错误");
		}
	}

	/**
	 * 添加
	 * 
	 * @param purchase
	 * @param model
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("add")
	public @ResponseBody Map<String, String> add(RfidPurchaseEntity purchase,
			String applyId, String hasNumber, ModelMap model)
			throws WebException {
		try {
			purchase.setPurchaseDate(new Date());
			purchase.setImportState(ImportStateEnum.not_imported);
			purchase.setPayState(PayStateEnum.unpaid);
			purchase.setReceiptState(DeliveryStateEnum.unfilled);
			purchaseRfidService.addPurchase(purchase, applyId, hasNumber);
			Map<String, String> map = new HashMap<String, String>();
			map.put("isOK", "ok");
			return map;
		} catch (Exception e) {
			SxjLogger.error("新增采购单错误", e, this.getClass());
			throw new WebException("新增采购单错误");
		}
	}

	@RequestMapping("getSupplierPrice")
	public @ResponseBody Map<String, Object> getSupplierPrice(String supplierNo)
			throws WebException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			RfidSupplierEntity supplier = supplierService
					.getSupplierByNo(supplierNo);
			if (supplier != null) {
				Double doorPrice = supplier.getDoorsPrice();
				Double batchPrice = supplier.getBatchPrice();
				map.put("doorPrice", doorPrice);
				map.put("batchPrice", batchPrice);
			} else {
				map.put("error", "供应商不存在");
			}
		} catch (Exception e) {
			SxjLogger.error("获取价格错误", e, this.getClass());
			map.put("error", e.getMessage());
		}
		return map;
	}

	@RequestMapping("getMaxCount")
	public @ResponseBody Map<String, Object> getMaxCount(String applyNo,
			Long oldCount) throws WebException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			RfidApplicationEntity apply = appService.getApplication(applyNo);
			if (apply == null) {
				throw new WebException("对应的申请单不存在");
			}
			long max = apply.getCount() - (apply.getHasNumber() - oldCount);
			map.put("maxCount", max);
		} catch (Exception e) {
			SxjLogger.error("获取价格错误", e, this.getClass());
			map.put("error", e.getMessage());
		}
		return map;
	}

	@RequestMapping("importRfid")
	public @ResponseBody Map<String, String> importRfid(String purchaseId,
			ModelMap model) throws WebException {
		Map<String, String> map = new HashMap<String, String>();
		try {
			purchaseRfidService.importRfid(purchaseId);
			map.put("isOK", "ok");

		} catch (Exception e) {
			SxjLogger.error("导入RFID错误", e, this.getClass());
			map.put("error", e.getMessage());
		}
		return map;
	}

	/**
	 * 导出RFID CSV文件
	 * 
	 * @param purchaseId
	 * @param model
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("exportRfid")
	public void exportRfid(String applyNo, Integer type, ModelMap model,
			HttpServletResponse response) throws WebException {
		Map<String, String> map = new HashMap<String, String>();
		try {
			String name = "私享家rfid-{" + applyNo + "}-"+type+".csv";
			name =new String(name.getBytes("UTF-8"),"iso-8859-1");
			response.addHeader("Content-Disposition", "attachment;filename="  
	                + new String(name.getBytes()));  
			response.setContentType("application/-excel");
			PrintWriter www=new PrintWriter(response.getOutputStream());
			ICsvBeanWriter writer = new CsvBeanWriter(www,
					CsvPreference.STANDARD_PREFERENCE);
			
			// 输出头部
			String headers[] = { "rfidNo" };
			writer.writeHeader(headers);
			if (type == 0) {
				// 门窗RFID
				WindowRfidQuery winQuery = new WindowRfidQuery();
				winQuery.setApplyNo(applyNo);
				List<WindowRfidEntity> window = windowRfidService
						.queryWindowRfid(winQuery);
				for (WindowRfidEntity windowRfidEntity : window) {
					writer.write(windowRfidEntity, headers);
				}
			} else {
				LogisticsRfidQuery lQuery = new LogisticsRfidQuery();
				lQuery.setApplyNo(applyNo);
				List<LogisticsRfidEntity> logisticsList = logisticsRfidService
						.queryLogistics(lQuery);
				for (LogisticsRfidEntity logisticsRfidEntity : logisticsList) {
					writer.write(logisticsRfidEntity, headers);
				}
			}
			writer.close();
			www.flush();
			www.close();
			response.getOutputStream().flush();
			response.getOutputStream().close();
			
		} catch (Exception e) {
			SxjLogger.error("导出RFID错误", e, this.getClass());
			map.put("error", e.getMessage());
		}
	}
	@RequestMapping("importFile")  
    public  @ResponseBody Map<String, String> importFile(HttpServletRequest request,  
            HttpServletResponse response) throws  WebException {
		Map<String, String> map = new HashMap<String, String>();
		try{
		DefaultMultipartHttpServletRequest re = (DefaultMultipartHttpServletRequest) request;
		response.setContentType("text/html;charset=utf-8"); 
		MultipartFile file = re.getFile("fileName");
		String name =file.getOriginalFilename();
		String prefix =name.substring(name.lastIndexOf(".")+1);
		if(!prefix.equals("csv")){
			throw new WebException("文件格式错误");
		}
		String type =name.substring(name.lastIndexOf("-")+1,name.lastIndexOf("."));
		InputStreamReader freader = new InputStreamReader(file.getInputStream(),"UTF-8");
		CsvBeanReader reader = new CsvBeanReader(freader,CsvPreference.STANDARD_PREFERENCE);  
		String[] headers = reader.getHeader(false);
		if(type.equals("0")){
			//门窗
			WindowRfidEntity bean = null;  
			List<WindowRfidEntity> windowList = new ArrayList<WindowRfidEntity>();
			while ((bean = reader.read(WindowRfidEntity.class, headers)) != null) {
				WindowRfidEntity win=windowRfidService.getWindowRfidByNo(bean.getRfidNo());
				if(win==null){
					break;
				}
				windowList.add(bean);
			}
			if(windowList!=null && windowList.size()>0){
				windowRfidService.updateGid(windowList);
			}else{
				throw  new WebException("文件内容错误");
			}
			
		}else  if(type.equals("1")||type.equals("2")){
			//物流
			LogisticsRfidEntity bean = null;  
			List<LogisticsRfidEntity> logisticsList = new ArrayList<LogisticsRfidEntity>();
			while ((bean = reader.read(LogisticsRfidEntity.class, headers)) != null) {  
				LogisticsRfidEntity logistics=logisticsRfidService.getLogisticsByNo(bean.getRfidNo());
				if(logistics==null){
					break;
				}
				logisticsList.add(bean);
			}
			if(logisticsList!=null && logisticsList.size()>0){
				logisticsRfidService.updateGid(logisticsList);
			}else{
				throw  new WebException("文件内容错误");
			}
			
		}else{
			throw  new WebException("文件内容错误");
		}
		reader.close();
		map.put("isOk", "ok");
		}catch (Exception e) {
			SxjLogger.error("导入GID错误", e, this.getClass());
			map.put("error", e.getMessage());
		}
		return map;
    }
}
