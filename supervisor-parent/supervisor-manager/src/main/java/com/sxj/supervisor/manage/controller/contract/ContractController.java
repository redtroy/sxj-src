package com.sxj.supervisor.manage.controller.contract;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sxj.supervisor.entity.record.RecordEntity;
import com.sxj.supervisor.enu.contract.ContractStateEnum;
import com.sxj.supervisor.enu.contract.ContractSureStateEnum;
import com.sxj.supervisor.enu.contract.ContractTypeEnum;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.model.contract.ContractModifyModel;
import com.sxj.supervisor.model.contract.ContractQuery;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.supervisor.service.record.IRecordService;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/contract")
public class ContractController extends BaseController {

	@Autowired
	private IContractService contractService;
	
	@Autowired
	private IRecordService recordService;

	@RequestMapping("query")
	public String queryContract(ContractQuery query, ModelMap model)throws WebException  {
		try {
			if (query != null) {
				query.setPagable(true);
			}
		List<ContractModel> list = contractService.queryContracts(query);
		ContractTypeEnum[] contractType = ContractTypeEnum.values();// 合同类型
		ContractSureStateEnum[] contractSureState = ContractSureStateEnum.values();// 确认状态
		ContractStateEnum[] contractState = ContractStateEnum.values();// 状态
		model.put("contractType", contractType);
		model.put("contractSureState", contractSureState);
		model.put("contractState", contractState);
		model.put("query", query);
		model.put("list", list);
		return "manage/contract/contract-list";
		} catch (Exception e) {
			SxjLogger.error("查询合同信息错误", e, this.getClass());
			throw new WebException("查询合同信息错误");
		}
	}

	@RequestMapping("info")
	public String queryContractInfo(ModelMap model, String contractId) {
		ContractModel contractModel = contractService.getContract(contractId);
		model.put("contractModel", contractModel);
		model.put("contractId", contractId);
		return "manage/contract/contract-info";
	}

	@RequestMapping("produced")
	public String producedContract(String recordId) {
		ModelMap model = new ModelMap();
		model.put("recordId", recordId);
		return "manage/contract/contract-add";
	}

	@RequestMapping("addContract")
	public String addContract(ContractControllerModel contract) {
		ModelMap model = new ModelMap();
		contractService.addContract(contract.getContract(), contract.getItems(), contract.getRecordId());
		
		return "manage/contract/contract-list";
	}
	@RequestMapping("toModify")
	public String toModifyContract(String  contractId,ModelMap model) {
		ContractModel contractModel = contractService.getContract("1");
		model.put("contractModel", contractModel);
		model.put("contractId", contractId);
		return "manage/contract/contract-edit";
	}
	@RequestMapping("modify")
	public String modifyContract(ContractModel  contractModel,ModelMap model) {
		contractService.modifyContract(contractModel);
		
		return "manage/contract/contract-edit";
	}
	
	@RequestMapping("changes")
	public String changesContract(ModelMap model, String contractId,String recordId) {
		ContractModel contractModel = contractService.getContract("1");
		RecordEntity  record= recordService.getRecord("1");
		model.put("contractModel", contractModel);
		model.put("record", record);
		return "manage/contract/contract-changes";
	}
	@RequestMapping("saveChanges")
	public String saveChanges(ContractModifyControllerModel contractModifyModel) {
		ContractModifyModel model = new ContractModifyModel();
		model.setModifyContract(contractModifyModel.getModifyContract());
		model.setModifyBatchList(contractModifyModel.getModifyBatchList());
		model.setModifyItemList(contractModifyModel.getModifyItemList());
		contractService.changeContract(contractModifyModel.getContractId(),model,contractModifyModel.getRecordNo(),
				contractModifyModel.getItemList());
		return "manage/contract/contract-list";
	}
	@RequestMapping("replenish")
	public String replenishContract(ModelMap model, String contractId,String recordId){
		ContractModel contractModel = contractService.getContract("1");
		RecordEntity  record= recordService.getRecord("1");
		model.put("contractModel", contractModel);
		model.put("record", record);
		return "manage/contract/contract-replenish";
	}
	@RequestMapping("upload")
	public String addUser(@RequestParam MultipartFile[] myfiles, HttpServletRequest request) throws IOException{
	    for(MultipartFile myfile : myfiles){  
            if(myfile.isEmpty()){  
                System.err.println("文件未上传");  
            }else{  
                System.err.println("文件长度: " + myfile.getSize());  
                System.err.println("文件类型: " + myfile.getContentType());  
                System.err.println("文件名称: " + myfile.getName());  
                System.err.println("文件原名: " + myfile.getOriginalFilename());  
                System.err.println("========================================");  
                //如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\文件夹中  
                String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");  
                //这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的  
                FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, myfile.getOriginalFilename()));  
            }  
        } 
		
		return null;  
	}
}
