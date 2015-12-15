package com.sxj.supervisor.website.controller.purchase;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import third.rewrite.fastdfs.NameValuePair;
import third.rewrite.fastdfs.service.IStorageClientService;

import com.sxj.spring.modules.mapper.JsonMapper;
import com.sxj.supervisor.entity.contract.ContractEntity;
import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.entity.purchase.ApplyEntity;
import com.sxj.supervisor.entity.purchase.PurchaseEntity;
import com.sxj.supervisor.entity.purchase.ReleaseRecordEntity;
import com.sxj.supervisor.entity.record.RecordEntity;
import com.sxj.supervisor.enu.record.ContractTypeEnum;
import com.sxj.supervisor.enu.record.RecordConfirmStateEnum;
import com.sxj.supervisor.enu.record.RecordFlagEnum;
import com.sxj.supervisor.enu.record.RecordStateEnum;
import com.sxj.supervisor.enu.record.RecordTypeEnum;
import com.sxj.supervisor.model.comet.MessageChannel;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.model.login.SupervisorPrincipal;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.supervisor.service.member.IMemberService;
import com.sxj.supervisor.service.purchase.IPurchaseService;
import com.sxj.supervisor.service.record.IRecordService;
import com.sxj.supervisor.website.controller.BaseController;
import com.sxj.util.comet.CometServiceImpl;
import com.sxj.util.common.FileUtil;
import com.sxj.util.common.ISxjHttpClient;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.WebException;
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

	@Autowired
	private IRecordService recordService;

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
			HttpServletResponse response, HttpServletRequest request)
			throws IOException {
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
	public String queryApply(ModelMap map, ApplyEntity applyEntity,
			HttpSession session) {
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
	 * 
	 * @param apply
	 * @param response
	 * @param request
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "saveApply")
	public @ResponseBody Map<String, String> saveApply(ApplyEntity apply,
			HttpServletResponse response, HttpServletRequest request,
			HttpSession session) throws IOException {
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
		// String json =JsonMapper.nonEmptyMapper().toJson(jsonMap);
		// String msg =
		// httpClient.postJson("http://192.168.1.234/houtai/admin.php/Index/purchase",
		// json);

		String msg = httpClient
				.post("http://192.168.1.234/houtai/admin.php/Index/purchase",
						jsonMap);
		if (!StringUtils.notEquals(msg, "success")) {
			map.put("isok", "ok");
		} else {
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

	/**
	 * 添加备案信息
	 * 
	 * @param json
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws WebException
	 */
	@RequestMapping(value = "addRecord")
	@ResponseBody
	public void addRecord(@RequestBody RecordEntity record,
			HttpServletResponse response, HttpServletRequest request)
			throws IOException, WebException {
		PrintWriter out = response.getWriter();
		Map<String, String> map = new HashMap<String, String>();
		try {
			MemberEntity memberB = memberService
					.memberInfo(record.getApplyId());
			MemberEntity memberA = memberService.memberInfo(record
					.getMemberIdA());
			record.setApplyId(memberB.getMemberNo());
			record.setApplyName(memberB.getName());
			record.setMemberIdB(memberB.getMemberNo());
			record.setMemberIdA(memberA.getMemberNo());
			record.setMemberNameA(memberA.getName());
			record.setMemberNameB(memberB.getName());
			record.setState(RecordStateEnum.NOBINDING);
			record.setType(RecordTypeEnum.CONTRACT);
			if (record.getRecordType() == 1) {
				record.setContractType(ContractTypeEnum.GLASS);
			} else if (record.getRecordType() == 2) {
				record.setContractType(ContractTypeEnum.EXTRUSIONS);
			} else {
				throw new WebException("错误的合同类型");
			}
			record.setApplyDate(new Date());
			record.setDelState(false);
			record.setFlag(RecordFlagEnum.B);
			record.setConfirmState(RecordConfirmStateEnum.ACCEPTED);
			recordService.addRecord(record);
			map.put("state", "1");
			map.put("recordNo", record.getRecordNo());
			out.print(JsonMapper.nonEmptyMapper().toJson(map));
			response.setContentType("text/plain;UTF-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
		} catch (Exception e) {
			map.put("state", "0");
			SxjLogger.error("新增备案出错 ", e, this.getClass());
			throw new WebException("新增备案错误");
		} finally {
			out.flush();
			out.close();
		}

	}

	/**
	 * 确认合同
	 * @param record
	 * @param response
	 * @param request
	 * @throws WebException
	 * @throws IOException
	 */
	@RequestMapping("confirmContract")
	@ResponseBody
	public void confirmContract(@RequestBody RecordEntity record,
			HttpServletResponse response, HttpServletRequest request)
			throws WebException, IOException {
		PrintWriter out = response.getWriter();
		try {
			ContractEntity contract = contractService
					.getContractEntityByNo(record.getContractNo());
			Assert.notNull(contract, "该合同不存在");
			MemberEntity member = memberService.memberInfo(record.getApplyId());
			Assert.notNull(member, "该会员不存在");
			recordService.modifyState(contract.getId(), member.getType());
			String key = MessageChannel.WEBSITE_RECORD_MESSAGE
					+ member.getMemberNo();
			List<String> messageList = CometServiceImpl.get(key);
			if (messageList != null && messageList.size() > 0) {
				for (int i = 0; i < messageList.size(); i++) {
					String message = messageList.get(i);
					if (message.contains(record.getId())) {
						CometServiceImpl.remove(key, message);
					}
				}
			}
			CometServiceImpl
					.subCount(MessageChannel.MEMBER_CONTRACT_MESSAGE_COUNT
							+ member.getMemberNo());
			out.print("1");
			response.setContentType("text/plain;UTF-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
		} catch (Exception e) {
			out.print("0");
			SxjLogger.error("确认备案信息错误", e, this.getClass());
			throw new WebException("确认备案信息错误");
		} finally {
			out.flush();
			out.close();
		}

	}
	/**
	 * 批量获取合同状态
	 * @param contractNos
	 * @param response
	 * @param request
	 * @throws WebException
	 * @throws IOException
	 */
	@RequestMapping("getContractState")
	public void getContractState(String contractNos,
			HttpServletResponse response, HttpServletRequest request)
			throws WebException, IOException {
		PrintWriter out = response.getWriter();
		try {
			Map<String, Integer> map = purchaseService.getContractState(contractNos);
			out.print(JsonMapper.nonEmptyMapper().toJson(map));
			response.setContentType("text/plain;UTF-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
		} catch (Exception e) {
			out.print("0");
			SxjLogger.error("获取合同状态错误", e, this.getClass());
			throw new WebException("获取合同状态错误");
		} finally {
			out.flush();
			out.close();
		}

	}
}
