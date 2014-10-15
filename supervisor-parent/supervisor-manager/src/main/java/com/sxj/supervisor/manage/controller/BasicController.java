package com.sxj.supervisor.manage.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.csource.common.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.sxj.file.common.LocalFileUtil;
import com.sxj.file.fastdfs.IFileUpLoad;
import com.sxj.spring.modules.mapper.JsonMapper;
import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.entity.rfid.base.RfidSupplierEntity;
import com.sxj.supervisor.entity.system.FunctionEntity;
import com.sxj.supervisor.entity.system.SystemAccountEntity;
import com.sxj.supervisor.model.member.MemberQuery;
import com.sxj.supervisor.model.rfid.base.RfidSupplierQuery;
import com.sxj.supervisor.model.system.FunctionModel;
import com.sxj.supervisor.service.member.IMemberService;
import com.sxj.supervisor.service.rfid.base.IRfidSupplierService;
import com.sxj.supervisor.service.system.IFunctionService;
import com.sxj.supervisor.service.system.IRoleService;
import com.sxj.supervisor.service.system.ISystemAccountService;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
public class BasicController extends BaseController {

	@Autowired
	private IRoleService roleService;

	@Autowired
	private ISystemAccountService accountService;

	@Autowired
	private IFunctionService functionService;

	@Autowired
	private IMemberService memberService;

	@Autowired
	private IRfidSupplierService supplierService;

	@Autowired
	private IFileUpLoad fastDfsClient;

	@RequestMapping("footer")
	public String ToFooter() {
		return "manage/footer";
	}

	@RequestMapping("head")
	public String ToHeader() {
		return "manage/head";
	}

	@RequestMapping("error")
	public String ToError() {
		return "manage/500";
	}

	@RequestMapping("404")
	public String To404() {
		return "manage/404";
	}

	@RequestMapping("index")
	public String ToIndex() {
		Subject user = SecurityUtils.getSubject();
		SystemAccountEntity userName = (SystemAccountEntity) user
				.getPrincipal();
		if (userName == null) {
			return LOGIN;
		} else {
			return INDEX;
		}
	}

	@RequestMapping("to_login")
	public String ToLogin() {
		return LOGIN;
	}

	@RequestMapping("login")
	public String login(String account, String password, HttpSession session,
			HttpServletRequest request, ModelMap map) {
		SystemAccountEntity user = accountService.getAccountByAccount(account);
		if (user == null) {
			map.put("message", "用户名不存在");
			return LOGIN;
		}
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(account,
				password);
		try {
			currentUser.login(token);
		} catch (AuthenticationException e) {
			map.put("account", account);
			map.put("message", "用户名或密码错误");
			return LOGIN;

		}
		if (currentUser.isAuthenticated()) {
			session.setAttribute("userinfo", user);
			user.setLastLogin(new Date());
			accountService.updateLoginTime(user.getId());
			return "redirect:" + getBasePath(request) + "index.htm";
		} else {
			map.put("account", account);
			map.put("message", "登陆失败");
			return LOGIN;
		}
	}

	@RequestMapping("logout")
	public String logout(HttpServletRequest request) {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		return "redirect:" + getBasePath(request) + "to_login.htm";

	}

	/**
	 * 左侧菜单
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("menu")
	public String toMenu(ModelMap map) {
		Subject user = SecurityUtils.getSubject();
		SystemAccountEntity userName = (SystemAccountEntity) user
				.getPrincipal();
		if (userName == null) {
			return LOGIN;
		}
		List<FunctionModel> list = roleService
				.getRoleFunction(userName.getId());
		map.put("list", list);
		return "manage/menu";
	}

	@RequestMapping("menu_path")
	public String menuPath(HttpServletRequest request, ModelMap map)
			throws WebException {
		try {
			HttpSession session = request.getSession(false);
			if (session.getAttribute("function") != null) {
				String functionId = (String) session.getAttribute("function");
				FunctionEntity function = functionService
						.getFunction(functionId);
				if (function != null) {
					if (!function.getParentId().equals("0")) {
						FunctionEntity parent = functionService
								.getFunction(function.getParentId());
						map.put("parentTitle", parent.getTitle());
					}
					map.put("title", function.getTitle());
				}
			}
			return "manage/menu-path";
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new WebException("系统错误");
		}
	}

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

	@RequestMapping("filesort")
	public @ResponseBody List<String> fileSort(String fileId)
			throws IOException {
		List<String> sortFile = new ArrayList<String>();
		try {
			String[] fileids = fileId.split(",");
			Map<String, String> nameMap = new TreeMap<String, String>();
			for (int i = 0; i < fileids.length; i++) {
				List<NameValuePair> values = fastDfsClient
						.getMetaList(fileids[i]);
				String value = values.get(0).getValue();
				nameMap.put(fileids[i], value);
			}
			List<Map.Entry<String, String>> mappingList = null;
			// 通过ArrayList构造函数把map.entrySet()转换成list
			mappingList = new ArrayList<Map.Entry<String, String>>(
					nameMap.entrySet());
			// 通过比较器实现比较排序
			Collections.sort(mappingList,
					new Comparator<Map.Entry<String, String>>() {
						public int compare(Map.Entry<String, String> mapping1,
								Map.Entry<String, String> mapping2) {
							return mapping1.getValue().compareTo(
									mapping2.getValue());
						}
					});
			for (Map.Entry<String, String> mapping : mappingList) {
				sortFile.add(mapping.getKey());
			}
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
		}
		return sortFile;

	}

	/**
	 * 自动感应会员
	 * 
	 * @param request
	 * @param response
	 * @param keyword
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("autoComple")
	public @ResponseBody Map<String, String> autoComple(
			HttpServletRequest request, HttpServletResponse response,
			String keyword) throws IOException {
		MemberQuery mq = new MemberQuery();
		if (keyword != "" && keyword != null) {
			mq.setMemberName(keyword);
		}
		List<MemberEntity> list = memberService.queryMembers(mq);
		List strlist = new ArrayList();
		String sb = "";
		for (MemberEntity memberEntity : list) {
			sb = "{\"title\":\"" + memberEntity.getName() + "\",\"result\":\""
					+ memberEntity.getMemberNo() + "\"}";
			strlist.add(sb);
		}
		String json = "{\"data\":" + strlist.toString() + "}";
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
		return null;
	}

	/**
	 * 自动感应供应商
	 * 
	 * @param request
	 * @param response
	 * @param keyword
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("autoSupplier")
	public @ResponseBody Map<String, String> autoSupplier(
			HttpServletRequest request, HttpServletResponse response,
			String keyword) throws IOException {
		RfidSupplierQuery query = new RfidSupplierQuery();
		if (keyword != "" && keyword != null) {
			query.setName(keyword);
		}
		List<RfidSupplierEntity> list = supplierService.querySupplier(query);
		List strlist = new ArrayList();
		String sb = "";
		for (RfidSupplierEntity supplier : list) {
			sb = "{\"title\":\"" + supplier.getName() + "\",\"result\":\""
					+ supplier.getSupplierNo() + "\"}";
			strlist.add(sb);
		}
		String json = "{\"data\":" + strlist.toString() + "}";
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
		return null;
	}
}
