package com.sxj.supervisor.website.controller.record;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.sxj.file.common.LocalFileUtil;
import com.sxj.file.fastdfs.FastDFSImpl;
import com.sxj.file.fastdfs.FileGroup;
import com.sxj.file.fastdfs.IFileUpLoad;
import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.entity.record.RecordEntity;
import com.sxj.supervisor.enu.record.ContractTypeEnum;
import com.sxj.supervisor.enu.record.RecordFlagEnum;
import com.sxj.supervisor.enu.record.RecordStateEnum;
import com.sxj.supervisor.enu.record.RecordTypeEnum;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.model.record.RecordQuery;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.supervisor.service.record.IRecordService;
import com.sxj.supervisor.website.controller.BaseController;
import com.sxj.supervisor.website.login.SupervisorPrincipal;
import com.sxj.util.exception.WebException;

@Controller
@RequestMapping("/record")
public class RecordController extends BaseController {

	/**
	 * 合同service
	 */
	@Autowired
	IContractService contractService;

	/**
	 * 备案service
	 */
	@Autowired
	IRecordService recordService;

	@RequestMapping("/query")
	public String to_query(ModelMap map, HttpSession session) {
		RecordQuery query = new RecordQuery();
		RecordTypeEnum[] rte = RecordTypeEnum.values();// 备案类型
		ContractTypeEnum[] cte = ContractTypeEnum.values(); // 合同类型
		RecordStateEnum[] rse = RecordStateEnum.values();// 备案状态
		SupervisorPrincipal userBean = (SupervisorPrincipal) session.getAttribute("userinfo");
		query.setMemberId(userBean.getMember().getId());
		List<RecordEntity> list = recordService.queryRecord(query);
		map.put("recordlist", list);
		map.put("recordType", rte);
		map.put("contractType", cte);
		return "site/record/contract-list";
	}

	@RequestMapping("info")
	public String queryContractInfo(ModelMap model, String contractNo) {
		ContractModel contract = contractService
				.getContractByContractNo(contractNo);
		ContractModel contractModel = new ContractModel();
		if (contract.getContract() != null) {
			contractModel = contractService.getContract(contract.getContract()
					.getId());
		}
		model.put("contractModel", contractModel);
		return "site/record/contract-info";
	}

	/**
	 * 上传图片
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("upload")
	public @ResponseBody Map<String, Object> uploadFile(
			HttpServletRequest request) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!(request instanceof DefaultMultipartHttpServletRequest)) {
			return map;
		}
		DefaultMultipartHttpServletRequest re = (DefaultMultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMaps = re.getFileMap();
		Collection<MultipartFile> files = fileMaps.values();
		List<String> fileIds = new ArrayList<String>();
		for (MultipartFile myfile : files) {
			if (myfile.isEmpty()) {
				System.err.println("文件未上传");
			} else {
				IFileUpLoad dfs = new FastDFSImpl(FileGroup.imgGroup);
				String fileId = dfs.uploadFile(myfile.getBytes(), LocalFileUtil
						.getFileExtName(myfile.getOriginalFilename()));
				fileIds.add(fileId);
			}
		}
		map.put("fileIds", fileIds);

		return map;
	}

	/**
	 * 跳转申请合同
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("/to_apply")
	public String to_apply(ModelMap map, HttpSession session) {
		SupervisorPrincipal userBean = (SupervisorPrincipal) session.getAttribute("userinfo");
		map.put("type", userBean.getMember().getType());
		map.put("name", userBean.getMember().getName());// name

		return "site/record/apply-record";
	}

	/**
	 * 申请采购备案合同
	 * 
	 * @param record
	 * @param map
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("/AcgApplyRecord")
	public @ResponseBody Map<String, String> cgApplyRecord(RecordEntity record,HttpSession session)
			throws WebException {
		try {
			SupervisorPrincipal userBean = (SupervisorPrincipal) session.getAttribute("userinfo");
			record.setApplyId(userBean.getMember().getId());
			record.setApplyName(userBean.getMember().getName());
			record.setState(RecordStateEnum.noBinding);
			record.setType(RecordTypeEnum.contract);
			record.setApplyDate(new Date());
			record.setDelState(false);
			record.setContractType(ContractTypeEnum.glass);// 合同类型
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
	 * 
	 * @param record
	 * @param map
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("/zbApplyRecord")
	public @ResponseBody Map<String, String> zbApplyRecord(RecordEntity record,HttpSession session)
			throws WebException {
		try {
			SupervisorPrincipal userBean = (SupervisorPrincipal) session.getAttribute("userinfo");
			record.setApplyId(userBean.getMember().getId());
			record.setApplyName(userBean.getMember().getName());
			record.setState(RecordStateEnum.noBinding);
			record.setType(RecordTypeEnum.contract);
			record.setApplyDate(new Date());
			record.setDelState(false);
			record.setContractType(ContractTypeEnum.bidding);// 合同类型
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
	 * 
	 * @param record
	 * @param map
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("/BzbApplyRecord")
	public @ResponseBody Map<String, String> BzbApplyRecord(RecordEntity record,HttpSession session)
			throws WebException {
		try {
			SupervisorPrincipal userBean = (SupervisorPrincipal) session.getAttribute("userinfo");
			record.setApplyId(userBean.getMember().getId());
			record.setApplyName(userBean.getMember().getName());
			record.setState(RecordStateEnum.noBinding);
			record.setType(RecordTypeEnum.contract);
			record.setApplyDate(new Date());
			record.setDelState(false);
			record.setContractType(ContractTypeEnum.extrusions);// 合同类型
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

	@RequestMapping("/to_modify")
	public String to_modify(String recordId,ModelMap map, HttpSession session)
			throws WebException {
		try {
			RecordEntity record= recordService.getRecord(recordId);
			SupervisorPrincipal member = (SupervisorPrincipal) session.getAttribute("userinfo");
			map.put("record", record);//备案类型
			map.put("member", member);//会员类型
			return "site/record/edit-record";
		} catch (Exception e) {
			throw new WebException(e);
		}
	}

	@RequestMapping("/modifyRecord")
	public @ResponseBody Map<String, String> modifyRecord(String recordId,
			String imgPath, String RFID) throws WebException {
		try {
			RecordEntity  record = new RecordEntity();
			if (recordId != "" && recordId != null) {
				record.setId(recordId);
			}
			if (imgPath != "" && imgPath != null) {
				record.setImgPath(imgPath);
			}
			if (RFID != "" && RFID != null) {
				record.setRfidNo(RFID);
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("isOK", "ok");
			return map;
		} catch (Exception e) {
			throw new WebException(e);
		}
	}
}
