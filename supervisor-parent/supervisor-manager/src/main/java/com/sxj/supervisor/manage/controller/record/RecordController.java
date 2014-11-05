package com.sxj.supervisor.manage.controller.record;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.cache.manager.HierarchicalCacheManager;
import com.sxj.supervisor.entity.record.RecordEntity;
import com.sxj.supervisor.enu.record.ContractTypeEnum;
import com.sxj.supervisor.enu.record.RecordFlagEnum;
import com.sxj.supervisor.enu.record.RecordStateEnum;
import com.sxj.supervisor.enu.record.RecordTypeEnum;
import com.sxj.supervisor.manage.comet.MessageChannel;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.model.login.SupervisorPrincipal;
import com.sxj.supervisor.model.record.RecordQuery;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.supervisor.service.record.IRecordService;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/record")
public class RecordController extends BaseController {

	@Autowired
	private IRecordService recordService;

	@Autowired
	private IContractService contractService;

	/**
	 * 备案管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/recordList")
	public String recordList(RecordQuery query, ModelMap map)
			throws WebException {
		try {
			if (query != null) {
				query.setPagable(true);
			}
			ContractTypeEnum[] cte = ContractTypeEnum.values(); // 合同类型
			RecordStateEnum[] rse = RecordStateEnum.values();// 备案状态
			RecordTypeEnum[] rte = RecordTypeEnum.values();// 备案类型
			query.setSort("desc");
			query.setSortColumn("RECORD_NO");
			List<RecordEntity> list = recordService.queryRecord(query);
			map.put("cte", cte);
			map.put("rse", rse);
			map.put("rte", rte);
			map.put("list", list);
			map.put("query", query);
			if (StringUtils.isNotEmpty(query.getIsDelMes())) {
				HierarchicalCacheManager.evict(2, "comet_message",
						MessageChannel.RECORD_MESSAGE);
			}
			registChannel(MessageChannel.RECORD_MESSAGE);
			return "manage/record/record";
		} catch (Exception e) {
			SxjLogger.error("查询备案信息错误", e, this.getClass());
			throw new WebException("查询备案信息错误");
		}

	}

	/**
	 * 备案修改
	 * 
	 * @return
	 */
	@RequestMapping("/record_edit")
	public String record_edit(String id, ModelMap map) {
		RecordEntity record = recordService.getRecord(id);
		ContractTypeEnum[] cte = ContractTypeEnum.values(); // 合同类型
		RecordTypeEnum[] rte = RecordTypeEnum.values();// 备案类型
		map.put("cte", cte);
		map.put("rte", rte);
		map.put("record", record);
		if(record.getContractType().getId()!=0){
			return "manage/record/record_edit";
		}else{
			return "manage/record/record_edit_zhaobiao";
		}
		
	}

	/**
	 * 保存备案修改
	 * 
	 * @param record
	 * @param map
	 * @return
	 */
	@RequestMapping("/record_save")
	public @ResponseBody Map<String, String> record_save(RecordEntity record) throws WebException {
		try {
			Map<String, String> map = new HashMap<String, String>();
			recordService.modifyRecord(record);
			map.put("isOK", "ok");
			return map;
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new WebException("新增备案错误", e);
		}
	}

	/**
	 * 绑定备案页面
	 * 
	 * @param map
	 * @param id
	 * @return
	 */
	@RequestMapping("/banding_edit")
	public String banding_edit(ModelMap map, String id) {
		RecordEntity record = recordService.getRecord(id);
		RecordFlagEnum[] flag = RecordFlagEnum.values();
		map.put("record", record);
		map.put("flag", flag);
		return "manage/record/record_banding";
	}

	@RequestMapping("/banding_save")
	public @ResponseBody Map<String, String> bandingSave(String contractNo,
			String refContractNo, String recordNo, String recordNo2,
			String recordIdA, String recordIdB) throws WebException {
		try {
			Map<String, String> map = new HashMap<String, String>();
			recordService.bindingContract(contractNo, refContractNo, recordNo,
					recordNo2, recordIdA, recordIdB);
			map.put("isOK", "ok");
			return map;
		} catch (Exception e) {
			SxjLogger.error("绑定合同错误", e, this.getClass());
			throw new WebException("绑定合同错误 ");
		}

	}

	/**
	 * 根据合同号查询
	 * 
	 * @param query
	 * @return
	 */
	@RequestMapping("/queryRecordNo")
	public @ResponseBody Map<String, Object> queryRecordNo(RecordQuery query,
			String recordId) {
		Map<String, Object> map = new HashMap<String, Object>();
		ContractModel cm = contractService.getContractModelByContractNo(query
				.getContractNo().trim());
		if (cm != null) {
			RecordEntity re = recordService.getRecord(recordId);// 申请绑定备案
			if(cm.getContract().getType().getId()!=0){
			String recordNo = cm.getContract().getRecordNo();
			if (recordNo != null) {
				String[] recordNoArr = recordNo.split(",");
				if (recordNoArr.length < 2) {
					for (String record : recordNoArr) {
						RecordEntity recordEntity = recordService
								.getRecordByNo(record.trim());
						if (re.getFlag().getId() == 0) {
							// 甲方备案
							if (recordEntity.getFlag().getId() == 0) {
								map.put("ok", "jfyba");
							} else {
								map.put("ok", "ok");
								map.put("record", recordEntity);
							}
						} else {
							// 乙方备案
							if (recordEntity.getFlag().getId() == 1) {
								map.put("ok", "yfyba");
							} else {
								map.put("ok", "ok");
								map.put("record", recordEntity);
							}
						}
					}
				} else {
					map.put("ok", "htyba");
				}
			}
			}else{
				map.put("ok", "no");
			}
		} else {
			map.put("ok", "no");
		}
		return map;
	}

	/**
	 * 根据ID删除备案
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delRecord")
	public @ResponseBody Map<String, String> delRecord(String id) {
		Map<String, String> map = new HashMap<String, String>();
		recordService.deleteRecord(id);
		map.put("isOk", "ok");
		return map;
	}
	
	/**
	 * 备案是否可修改
	 * 
	 * @param contractNo
	 * @param session
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("getRecordState")
	public @ResponseBody Map<String, String> getRecordState(String id, HttpSession session)
			throws WebException {
		try {
			Map<String, String> map = new HashMap<String, String>();
			RecordEntity re= recordService.getRecord(id);
			if(re!=null){
			if(re.getType().getId()==0){
				if(StringUtils.isEmpty(re.getContractNo())){
					map.put("isOK", "ok");
				}else{
					map.put("isOK", "no");
				}
			}else if(re.getType().getId()==1){
				if(re.getState().getId()==2){
					map.put("isOK", "ok");
				}else{
					map.put("isOK", "no");
				}
			}else if(re.getType().getId()==2){
				if(re.getState().getId()==4){
					map.put("isOK", "ok");
				}else{
					map.put("isOK", "no");
				}
			}
			}else{
				map.put("isOK", "del");
			}
			return map;
		} catch (Exception e) {
			SxjLogger.error("确认备案信息错误", e, this.getClass());
			throw new WebException("确认备案信息错误");
		}
	}
	@RequestMapping("getContract")
	public @ResponseBody Map<String, String> getContract(String param,String id) throws WebException {
		try {
			Map<String, String> map = new HashMap<String, String>();
			RecordEntity re =recordService.getRecord(id);
			int size = contractService.getContractByZhaobiaoContractNo(
					param.trim(), re.getMemberIdA());
			if (size == 0) {
				map.put("status", "n");
				map.put("info", "请输入正确的招标合同号");
			} else {
				map.put("status", "y");
			}

			return map;
		} catch (Exception e) {
			SxjLogger.error("确认备案信息错误", e, this.getClass());
			throw new WebException("确认备案信息错误");
		}
	}
}
