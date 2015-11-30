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
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import third.rewrite.fastdfs.NameValuePair;
import third.rewrite.fastdfs.service.IStorageClientService;

import com.sxj.spring.modules.mapper.JsonMapper;
import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.entity.purchase.ApplyEntity;
import com.sxj.supervisor.entity.purchase.PurchaseEntity;
import com.sxj.supervisor.entity.purchase.ReleaseRecordEntity;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.model.login.SupervisorPrincipal;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.supervisor.service.member.IMemberService;
import com.sxj.supervisor.service.purchase.IPurchaseService;
import com.sxj.supervisor.website.controller.BaseController;
import com.sxj.util.common.FileUtil;
import com.sxj.util.common.ISxjHttpClient;
import com.sxj.util.common.StringUtils;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/purchase")
public class PurchaseController extends BaseController {

	@Autowired
	private IStorageClientService storageClientService;

	@Autowired
	private IMemberService memberService;

	@Autowired
	private IPurchaseService purchaseService;

	@Autowired
	private IContractService contractService;

	@Autowired
	private ISxjHttpClient httpClient;
	

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
		response.setHeader("Access-Control-Allow-Origin", "*");
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
	@RequestMapping(value = "syncMember")
	@ResponseBody
	public void syncMember(@RequestBody MemberEntity memberEntity,
			HttpServletResponse response, HttpServletRequest request)
			throws IOException {
		PrintWriter out = response.getWriter();
		try {
			System.err.println(memberEntity.getAddress());
			purchaseService.syncMember(memberEntity);
			out.print("1");
			response.setContentType("text/plain;UTF-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
		} catch (Exception e) {
			SxjLogger.error("同步会员出错 ", e, this.getClass());
			out.print("0");
		} finally {
			out.flush();
			out.close();
		}

	}

	/**
	 * 获取合同内容
	 * 
	 * @param response
	 * @param request
	 * @param contractNo
	 * @throws IOException
	 */
	@RequestMapping(value = "getContract")
	public void getContract(HttpServletResponse response,
			HttpServletRequest request, String contractNo) throws IOException {
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		try {
			contractNo = "CT15110013";
			ContractModel contract = contractService
					.getContractModelByContractNo(contractNo);
			String jsonString = JsonMapper.nonEmptyMapper().toJson(contract);
			System.err.println(jsonString);
			out.print(jsonString);
			response.setHeader("Access-Control-Allow-Origin", "*");
		} catch (Exception e) {
			SxjLogger.error("同步会员出错 ", e, this.getClass());
			out.print("0");
		} finally {
			out.flush();
			out.close();
		}
	}

	/**
	 * 更新采购池数据
	 * 
	 * @param json
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "updatePurchase")
	@ResponseBody
	public void updatePurchase(@RequestBody PurchaseEntity purchaseEntity,
			HttpServletResponse response, HttpServletRequest request)
			throws IOException {
		PrintWriter out = response.getWriter();
		try {
			purchaseService.updatePurchase(purchaseEntity);
			out.print("1");
			response.setContentType("text/plain;UTF-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
		} catch (Exception e) {
			SxjLogger.error("更新采购池数据出错 ", e, this.getClass());
			out.print("0");
		} finally {
			out.flush();
			out.close();
		}

	}

	/**
	 * 获取采购池数据
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("getPurchase")
	public String getPurchase(ModelMap map,
			ReleaseRecordEntity releaseRecordEntity, HttpSession session) {
		SupervisorPrincipal userBean = (SupervisorPrincipal) session
				.getAttribute("userinfo");
		PurchaseEntity purchaseEntity = purchaseService.getPurchase();
		releaseRecordEntity.setPagable(true);
		releaseRecordEntity.setShowCount(5);
		List<ReleaseRecordEntity> rrList = purchaseService
				.queryReleaseRecords(releaseRecordEntity);
		map.put("purchase", purchaseEntity);
		map.put("rrList", rrList);
		map.put("query", releaseRecordEntity);
		map.put("memberNo", userBean.getMember().getMemberNo());
		map.put("company", userBean.getMember().getName());
		
		return "site/purchase/purchase";
	}

	/**
	 * 添加申请单数据
	 * 
	 * @param json
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "addApply")
	@ResponseBody
	public void addApply(@RequestBody ApplyEntity applyEntity,
			HttpServletResponse response, HttpServletRequest request) throws IOException {
		PrintWriter out = response.getWriter();
		try {
			purchaseService.addApply(applyEntity);
			out.print("1");
			response.setContentType("text/plain;UTF-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
		} catch (Exception e) {
			SxjLogger.error("添加申请单数据出错 ", e, this.getClass());
			out.print("0");
		} finally {
			out.flush();
			out.close();
		}

	}

	/**
	 * 获取采购池数据
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("queryApply")
	public String queryApply(ModelMap map, ApplyEntity applyEntity,HttpSession session) {
		SupervisorPrincipal userBean = (SupervisorPrincipal) session
				.getAttribute("userinfo");
		applyEntity.setPagable(true);
		applyEntity.setMemberNo(userBean.getMember().getMemberNo());
		List<ApplyEntity> applyList = purchaseService.queryApply(applyEntity);
		map.put("query", applyEntity);
		map.put("applyList", applyList);
		return "site/purchase/applyList";
	}

	/**
	 * 添加采购池数据
	 * 
	 * @param json
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "addReleaseRecord")
	@ResponseBody
	public void addReleaseRecord(
			@RequestBody ReleaseRecordEntity releaseRecordEntity,
			HttpServletResponse response, HttpServletRequest request)
			throws IOException {
		PrintWriter out = response.getWriter();
		try {
			purchaseService.addReleaseRecordDao(releaseRecordEntity);
			out.print("1");
			response.setContentType("text/plain;UTF-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
		} catch (Exception e) {
			SxjLogger.error("添加采购池数据出错 ", e, this.getClass());
			out.print("0");
		} finally {
			out.flush();
			out.close();
		}

	}

	/**
	 * 请求汇窗平台
	 * @param apply
	 * @param response
	 * @param request
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "saveApply")
	public @ResponseBody Map<String, String> saveApply(ApplyEntity apply,
			HttpServletResponse response, HttpServletRequest request, HttpSession session)
			throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		SupervisorPrincipal userBean = (SupervisorPrincipal) session
				.getAttribute("userinfo");
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("memberNo", userBean.getMember().getMemberNo());
		jsonMap.put("company", userBean.getMember().getName());
		jsonMap.put("num", apply.getApplyNum().toString());
		jsonMap.put("price", apply.getPrice());
		jsonMap.put("applyType", apply.getApplyType().toString());
		jsonMap.put("recordNumber", apply.getSerialNumber());
//		String json =JsonMapper.nonEmptyMapper().toJson(jsonMap);
//		String msg = httpClient.postJson("http://192.168.1.234/houtai/admin.php/Index/purchase", json);
		
		String msg = httpClient.post("http://192.168.1.234/houtai/admin.php/Index/purchase", jsonMap);
		if (!StringUtils.notEquals(msg, "success")){
			map.put("isok", "ok");
		}else{
			map.put("isok", msg);
		}
		
		try {
		} catch (Exception e) {
			SxjLogger.error("保存采购申请单出错 ", e, this.getClass());
			map.put("isok", "no");
		}
		return map;
	}
	/**
	 * 添加更新申请单数据
	 * 
	 * @param json
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "updateApply")
	@ResponseBody
	public void updateApply(@RequestBody ApplyEntity applyEntity,
			HttpServletResponse response, HttpServletRequest request)
			throws IOException {
		PrintWriter out = response.getWriter();
		try {
			purchaseService.updateApply(applyEntity);
			out.print("1");
			response.setContentType("text/plain;UTF-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
		} catch (Exception e) {
			SxjLogger.error("更新申请单数据出错 ", e, this.getClass());
			out.print("0");
		} finally {
			out.flush();
			out.close();
		}

	}
}
