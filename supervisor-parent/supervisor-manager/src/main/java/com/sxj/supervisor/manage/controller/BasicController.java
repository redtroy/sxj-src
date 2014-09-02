package com.sxj.supervisor.manage.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.sxj.file.common.LocalFileUtil;
import com.sxj.file.fastdfs.FastDFSImpl;
import com.sxj.file.fastdfs.FileGroup;
import com.sxj.file.fastdfs.IFileUpLoad;
import com.sxj.spring.modules.mapper.JsonMapper;
import com.sxj.supervisor.entity.system.FunctionEntity;
import com.sxj.supervisor.entity.system.SystemAccountEntity;
import com.sxj.supervisor.model.system.FunctionModel;
import com.sxj.supervisor.service.system.IFunctionService;
import com.sxj.supervisor.service.system.IRoleService;
import com.sxj.supervisor.service.system.ISystemAccountService;
import com.sxj.util.exception.WebException;

@Controller
public class BasicController extends BaseController {

	@Autowired
	private IRoleService roleService;

	@Autowired
	private ISystemAccountService accountService;

	@Autowired
	private IFunctionService functionService;

	@RequestMapping("footer")
	public String ToFooter() {
		return "manage/footer";
	}

	@RequestMapping("head")
	public String ToHeader() {
		return "manage/head";
	}

	@RequestMapping("index")
	public String ToIndex() {
		return INDEX;
	}

	@RequestMapping("to_login")
	public String ToLogin() {
		return LOGIN;
	}

	@RequestMapping("logout")
	public String logout() {
		SecurityUtils.getSubject().logout();
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
			return "redirect:" + getBasePath(request) + "index.htm";
		} else {
			map.put("account", account);
			map.put("message", "登陆失败");
			return LOGIN;
		}
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
			return "403";
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
			if (session.getAttribute("functionId") != null) {
				String functionId = (String) session.getAttribute("functionId");
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
				IFileUpLoad dfs = new FastDFSImpl(FileGroup.imgGroup);
				String fileId = dfs.uploadFile(myfile.getBytes(), LocalFileUtil
						.getFileExtName(myfile.getOriginalFilename()));
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
