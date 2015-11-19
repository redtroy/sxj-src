package com.sxj.supervisor.website.controller.purchase;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import third.rewrite.fastdfs.NameValuePair;
import third.rewrite.fastdfs.service.IStorageClientService;

import com.sxj.spring.modules.mapper.JsonMapper;
import com.sxj.spring.modules.web.MediaTypes;
import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.supervisor.service.member.IMemberService;
import com.sxj.supervisor.service.purchase.IPurchaseService;
import com.sxj.supervisor.website.controller.BaseController;
import com.sxj.util.common.FileUtil;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/purchase")
public class PurchaseController extends BaseController {

	@Autowired
	private IStorageClientService storageClientService;

	@Autowired
	private IMemberService memberService;
	
	@Autowired
	private IPurchaseService  purchaseService;
	
	@Autowired
	private IContractService  contractService;

	/**
	 * 上传文件
	 * 
	 * @param request
	 * @param response
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
				String originalName = myfile.getOriginalFilename();
				String extName = FileUtil.getFileExtName(originalName);
				String filePath = storageClientService.uploadFile(null,
						new ByteArrayInputStream(myfile.getBytes()),
						myfile.getBytes().length, extName.toUpperCase());
				SxjLogger.info("siteUploadFilePath=" + filePath,
						this.getClass());
				fileIds.add(filePath);

				// 上传元数据
				NameValuePair[] metaList = new NameValuePair[1];
				metaList[0] = new NameValuePair("originalName",
						URLEncoder.encode(originalName, "UTF-8"));
				storageClientService.overwriteMetadata(filePath, metaList);
			}
		}
		map.put("fileIds", fileIds);
		String res = JsonMapper.nonDefaultMapper().toJson(map);
		response.setContentType("text/plain;UTF-8");
		response.setHeader("Access-Control-Allow-Origin","*");
		PrintWriter out = response.getWriter();
		out.print(res);
		out.flush();
		out.close();
	}

	/**
	 * 同步会员
	 * 
	 * @param json
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="syncMember")
	@ResponseBody
	public void syncMember(@RequestBody MemberEntity memberEntity,
			HttpServletResponse response,HttpServletRequest request) throws IOException {
		PrintWriter out = response.getWriter();
		try {
			System.err.println(memberEntity.getAddress());
			purchaseService.syncMember(memberEntity);
			out.print("1");
			response.setContentType("text/plain;UTF-8");
			response.setHeader("Access-Control-Allow-Origin","*");
		} catch (Exception e) {
			SxjLogger.error("同步会员出错 ", e, this.getClass());
			out.print("0");
		}finally{
			out.flush();
			out.close();
		}
		
	}
	/**
	 * 获取合同内容
	 * @param response
	 * @param request
	 * @param contractNo
	 * @throws IOException
	 */
	@RequestMapping(value="getContract")
	public void getContract(HttpServletResponse response,HttpServletRequest request,String contractNo) throws IOException{
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		try {
			contractNo="CT15110013";
			ContractModel contract = contractService.getContractModelByContractNo(contractNo);
			String  jsonString =JsonMapper.nonEmptyMapper().toJson(contract);
			System.err.println(jsonString);
			out.print(jsonString);
			response.setHeader("Access-Control-Allow-Origin","*");
		} catch (Exception e) {
			SxjLogger.error("同步会员出错 ", e, this.getClass());
			out.print("0");
		}finally{
			out.flush();
			out.close();
		}
	}
}
