package com.sxj.supervisor.website.controller.record;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.entity.record.RecordEntity;
import com.sxj.supervisor.enu.record.ContractTypeEnum;
import com.sxj.supervisor.enu.record.RecordFlagEnum;
import com.sxj.supervisor.enu.record.RecordStateEnum;
import com.sxj.supervisor.enu.record.RecordTypeEnum;
import com.sxj.supervisor.service.record.IRecordService;
import com.sxj.supervisor.website.controller.BaseController;
import com.sxj.util.exception.WebException;

@Controller
@RequestMapping("/record")
public class RecordController extends BaseController {


	@Autowired
	IRecordService recordService;
	/**
	 * 跳转申请合同
	 * @param map
	 * @return
	 */
	@RequestMapping("/to_apply")
	public String to_apply(ModelMap map) {
		map.put("status", "1");
		map.put("name", "江苏私享家商贸有限公司");//name
		
		return "site/record/apply-record";
	}
	/**
	 * 申请采购备案合同
	 * @param record
	 * @param map
	 * @return
	 * @throws WebException 
	 */
	@RequestMapping("/AcgApplyRecord")
	public @ResponseBody Map<String, String> cgApplyRecord(RecordEntity record) throws WebException {
		try {
			record.setState(RecordStateEnum.noBinding);
			record.setType(RecordTypeEnum.contract);
			record.setApplyDate(new Date());
			record.setDelState(false);
			record.setContractType(ContractTypeEnum.glass);//合同类型
			record.setFlag(RecordFlagEnum.A);
			recordService.addRecord(record);
			Map<String, String> map = new HashMap<String, String>();
			map.put("isOK", "ok");
			map.put("recordNo", record.getRecordNo());
			return map;
		} catch (Exception e) {
			throw new WebException(e);
		}
	}
	/**
	 * 申请招标备案合同
	 * @param record
	 * @param map
	 * @return
	 * @throws WebException 
	 */
	@RequestMapping("/zbApplyRecord")
	public @ResponseBody Map<String, String> zbApplyRecord(RecordEntity record) throws WebException {
		try {
			record.setState(RecordStateEnum.noBinding);
			record.setType(RecordTypeEnum.contract);
			record.setApplyDate(new Date());
			record.setDelState(false);
			record.setContractType(ContractTypeEnum.bidding);//合同类型
			record.setFlag(RecordFlagEnum.B);
			recordService.addRecord(record);
			Map<String, String> map = new HashMap<String, String>();
			map.put("isOK", "ok");
			map.put("recordNo", record.getRecordNo());
			return map;
		} catch (Exception e) {
			throw new WebException(e);
		}
	}
	/**
	 * 乙方申请采购备案合同
	 * @param record
	 * @param map
	 * @return
	 * @throws WebException 
	 */
	@RequestMapping("/BzbApplyRecord")
	public @ResponseBody Map<String, String> BzbApplyRecord(RecordEntity record) throws WebException {
		try {
			record.setState(RecordStateEnum.noBinding);
			record.setType(RecordTypeEnum.contract);
			record.setApplyDate(new Date());
			record.setDelState(false);
			record.setContractType(ContractTypeEnum.extrusions);//合同类型
			record.setFlag(RecordFlagEnum.B);
			recordService.addRecord(record);
			Map<String, String> map = new HashMap<String, String>();
			map.put("isOK", "ok");
			map.put("recordNo", record.getRecordNo());
			return map;
		} catch (Exception e) {
			throw new WebException(e);
		}
	}
}
