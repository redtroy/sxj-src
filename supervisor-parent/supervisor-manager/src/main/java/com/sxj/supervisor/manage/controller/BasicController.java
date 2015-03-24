package com.sxj.supervisor.manage.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import third.rewrite.fastdfs.NameValuePair;
import third.rewrite.fastdfs.service.IStorageClientService;

import com.sxj.redis.core.pubsub.RedisTopics;
import com.sxj.spring.modules.mapper.JsonMapper;
import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.entity.rfid.base.RfidSupplierEntity;
import com.sxj.supervisor.entity.system.FunctionEntity;
import com.sxj.supervisor.entity.system.OperatorLogEntity;
import com.sxj.supervisor.entity.system.SystemAccountEntity;
import com.sxj.supervisor.manage.login.SupervisorShiroRedisCache;
import com.sxj.supervisor.model.comet.MessageChannel;
import com.sxj.supervisor.model.comet.RfidChannel;
import com.sxj.supervisor.model.member.MemberQuery;
import com.sxj.supervisor.model.rfid.base.RfidSupplierQuery;
import com.sxj.supervisor.model.system.FunctionModel;
import com.sxj.supervisor.service.member.IMemberService;
import com.sxj.supervisor.service.rfid.base.IRfidSupplierService;
import com.sxj.supervisor.service.system.IFunctionService;
import com.sxj.supervisor.service.system.IQueryOperation;
import com.sxj.supervisor.service.system.IRoleService;
import com.sxj.supervisor.service.system.ISystemAccountService;
import com.sxj.util.comet.CometServiceImpl;
import com.sxj.util.common.FileUtil;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
public class BasicController extends BaseController
{
    
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
    private IStorageClientService storageClientService;
    
    @Autowired
    private IQueryOperation operatorService;
    
    @Autowired
    private RedisTopics topics;
    
    //    @PostConstruct
    //    public void init()
    //    {
    //        CometEngine engine = CometContext.getInstance().getEngine();
    //        // 启动 Comet Server Thread
    //        MessageThread cometServer = MessageThread.newInstance(engine);
    //        RTopic<String> topic1 = topics.getTopic("topic1");
    //        topic1.addListener(new CometMessageListener(cometServer));
    //    }
    
    @RequestMapping("notifyComet")
    public @ResponseBody void notifyComet(String channelName)
    {
        CometServiceImpl.sendMessage("topic1", channelName);
    }
    
    public void uEdit(HttpServletRequest request, HttpServletResponse response)
    {
        //        request.setCharacterEncoding("utf-8");
        //        response.setHeader("Content-Type", "text/html");
        //        
        //        String rootPath = application.getRealPath("/");
        //        
        //        out.write(new ActionEnter(request, rootPath).exec());
        
    }
    
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
    
    @RequestMapping("error")
    public String ToError()
    {
        return "manage/500";
    }
    
    @RequestMapping("404")
    public String To404()
    {
        return "manage/404";
    }
    
    @RequestMapping("index")
    public String ToIndex()
    {
        Subject user = SecurityUtils.getSubject();
        SystemAccountEntity userName = (SystemAccountEntity) user.getPrincipal();
        if (userName == null)
        {
            return LOGIN;
        }
        else
        {
            SystemAccountEntity newAccount = accountService.getAccountByAccount(userName.getAccount());
            if (newAccount == null)
            {
                return LOGIN;
            }
            return INDEX;
        }
    }
    
    @RequestMapping("to_login")
    public String ToLogin()
    {
        return LOGIN;
    }
    
    @RequestMapping("login")
    public String login(String account, String password, HttpSession session,
            HttpServletRequest request, ModelMap map)
    {
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
            PrincipalCollection principals = SecurityUtils.getSubject()
                    .getPrincipals();
            String accountNo = user.getAccountNo();
            SupervisorShiroRedisCache.addToMap(accountNo, principals);
        }
        catch (AuthenticationException e)
        {
            map.put("account", account);
            map.put("message", "用户名或密码错误");
            return LOGIN;
            
        }
        if (currentUser.isAuthenticated())
        {
            session.setAttribute("userinfo", user);
            user.setLastLogin(new Date());
            accountService.updateLoginTime(user.getId());
            return "redirect:" + getBasePath(request) + "index.htm";
        }
        else
        {
            map.put("account", account);
            map.put("message", "登陆失败");
            return LOGIN;
        }
    }
    
    @RequestMapping("logout")
    public String logout(HttpServletRequest request)
    {
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
    public String toMenu(ModelMap map)
    {
        Subject user = SecurityUtils.getSubject();
        SystemAccountEntity userName = (SystemAccountEntity) user.getPrincipal();
        if (userName == null)
        {
            return LOGIN;
        }
        List<FunctionModel> list = roleService.getRoleFunction(userName.getId());
        map.put("list", list);
        return "manage/menu";
    }
    
    @RequestMapping("menu_path")
    public String menuPath(HttpServletRequest request, ModelMap map)
            throws WebException
    {
        try
        {
            HttpSession session = request.getSession(false);
            if (session.getAttribute("function") != null)
            {
                String functionId = (String) session.getAttribute("function");
                FunctionEntity function = functionService.getFunction(functionId);
                if (function != null)
                {
                    if (!function.getParentId().equals("0"))
                    {
                        FunctionEntity parent = functionService.getFunction(function.getParentId());
                        map.put("parentTitle", parent.getTitle());
                    }
                    map.put("title", function.getTitle());
                }
            }
            return "manage/menu-path";
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new WebException("系统错误");
        }
    }
    
    @RequestMapping("upload")
    public void uploadFile(HttpServletRequest request,
            HttpServletResponse response) throws WebException
    {
        try
        {
            Map<String, Object> map = new HashMap<String, Object>();
            if (!(request instanceof DefaultMultipartHttpServletRequest))
            {
                return;
            }
            DefaultMultipartHttpServletRequest re = (DefaultMultipartHttpServletRequest) request;
            Map<String, MultipartFile> fileMaps = re.getFileMap();
            Collection<MultipartFile> files = fileMaps.values();
            List<String> fileIds = new ArrayList<String>();
            for (MultipartFile myfile : files)
            {
                if (myfile.isEmpty())
                {
                    System.err.println("文件未上传");
                }
                else
                {
                    String originalName = myfile.getOriginalFilename();
                    String extName = FileUtil.getFileExtName(originalName);
                    // 上传文件
                    String filePath = storageClientService.uploadFile(null,
                            new ByteArrayInputStream(myfile.getBytes()),
                            myfile.getBytes().length,
                            extName.toUpperCase());
                    SxjLogger.info("manageUploadFilePath=" + filePath,
                            this.getClass());
                    fileIds.add(filePath);
                    
                    // 上传元数据
                    NameValuePair[] metaList = new NameValuePair[1];
                    metaList[0] = new NameValuePair("originalName",
                            originalName);
                    storageClientService.overwriteMetadata(filePath, metaList);
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
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new WebException("文件上传错误");
        }
        
    }
    
    @RequestMapping("filesort")
    public @ResponseBody List<String> fileSort(String fileId)
            throws WebException
    {
        List<String> sortFile = new ArrayList<String>();
        try
        {
            if (StringUtils.isEmpty(fileId))
            {
                return sortFile;
            }
            String[] fileids = fileId.split(",");
            Map<String, String> nameMap = new TreeMap<String, String>();
            Map<String, NameValuePair[]> values = storageClientService.getMetadata(fileids);
            for (String key : values.keySet())
            {
                if (key == null)
                {
                    continue;
                }
                NameValuePair[] value = values.get(key);
                nameMap.put(key, value[0].getValue());
            }
            List<Map.Entry<String, String>> mappingList = null;
            // 通过ArrayList构造函数把map.entrySet()转换成list
            mappingList = new ArrayList<Map.Entry<String, String>>(
                    nameMap.entrySet());
            // 通过比较器实现比较排序
            Collections.sort(mappingList,
                    new Comparator<Map.Entry<String, String>>()
                    {
                        public int compare(Map.Entry<String, String> mapping1,
                                Map.Entry<String, String> mapping2)
                        {
                            return mapping1.getValue()
                                    .compareTo(mapping2.getValue());
                        }
                    });
            for (Map.Entry<String, String> mapping : mappingList)
            {
                sortFile.add(mapping.getKey());
            }
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new WebException("获取文件元信息错误");
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
            String keyword) throws IOException
    {
        MemberQuery mq = new MemberQuery();
        if (keyword != "" && keyword != null)
        {
            mq.setMemberName(keyword);
        }
        List<MemberEntity> list = memberService.queryMembers(mq);
        List<String> strlist = new ArrayList<String>();
        String sb = "";
        for (MemberEntity memberEntity : list)
        {
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
            String keyword) throws IOException
    {
        RfidSupplierQuery query = new RfidSupplierQuery();
        if (keyword != "" && keyword != null)
        {
            query.setName(keyword);
        }
        List<RfidSupplierEntity> list = supplierService.querySupplier(query);
        List<String> strlist = new ArrayList<String>();
        String sb = "";
        for (RfidSupplierEntity supplier : list)
        {
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
    
    public static String getIpAddr(HttpServletRequest request)
    {
        String strUserIp = null;
        /** * */
        // Apache 代理 解决IP地址问题
        strUserIp = request.getHeader("X-Forwarded-For");
        if (strUserIp == null || strUserIp.length() == 0
                || "unknown".equalsIgnoreCase(strUserIp))
        {
            strUserIp = request.getHeader("Proxy-Client-IP");
        }
        if (strUserIp == null || strUserIp.length() == 0
                || "unknown".equalsIgnoreCase(strUserIp))
        {
            strUserIp = request.getHeader("WL-Proxy-Client-IP");
        }
        if (strUserIp == null || strUserIp.length() == 0
                || "unknown".equalsIgnoreCase(strUserIp))
        {
            strUserIp = request.getRemoteAddr();
        }
        // 解决获取多网卡的IP地址问题
        if (strUserIp != null)
        {
            strUserIp = strUserIp.split(",")[0];
        }
        else
        {
            strUserIp = "127.0.0.1";
        }
        // 解决获取IPv6地址 暂时改为本机地址模式
        if (strUserIp.length() > 16)
        {
            strUserIp = "127.0.0.1";
        }
        return strUserIp;
        // 没有IP Apache 代理 解决IP地址问题
        // strUserIp=request.getRemoteAddr();
        // if (strUserIp != null) {strUserIp = strUserIp.split(",")[0];}
        // return strUserIp;
    }
    
    @RequestMapping("enter")
    @ResponseBody
    public void enter(HttpSession session, HttpServletRequest request,
            String url)
    {
        Date enterTime = (Date) session.getAttribute("enterTime");
        String currentUrl = (String) session.getAttribute("currentUrl");
        String nextUrl = (String) session.getAttribute("nextUrl");
        if (currentUrl == null)
        {
            session.setAttribute("currentUrl", request.getHeader("Referer"));
            
        }
        session.setAttribute("previousUrl", currentUrl);
        session.setAttribute("currentUrl", nextUrl);
        session.setAttribute("nextUrl", url);
        
        if (enterTime != null)
        {
            SystemAccountEntity account = getLoginInfo(session);
            OperatorLogEntity log = new OperatorLogEntity();
            log.setAccountNo(account.getAccountNo());
            log.setOperatorTime(new Date());
            log.setLogs("Entering page" + session.getAttribute("previousUrl")
                    + "------current:    " + session.getAttribute("currentUrl")
                    + "------next:     " + session.getAttribute("nextUrl"));
            operatorService.addOperatorLog(log);
        }
        session.setAttribute("enterTime", new Date());
        
    }
    
    /**
     * 甲方联想
     * 
     * @param request
     * @param response
     * @param keyword
     * @return
     * @throws IOException
     */
    @RequestMapping("autoCompleA")
    public @ResponseBody Map<String, String> autoCompleA(
            HttpServletRequest request, HttpServletResponse response,
            String keyword) throws IOException
    {
        MemberQuery mq = new MemberQuery();
        if (keyword != "" && keyword != null)
        {
            mq.setMemberName(keyword);
        }
        mq.setMemberType(0);
        List<MemberEntity> list = memberService.queryMembers(mq);
        List strlist = new ArrayList();
        String sb = "";
        for (MemberEntity memberEntity : list)
        {
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
     * 乙方联想
     * 
     * @param request
     * @param response
     * @param keyword
     * @return
     * @throws IOException
     */
    @RequestMapping("autoCompleB")
    public @ResponseBody Map<String, String> autoCompleB(
            HttpServletRequest request, HttpServletResponse response,
            String keyword) throws IOException
    {
        MemberQuery mq = new MemberQuery();
        if (keyword != "" && keyword != null)
        {
            mq.setMemberName(keyword);
        }
        mq.setMemberTypeB(0);
        List<MemberEntity> list = memberService.queryMembers(mq);
        List strlist = new ArrayList();
        String sb = "";
        for (MemberEntity memberEntity : list)
        {
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
    
    public static void main(String... args)
    {
        try
        {
            java.net.HttpURLConnection connection = (HttpURLConnection) new URL(
                    "http://192.168.1.115:8080/rfid-website/rfid/test").openConnection();
            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write("contractNo=1233&rfidNos=1".getBytes());
            outputStream.flush();
            connection.getInputStream();
            while (true)
            {
                
            }
        }
        catch (MalformedURLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @RequestMapping("message")
    public @ResponseBody Map<String, Object> message(
            HttpServletRequest request, HttpServletResponse response,
            String channelName) throws IOException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        if (channelName.equals(MessageChannel.RECORD_MESSAGE)
                || channelName.equals(RfidChannel.RFID_APPLY_MESSAGE)
                || channelName.contains(MessageChannel.WEBSITE_PAY_MESSAGE)
                || channelName.contains(MessageChannel.WEBSITE_FINANCE_MESSAGE)
                || channelName.equals(MessageChannel.MEMBER_MESSAGE)
                || channelName.equals(MessageChannel.MEMBER_PERFECT_MESSAGE))
        {
            Long count = CometServiceImpl.getCount(channelName);
            SxjLogger.debug("Sending Message to Comet Client:" + count,
                    getClass());
            if (count > 0)
            {
                map.put("count", count);
                map.put("channelName", channelName);
                return map;
            }
        }
        else
        {
            List<String> cache = CometServiceImpl.get(channelName);
            SxjLogger.debug("Sending Message to Comet Client:" + cache,
                    getClass());
            if (cache != null && cache.size() > 0)
            {
                map.put("cache", cache);
                map.put("channelName", channelName);
                return map;
            }
        }
        return null;
    }
}
