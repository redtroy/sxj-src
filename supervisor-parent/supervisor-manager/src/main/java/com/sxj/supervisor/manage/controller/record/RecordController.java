package com.sxj.supervisor.manage.controller.record;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.sxj.cache.manager.HierarchicalCacheManager;
import com.sxj.file.common.LocalFileUtil;
import com.sxj.file.fastdfs.FastDFSImpl;
import com.sxj.file.fastdfs.FileGroup;
import com.sxj.file.fastdfs.IFileUpLoad;
import com.sxj.spring.modules.mapper.JsonMapper;
import com.sxj.supervisor.entity.record.RecordEntity;
import com.sxj.supervisor.enu.record.ContractTypeEnum;
import com.sxj.supervisor.enu.record.RecordFlagEnum;
import com.sxj.supervisor.enu.record.RecordStateEnum;
import com.sxj.supervisor.enu.record.RecordTypeEnum;
import com.sxj.supervisor.manage.comet.MessageChannel;
import com.sxj.supervisor.manage.comet.record.RecordThread;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.model.contract.ContractQuery;
import com.sxj.supervisor.model.record.RecordQuery;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.supervisor.service.record.IRecordService;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/record")
public class RecordController extends BaseController {

	@Autowired
	private IRecordService recordService;

	@Autowired
	private IContractService contractService;

	@Autowired
	private IFileUpLoad fastDfsClient;

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
			registChannel(MessageChannel.RECORD_MESSAGE, RecordThread.class);
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
		return "manage/record/record_edit";
	}

	/**
	 * 保存备案修改
	 * 
	 * @param record
	 * @param map
	 * @return
	 */
	@RequestMapping("/record_save")
	public String record_save(HttpServletRequest request, RecordEntity record,
			ModelMap map) throws WebException {
		try {
			recordService.modifyRecord(record);
			return "redirect:" + getBasePath(request) + "record/recordList.htm";
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new WebException("新增备案错误", e);
		}
	}

	/**
	 * 办绑定备案页面
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
	public @ResponseBody Map<String, Object> queryRecordNo(RecordQuery query) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<RecordEntity> list = recordService.queryRecord(query);
		ContractModel cm = contractService.getContractByContractNo(query
				.getContractNo());
		ContractQuery contractQuery = new ContractQuery();
		contractQuery.setContractNo(query.getContractNo());
		map.put("record", list.get(0));
		map.put("refContractNo", cm.getContract().getRefContractNo());
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
	 * 上传图片
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("upload")
	public void uploadFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!(request instanceof DefaultMultipartHttpServletRequest)) {
			return;
		}
		DefaultMultipartHttpServletRequest re = (DefaultMultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMaps = re.getFileMap();
		Collection<MultipartFile> files = fileMaps.values();
		List<String> fileIds = new ArrayList<String>();
		for (MultipartFile myfile : files) {
			if (myfile.isEmpty()) {
				System.err.println("文件未上传");
			} else {
				String fileId = fastDfsClient.uploadFile(myfile.getBytes(),
						myfile.getOriginalFilename());
				fileIds.add(fileId);
			}
		}
		map.put("fileIds", fileIds);
		String res = JsonMapper.nonDefaultMapper().toJson(map);
		response.setContentType("text/plain;UTF-8");
		PrintWriter out = response.getWriter();
		out.print(res);
		out.flush();
		out.close();
	}
}
