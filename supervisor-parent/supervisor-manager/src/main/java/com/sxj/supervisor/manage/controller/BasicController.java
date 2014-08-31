package com.sxj.supervisor.manage.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.entity.system.SystemAccountEntity;
import com.sxj.supervisor.model.system.FunctionModel;
import com.sxj.supervisor.service.system.IRoleService;
import com.sxj.supervisor.service.system.ISystemAccountService;

@Controller
public class BasicController extends BaseController
{
    
    @Autowired
    private IRoleService roleService;
    
    @Autowired
    private ISystemAccountService accountService;
    
    @RequestMapping("footer")
    public String ToFooter()
    {
        return "manage/footer";
    }
    
    @RequestMapping("head")
    public String ToHeader()
    {
        return "manage/head";
    }
    
    @RequestMapping("index")
    public String ToIndex()
    {
        return INDEX;
    }
    
    @RequestMapping("to_login")
    public String ToLogin()
    {
        return LOGIN;
    }
    
    @RequestMapping("logout")
    public String logout()
    {
        SecurityUtils.getSubject().logout();
        return LOGIN;
    }
    
    @RequestMapping("login")
    public String login(String account, String password, HttpSession session,
            HttpServletRequest request, ModelMap map)
    {
        map.put("account", account);
        SystemAccountEntity user = accountService.getAccountByAccount(account);
        if (user == null)
        {
            map.put("message", "用户名不存在");
            return LOGIN;
        }
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(account,
                password);
        try
        {
            currentUser.login(token);
        }
        catch (AuthenticationException e)
        {
            map.put("message", "用户名或密码错误");
            return LOGIN;
            
        }
        if (currentUser.isAuthenticated())
        {
            session.setAttribute("userinfo", user);
            return "redirect:" + getBasePath(request) + "index.htm";
        }
        else
        {
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
    public String ToMenu(ModelMap map)
    {
        Subject user = SecurityUtils.getSubject();
        SystemAccountEntity userName = (SystemAccountEntity) user.getPrincipal();
        if (userName == null)
        {
            return "403";
        }
        List<FunctionModel> list = roleService.getRoleFunction(userName.getId());
        map.put("list", list);
        return "manage/menu";
    }
    
}
