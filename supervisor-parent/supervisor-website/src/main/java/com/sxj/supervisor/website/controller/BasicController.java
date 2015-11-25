package com.sxj.supervisor.website.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.bouncycastle.openssl.PEMReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import sun.security.x509.X500Name;
import third.rewrite.fastdfs.NameValuePair;
import third.rewrite.fastdfs.service.IStorageClientService;

import com.sxj.redis.core.pubsub.RedisTopics;
import com.sxj.spring.modules.mapper.JsonMapper;
import com.sxj.supervisor.entity.contract.ContractItemEntity;
import com.sxj.supervisor.entity.developers.DevelopersEntity;
import com.sxj.supervisor.entity.gov.GovEntity;
import com.sxj.supervisor.entity.member.AccountEntity;
import com.sxj.supervisor.entity.member.CertificateEntity;
import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.entity.member.MemberFunctionEntity;
import com.sxj.supervisor.entity.member.MemberLogEntity;
import com.sxj.supervisor.entity.member.MemberToMemberEntity;
import com.sxj.supervisor.entity.message.TenderMessageEntity;
import com.sxj.supervisor.entity.system.AreaEntity;
import com.sxj.supervisor.enu.member.AccountStatesEnum;
import com.sxj.supervisor.enu.member.MemberCheckStateEnum;
import com.sxj.supervisor.enu.member.MemberStatesEnum;
import com.sxj.supervisor.enu.member.MemberTypeEnum;
import com.sxj.supervisor.enu.message.MessageStateEnum;
import com.sxj.supervisor.model.comet.MessageChannel;
import com.sxj.supervisor.model.comet.RfidChannel;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.model.contract.ContractQuery;
import com.sxj.supervisor.model.login.SupervisorPrincipal;
import com.sxj.supervisor.model.member.MemberFunctionModel;
import com.sxj.supervisor.model.member.MemberQuery;
import com.sxj.supervisor.model.message.TenderMessageModel;
import com.sxj.supervisor.model.message.TenderMessageQuery;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.supervisor.service.developers.IDevelopersService;
import com.sxj.supervisor.service.gov.IGovService;
import com.sxj.supervisor.service.member.IAccountService;
import com.sxj.supervisor.service.member.IMemberFunctionService;
import com.sxj.supervisor.service.member.IMemberImageService;
import com.sxj.supervisor.service.member.IMemberLogService;
import com.sxj.supervisor.service.member.IMemberRoleService;
import com.sxj.supervisor.service.member.IMemberService;
import com.sxj.supervisor.service.member.IMemberToMemberService;
import com.sxj.supervisor.service.message.ITenderMessageService;
import com.sxj.supervisor.service.system.IAreaService;
import com.sxj.supervisor.service.tasks.IWindDoorService;
import com.sxj.supervisor.website.login.SupervisorShiroRedisCache;
import com.sxj.supervisor.website.login.SupervisorSiteToken;
import com.sxj.util.comet.CometServiceImpl;
import com.sxj.util.common.AuthImg;
import com.sxj.util.common.FileUtil;
import com.sxj.util.common.StringUtils;
import com.sxj.util.common.ValidateImage;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
public class BasicController extends BaseController
{
    
    @Autowired
    private IMemberFunctionService functionService;
    
    @Autowired
    private IMemberService memberService;
    
    @Autowired
    private IAccountService accountService;
    
    @Autowired
    private IMemberRoleService roleService;
    
    @Autowired
    private IMemberLogService logService;
    
    @Autowired
    private IAreaService areaService;
    
    @Autowired
    private IStorageClientService storageClientService;
    
    @Autowired
    private RedisTopics topics;
    
    @Autowired
    private IDevelopersService developerService;
    
    @Autowired
    private ITenderMessageService tenderMessageService;
    
    @Autowired
    private IWindDoorService windDoorSercice;
    
    @Autowired
    private IGovService govService;
    
    @Autowired
    private IMemberToMemberService memberToMemberService;
    
    /**
     * getRecordState 合同service
     */
    @Autowired
    private IContractService contractService;
    
    @Autowired
    private IMemberImageService memberImageService;
    
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
    
    @RequestMapping("index")
    public String ToIndex(HttpServletRequest request,
            TenderMessageQuery messQuery, ModelMap map) throws WebException
    {
        HttpSession session = request.getSession(false);
        try
        {
            if (session == null || session.getAttribute("userinfo") == null)
            {
                return LOGIN;
            }
            else
            {
                SupervisorPrincipal info = getLoginInfo(session);
                if (info.getAccount() != null && info.getMember() != null)
                {
                    AccountEntity newAccount = accountService.getAccount(info.getAccount()
                            .getId());
                    if (newAccount == null)
                    {
                        return LOGIN;
                    }
                    if (newAccount.getState().equals(AccountStatesEnum.STOP))
                    {
                        return LOGIN;
                    }
                    if (StringUtils.isEmpty(newAccount.getPassword()))
                    {
                        return LOGIN;
                    }
                    if (!newAccount.getPassword().equals(info.getAccount()
                            .getPassword()))
                    {
                        return LOGIN;
                    }
                    return "site/member/account-index";
                }
                else if (info.getAccount() == null && info.getMember() != null)
                {
                    List<AreaEntity> cityList = areaService.getChildrenAreas("32");
                    MemberEntity member = memberService.getMember(info.getMember()
                            .getId());
                    if (member.getAccountNum() == null)
                    {
                        member.setAccountNum(0);
                    }
                    map.put("cityList", cityList);
                    map.put("member", member);
                    MemberToMemberEntity m = new MemberToMemberEntity();
                    
                    List<MemberToMemberEntity> mlist = memberToMemberService.queryInfo(member.getMemberNo());
                    map.put("mlist", mlist);
                    if (info.getMember().getFlag())
                    {
                        List<DevelopersEntity> totalKFSList = developerService.queryDeveloperList(new DevelopersEntity());
                        List<DevelopersEntity> kfsList = new ArrayList<DevelopersEntity>();
                        
                        MemberQuery query = new MemberQuery();
                        query.setMemberType(MemberTypeEnum.DAWP.getId());
                        query.setCheckState(MemberCheckStateEnum.CERTIFIED.getId());
                        query.setFilterStr(1);
                        List<MemberEntity> totalMCList = memberService.queryMembersWebSite(query);
                        List<MemberEntity> mcList = new ArrayList<MemberEntity>();
                        
                        query = new MemberQuery();
                        query.setMemberType(MemberTypeEnum.GLASSFACTORY.getId());
                        query.setCheckState(MemberCheckStateEnum.CERTIFIED.getId());
                        query.setFilterStr(1);
                        List<MemberEntity> totalBLList = memberService.queryMembersWebSite(query);
                        List<MemberEntity> blList = new ArrayList<MemberEntity>();
                        
                        query = new MemberQuery();
                        query.setMemberType(MemberTypeEnum.GENRESFACTORY.getId());
                        query.setCheckState(MemberCheckStateEnum.CERTIFIED.getId());
                        query.setFilterStr(1);
                        List<MemberEntity> totalXCList = memberService.queryMembersWebSite(query);
                        List<MemberEntity> xcList = new ArrayList<MemberEntity>();
                        
                        query = new MemberQuery();
                        query.setMemberType(MemberTypeEnum.PRODUCTS.getId());
                        query.setCheckState(MemberCheckStateEnum.CERTIFIED.getId());
                        query.setFilterStr(1);
                        List<MemberEntity> totalPJList = memberService.queryMembersWebSite(query);
                        List<MemberEntity> pjList = new ArrayList<MemberEntity>();
                        List<MemberEntity> fcList = new ArrayList<MemberEntity>();
                        if (totalMCList.size() > 18)
                        {
                            for (int i = 0; i < 18; i++)
                            {
                                Random rd1 = new Random();
                                int k1 = rd1.nextInt(totalMCList.size());
                                MemberEntity temMc = totalMCList.get(k1);
                                mcList.add(temMc);
                                totalMCList.remove(temMc);
                            }
                        }
                        else
                        {
                            mcList = totalMCList;
                        }
                        
                        if (totalBLList.size() > 18)
                        {
                            for (int i = 0; i < 18; i++)
                            {
                                Random rd2 = new Random();
                                int k2 = rd2.nextInt(totalBLList.size());
                                MemberEntity temBl = totalBLList.get(k2);
                                blList.add(temBl);
                                totalBLList.remove(temBl);
                            }
                        }
                        else
                        {
                            blList = totalBLList;
                        }
                        
                        if (totalXCList.size() > 18)
                        {
                            for (int i = 0; i < 18; i++)
                            {
                                Random rd3 = new Random();
                                int k3 = rd3.nextInt(totalXCList.size());
                                MemberEntity temXc = totalXCList.get(k3);
                                xcList.add(temXc);
                                totalXCList.remove(temXc);
                            }
                        }
                        else
                        {
                            xcList = totalXCList;
                        }
                        
                        if (totalKFSList.size() > 18)
                        {
                            for (int i = 0; i < 18; i++)
                            {
                                Random rd4 = new Random();
                                int k4 = rd4.nextInt(totalKFSList.size());
                                DevelopersEntity temKfs = totalKFSList.get(k4);
                                kfsList.add(temKfs);
                                totalKFSList.remove(temKfs);
                            }
                        }
                        else
                        {
                            kfsList = totalKFSList;
                        }
                        
                        if (totalPJList.size() > 18)
                        {
                            for (int i = 0; i < 18; i++)
                            {
                                Random rd5 = new Random();
                                int k5 = rd5.nextInt(totalPJList.size());
                                MemberEntity tempj = totalPJList.get(k5);
                                pjList.add(tempj);
                                totalPJList.remove(tempj);
                            }
                        }
                        else
                        {
                            pjList = totalPJList;
                        }
                        //
                        SupervisorPrincipal user = getLoginInfo(session);
                        if (user == null)
                        {
                            return LOGIN;
                        }
                        String memberNo = user.getMember().getMemberNo();
                        List<String> infoIdList = CometServiceImpl.get(MessageChannel.MEMBER_TENDER_MESSAGE_INFO);
                        if (infoIdList != null && infoIdList.size() > 0)
                        {
                            List<TenderMessageEntity> messageList = new ArrayList<TenderMessageEntity>();
                            for (Iterator<String> iterator = infoIdList.iterator(); iterator.hasNext();)
                            {
                                String infoId = iterator.next();
                                TenderMessageQuery query2 = new TenderMessageQuery();
                                query2.setMemberNo(memberNo);
                                query2.setInfoId(infoId);
                                List<TenderMessageModel> list = tenderMessageService.queryMessageList(query2);
                                if (list == null || list.size() == 0)
                                {
                                    TenderMessageEntity message = new TenderMessageEntity();
                                    message.setInfoId(infoId);
                                    message.setMemberNo(memberNo);
                                    message.setState(MessageStateEnum.UNREAD);
                                    messageList.add(message);
                                }
                            }
                            tenderMessageService.addMessage(messageList);
                        }
                        MemberQuery q = new MemberQuery();
                        q.setMemberType(MemberTypeEnum.FRAMEFACTORY.getId());
                        q.setCheckState(MemberCheckStateEnum.CERTIFIED.getId());
                        q.setFilterStr(1);
                        List<MemberEntity> totalfcList = memberService.queryMembersWebSite(q);
                        if (totalfcList.size() > 16)
                        {
                            for (int i = 0; i < 16; i++)
                            {
                                
                                Random rd5 = new Random();
                                int k5 = rd5.nextInt(totalfcList.size());
                                MemberEntity fc = totalfcList.get(k5);
                                fcList.add(fc);
                                totalfcList.remove(fc);
                            }
                        }
                        else
                        {
                            for (int i = 0; i < totalfcList.size(); i++)
                            {
                                fcList.add(totalfcList.get(i));
                            }
                        }
                        // TenderMessageQuery messQuery = new TenderMessageQuery();
                        messQuery.setPagable(true);
                        messQuery.setShowCount(6);
                        messQuery.setMemberNo(getLoginInfo(session).getMember()
                                .getMemberNo());
                        List<TenderMessageModel> messageList = tenderMessageService.queryMessageList(messQuery);
                        GovEntity gov = new GovEntity();
                        gov.setShowCount(6);
                        gov.setPagable(true);
                        List<GovEntity> list = govService.queryGovList(gov);
                        map.put("kfsList", kfsList);
                        map.put("mcList", mcList);
                        map.put("blList", blList);
                        map.put("xcList", xcList);
                        map.put("pjList", pjList);
                        map.put("messageList", messageList);
                        map.put("fcList", fcList);
                        map.put("govList", list);
                        map.put("query", messQuery);
                        
                        /*for (int i = 0; i < kfsList.size(); i++)
                        {
                            System.err.println("开发商:"+kfsList.get(i).getName());
                        }
                        for (int i = 0; i < mcList.size(); i++)
                        {
                            System.err.println("门窗:"+mcList.get(i).getName());
                        }
                        for (int i = 0; i < blList.size(); i++)
                        {
                            System.err.println("玻璃:"+blList.get(i).getName());
                        }
                        for (int i = 0; i < xcList.size(); i++)
                        {
                            System.err.println("型材:"+xcList.get(i).getName());
                        }
                        for (int i = 0; i < messageList.size(); i++)
                        {
                            System.err.println("招标:"+messageList.get(i).getInfoId());
                        }*/
                        if (info.getMember().getCheckState().ordinal() < 2)
                        {
                            return "site/member/member-profile";
                        }
                        return "site/member/platform_index";
                    }
                    else
                    {
                        return "site/member/edit-member";
                    }
                }
                else
                {
                    return LOGIN;
                }
                
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
    }
    
    @RequestMapping("newIndex")
    public String NewIndex(HttpServletRequest request, ModelMap map,
            HttpSession session) throws WebException
    {
        try
        {
            List<DevelopersEntity> totalKFSList = developerService.queryDeveloperList(new DevelopersEntity());
            List<DevelopersEntity> kfsList = new ArrayList<DevelopersEntity>();
            
            MemberQuery query = new MemberQuery();
            query.setMemberType(MemberTypeEnum.DAWP.getId());
            query.setCheckState(MemberCheckStateEnum.CERTIFIED.getId());
            query.setFilterStr(1);
            List<MemberEntity> totalMCList = memberService.queryMembers(query);
            List<MemberEntity> mcList = new ArrayList<MemberEntity>();
            
            query = new MemberQuery();
            query.setMemberType(MemberTypeEnum.GLASSFACTORY.getId());
            query.setCheckState(MemberCheckStateEnum.CERTIFIED.getId());
            query.setFilterStr(1);
            List<MemberEntity> totalBLList = memberService.queryMembers(query);
            List<MemberEntity> blList = new ArrayList<MemberEntity>();
            
            query = new MemberQuery();
            query.setMemberType(MemberTypeEnum.GENRESFACTORY.getId());
            query.setCheckState(MemberCheckStateEnum.CERTIFIED.getId());
            query.setFilterStr(1);
            List<MemberEntity> totalXCList = memberService.queryMembers(query);
            //附框厂
            query = new MemberQuery();
            query.setMemberType(MemberTypeEnum.FRAMEFACTORY.getId());
            query.setCheckState(MemberCheckStateEnum.CERTIFIED.getId());
            query.setFilterStr(1);
            List<MemberEntity> totalfcList = memberService.queryMembers(query);
            List<MemberEntity> xcList = new ArrayList<MemberEntity>();
            List<MemberEntity> fcList = new ArrayList<MemberEntity>();
            
            for (int i = 0; i < 16; i++)
            {
                Random rd1 = new Random();
                int k1 = rd1.nextInt(totalMCList.size());
                MemberEntity temMc = totalMCList.get(k1);
                mcList.add(temMc);
                totalMCList.remove(temMc);
                
                Random rd2 = new Random();
                int k2 = rd2.nextInt(totalBLList.size());
                MemberEntity temBl = totalBLList.get(k2);
                blList.add(temBl);
                totalBLList.remove(temBl);
                
                Random rd3 = new Random();
                int k3 = rd3.nextInt(totalMCList.size());
                MemberEntity temXc = totalXCList.get(k3);
                xcList.add(temXc);
                totalXCList.remove(temXc);
                
                Random rd4 = new Random();
                int k4 = rd4.nextInt(totalKFSList.size());
                DevelopersEntity temKfs = totalKFSList.get(k4);
                kfsList.add(temKfs);
                totalKFSList.remove(temKfs);
                
                Random rd5 = new Random();
                int k5 = rd5.nextInt(totalfcList.size());
                MemberEntity fc = totalfcList.get(k5);
                fcList.add(fc);
                totalfcList.remove(fc);
            }
            
            TenderMessageQuery messQuery = new TenderMessageQuery();
            messQuery.setPagable(true);
            messQuery.setMemberNo(getLoginInfo(session).getMember()
                    .getMemberNo());
            List<TenderMessageModel> list = tenderMessageService.queryMessageList(messQuery);
            
            map.put("kfsList", kfsList);
            map.put("mcList", mcList);
            map.put("blList", blList);
            map.put("xcList", xcList);
            map.put("fcList", fcList);
            map.put("messageList", list);
            map.put("query", messQuery);
            
            return LOGINPAGER;
        }
        
        catch (Exception e)
        {
            throw new WebException("系统错误", e);
        }
    }
    
    @RequestMapping("to_login")
    public String ToLogin(String memberName, String accountName,
            String message, String pmessage, String amessage,
            HttpServletRequest request, ModelMap map) throws WebException
    {
        try
        {
            map.put("accountName", accountName);
            map.put("memberName", memberName);
            map.put("message", message);
            map.put("pmessage", pmessage);
            map.put("amessage", amessage);
            //X509Certificate[] clientCertChain = (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");
            String certString = request.getHeader("client-cert");
            if (StringUtils.isEmpty(certString))
            {
                return LOGINPAGER;
            }
            certString = certString.replaceAll("\t", "\n");
            X509Certificate clientCertChain = (X509Certificate) new PEMReader(
                    new StringReader(certString), null, "SUN").readObject();
            if (clientCertChain == null)
            {
                return LOGINPAGER;
            }
            else
            {
                Principal dn = clientCertChain.getSubjectDN();
                X500Name x509Principal = (X500Name) dn;
                String uid = x509Principal.getGivenName();
                if (StringUtils.isNotEmpty(uid))
                {
                    String[] uids = uid.split(",");
                    map.put("accountName", uids[1]);
                    map.put("memberName", uids[0]);
                }
                
            }
            
            return LOGINPAGER;
        }
        catch (Exception e)
        {
            throw new WebException("系统错误", e);
        }
    }
    
    @RequestMapping("logout")
    public String logout(HttpServletRequest request)
    {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return "redirect:" + getBasePath(request) + "to_login.htm";
        
    }
    
    @RequestMapping("error")
    public String ToError()
    {
        return "site/500";
    }
    
    @RequestMapping("404")
    public String To404()
    {
        return "site/404";
    }
    
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(String memberName, String accountName, String password,
            HttpSession session, HttpServletRequest request, ModelMap map)
    {
        map.put("accountName", accountName);
        map.put("memberName", memberName);
        SupervisorSiteToken token = null;
        SupervisorPrincipal userBean = null;
        AccountEntity account = null;
        if (StringUtils.isNotEmpty(memberName)
                && StringUtils.isNotEmpty(accountName))
        {
            MemberEntity member = memberService.getMemberByName(memberName);
            if (member == null)
            {
                map.put("message", "会员不存在");
                return LOGIN;
            }
            if (!member.getName().equals(memberName))
            {
                map.put("message", "会员名错误");
                return LOGIN;
            }
            if (MemberCheckStateEnum.UNAUDITED.equals(member.getCheckState()))
            {
                map.put("message", "会员未审核");
                return LOGIN;
            }
            if (MemberStatesEnum.STOP.equals(member.getState()))
            {
                map.put("message", "会员已冻结");
                return LOGIN;
            }
            
            account = accountService.getAccountByName(accountName,
                    member.getMemberNo());
            if (account == null)
            {
                map.put("amessage", "会员子账户不存在");
                return LOGIN;
            }
            if (AccountStatesEnum.STOP.equals(account.getState()))
            {
                map.put("amessage", "会员子账户已冻结");
                return LOGIN;
            }
            
            userBean = new SupervisorPrincipal();
            userBean.setAccount(account);
            userBean.setMember(member);
            token = new SupervisorSiteToken(userBean, password);
        }
        else if (StringUtils.isNotEmpty(memberName)
                && StringUtils.isEmpty(accountName))
        {
            MemberEntity member = memberService.getMemberByName(memberName);
            if (member == null)
            {
                map.put("message", "会员不存在");
                return LOGIN;
            }
            if (MemberCheckStateEnum.UNAUDITED.equals(member.getCheckState()))
            {
                map.put("message", "会员未审核");
                return LOGIN;
            }
            if (MemberStatesEnum.STOP.equals(member.getState()))
            {
                map.put("message", "会员已冻结");
                return LOGIN;
            }
            userBean = new SupervisorPrincipal();
            userBean.setMember(member);
            token = new SupervisorSiteToken(userBean, password);
        }
        else
        {
            map.put("message", "公司名称和密码不能为空");
            //map.put("pmessage", "密码不能为空");
            return LOGIN;
        }
        Subject currentUser = SecurityUtils.getSubject();
        try
        {
            currentUser.login(token);
            PrincipalCollection principals = currentUser.getPrincipals();
            if (userBean.getAccount() != null)
            {
                SupervisorShiroRedisCache.addToMap(userBean.getAccount()
                        .getId(), principals);
            }
            else
            {
                SupervisorShiroRedisCache.addToMap(userBean.getMember()
                        .getMemberNo(), principals);
            }
        }
        catch (AuthenticationException e)
        {
            SxjLogger.error("登陆失败", e, this.getClass());
            map.put("pmessage", "密码错误");
            return LOGIN;
            
        }
        if (currentUser.isAuthenticated())
        {
            session.setAttribute("userinfo", userBean);
            if (account != null)
            {
                accountService.edit_Login(account.getId());
            }
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
    public String ToMenu(HttpServletRequest request, ModelMap map)
    {
        HttpSession session = request.getSession(false);
        if (session.getAttribute("userinfo") == null)
        {
            return LOGIN;
        }
        SupervisorPrincipal userBean = (SupervisorPrincipal) session.getAttribute("userinfo");
        if (userBean.getMember() != null && userBean.getAccount() == null)
        {
            MemberTypeEnum type = userBean.getMember().getType();
            int flag = 0;
            if (MemberTypeEnum.DAWP.equals(type))
            {
                flag = 1;
            }
            else
            {
                flag = 2;
            }
            List<MemberFunctionModel> list = functionService.queryFunctions(flag);
            map.put("list", list);
        }
        else if (userBean.getMember() != null && userBean.getAccount() != null)
        {
            List<MemberFunctionModel> list = roleService.getRoleFunctions(userBean.getAccount()
                    .getId());
            map.put("list", list);
        }
        if (userBean.getMember() != null)
        {
            Long transMessageCount = CometServiceImpl.getCount(MessageChannel.MEMBER_TRANS_MESSAGE_COUNT
                    + userBean.getMember().getMemberNo());
            Long sysMessageCount = CometServiceImpl.getCount(MessageChannel.MEMBER_SYSTEM_MESSAGE_COUNT
                    + userBean.getMember().getMemberNo());
            Long tenderMessageCount = CometServiceImpl.getCount(MessageChannel.MEMBER_TENDER_MESSAGE_COUNT
                    + userBean.getMember().getMemberNo());
            Long contractMessageCount = CometServiceImpl.getCount(MessageChannel.MEMBER_CONTRACT_MESSAGE_COUNT
                    + userBean.getMember().getMemberNo());
            Long payMessageCount = CometServiceImpl.getCount(MessageChannel.MEMBER_PAY_MESSAGE_COUNT
                    + userBean.getMember().getMemberNo());
            if (tenderMessageCount <= 0)
            {
                String key = MessageChannel.MEMBER_TENDER_MESSAGE_COUNT
                        + userBean.getMember().getMemberNo();
                List<String> userKeys = CometServiceImpl.get(MessageChannel.MEMBER_READTENDER_MESSAGE_KEYS);
                userKeys.add(key);
                Long totalCount = CometServiceImpl.getCount(MessageChannel.MEMBER_TENDER_MESSAGE_COUNT);
                if (userKeys != null && userKeys.size() > 0)
                {
                    if (!userKeys.contains(key))
                    {
                        CometServiceImpl.setCount(key, totalCount);
                        tenderMessageCount = totalCount;
                    }
                }
                else
                {
                    CometServiceImpl.setCount(key, totalCount);
                    tenderMessageCount = totalCount;
                }
            }
            map.put("transMessageCount", transMessageCount);
            map.put("sysMessageCount", sysMessageCount);
            map.put("tenderMessageCount", tenderMessageCount);
            map.put("contractMessageCount", contractMessageCount);
            map.put("payMessageCount", payMessageCount);
        }
        return "site/menu";
    }
    
    /**
     * 上传文件
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("upload")
    public void uploadFile(HttpServletRequest request,
            HttpServletResponse response) throws IOException
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
                String filePath = storageClientService.uploadFile(null,
                        new ByteArrayInputStream(myfile.getBytes()),
                        myfile.getBytes().length,
                        extName.toUpperCase());
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
        PrintWriter out = response.getWriter();
        out.print(res);
        out.flush();
        out.close();
    }
    
    /**
     * 上传市场信息
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("uploadWord")
    public void uploadWord(HttpServletRequest request,
            HttpServletResponse response) throws Exception
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
                //原数据路径
                String filePathBack = storageClientService.uploadFile(null,
                        myfile.getInputStream(),
                        myfile.getBytes().length,
                        extName);
                //转HTML
                byte[] html = convert2Html(myfile.getInputStream()).getBytes();
                String filePath = storageClientService.uploadFile(null,
                        new ByteArrayInputStream(html),
                        html.length,
                        "JPG");
                SxjLogger.info("siteUploadFilePath=" + filePath,
                        this.getClass());
                fileIds.add(filePath);
                fileIds.add(filePathBack);
                // 上传元数据
                NameValuePair[] metaList = new NameValuePair[1];
                metaList[0] = new NameValuePair("originalName",
                        URLEncoder.encode(originalName, "UTF-8"));
                storageClientService.overwriteMetadata(filePath, metaList);
                storageClientService.overwriteMetadata(filePathBack, metaList);
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
    
    /**
     * 删除文件
     * @param fileId
     */
    @RequestMapping("deleteFile")
    public @ResponseBody Map<String, String> deleteFile(String fileId)
            throws IOException
    {
        Map<String, String> map = new HashMap<String, String>();
        try
        {
            storageClientService.deleteFile(fileId);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            SxjLogger.debug("删除失败!", e.getClass());
        }
        return map;
    }
    
    /**
     * 下载文件
     * @param request
     * @param response
     * @param filePath
     * @throws IOException
     */
    @RequestMapping("downloadFile")
    public void downloadFile(HttpServletRequest request,
            HttpServletResponse response, String filePath) throws IOException
    {
        try
        {
            ServletOutputStream output = response.getOutputStream();
            String fileName = "扫描件" + stringDate();
            String group = filePath.substring(0, filePath.indexOf("/"));
            response.addHeader("Content-Disposition", "attachment;filename="
                    + fileName + ".pdf");
            response.setContentType("application/pdf");
            String path = filePath.substring(filePath.indexOf("/") + 1,
                    filePath.length());
            storageClientService.downloadFile(group, path, output);
            output.flush();
            output.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            SxjLogger.debug("下载文件出错", e.getClass());
        }
    }
    
    /**
     * 下载附件
     * @param request
     * @param response
     * @param filePath
     * @throws IOException
     */
    @RequestMapping("downloadFileFJ")
    public void downloadFileFJ(HttpServletRequest request,
            HttpServletResponse response, String filePath) throws IOException
    {
        try
        {
            ServletOutputStream output = response.getOutputStream();
            String group = filePath.substring(0, filePath.indexOf("/"));
            String path = filePath.substring(filePath.indexOf("/") + 1,
                    filePath.length());
            NameValuePair[] metaList = storageClientService.getMetadata(group,
                    path);
            String fjname = metaList[0].getValue();
            response.addHeader("Content-Disposition", "attachment;filename="
                    + new String(URLDecoder.decode(fjname, "UTF-8").getBytes(),
                            "ISO8859-1"));
            response.setContentType("application/"
                    + FileUtil.getFileExtName(fjname));
            storageClientService.downloadFile(group, path, output);
            output.flush();
            output.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            SxjLogger.debug("下载文件出错", e.getClass());
        }
    }
    
    private String stringDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);//获取年份         
        int month = cal.get(Calendar.MONTH);//获取月份         
        int day = cal.get(Calendar.DATE);//获取日         
        int hour = cal.get(Calendar.HOUR);//小时          
        int minute = cal.get(Calendar.MINUTE);//分                     
        int second = cal.get(Calendar.SECOND);//秒     
        String date = year + "" + month + "" + day + "" + hour + "" + minute
                + "" + second;
        
        return date;
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
     * 获取招标合同号
     * 
     * @param request
     * @param response
     * @param keyword
     * @return
     * @throws IOException
     */
    @RequestMapping("refContractNo")
    public @ResponseBody Map<String, String> getRefContractNo(
            HttpSession session, HttpServletRequest request,
            HttpServletResponse response, String keyword) throws IOException
    {
        ContractQuery query = new ContractQuery();
        if (keyword != "" && keyword != null)
        {
            query.setEngAddress(keyword);
        }
        SupervisorPrincipal userBean = (SupervisorPrincipal) session.getAttribute("userinfo");
        query.setMemberIdB(userBean.getMember().getMemberNo());
        List<ContractModel> list = contractService.queryContracts(query);
        List strlist = new ArrayList();
        String sb = "";
        for (ContractModel cm : list)
        {
            if (cm != null)
            {
                sb = "{\"title\":\"" + cm.getContract().getEngAddress()
                        + "\",\"result\":\"" + cm.getContract().getContractNo()
                        + "\"}";
                strlist.add(sb);
            }
            
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
    
    /**
     * 会员联想
     * 
     * @param request
     * @param response
     * @param keyword
     * @return
     * @throws IOException
     */
    @RequestMapping("autoCompleMember")
    public @ResponseBody Map<String, String> autoCompleMember(
            HttpServletRequest request, HttpServletResponse response,
            String keyword, Integer memberType) throws IOException
    {
        MemberQuery mq = new MemberQuery();
        if (keyword != "" && keyword != null)
        {
            mq.setMemberName(keyword);
        }
        if (null != memberType)
        {
            mq.setMemberType(memberType);
        }
        //mq.setMemberTypeB(0);
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
     * 会员联想(可以获取到联系人和联系电话)
     * 
     * @param request
     * @param response
     * @param keyword
     * @return
     * @throws IOException
     */
    @RequestMapping("autoCompleMemberInfo")
    public @ResponseBody Map<String, String> autoCompleMemberInfo(
            HttpServletRequest request, HttpServletResponse response,
            String keyword, Integer memberType) throws IOException
    {
        MemberQuery mq = new MemberQuery();
        if (keyword != "" && keyword != null)
        {
            mq.setMemberName(keyword);
        }
        if (null != memberType)
        {
            mq.setMemberType(memberType);
        }
        //mq.setMemberTypeB(0);
        List<MemberEntity> list = memberService.queryMembers(mq);
        List strlist = new ArrayList();
        String sb = "";
        for (MemberEntity memberEntity : list)
        {
            String contacts = memberEntity.getContacts();
            String telNum = memberEntity.getTelNum();
            if (StringUtils.isEmpty(contacts))
            {
                contacts = "";
            }
            if (StringUtils.isEmpty(telNum))
            {
                telNum = "";
            }
            sb = "{\"title\":\"" + memberEntity.getName() + "\",\"result\":\""
                    + memberEntity.getMemberNo() + "\",\"contacts\":\""
                    + contacts + "\",\"telNum\":\"" + telNum + "\"}";
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
    
    @RequestMapping("filesort")
    public @ResponseBody List<String> fileSort(String fileId)
            throws IOException
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
            // Map<String, Object> map = new HashMap<String, Object>();
            // map.put("", sortFile);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
        }
        return sortFile;
        
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
    public void enter(String functionId, String url, HttpSession session,
            HttpServletRequest request)
    {
        Date enterTime = (Date) session.getAttribute("enterTime");
        Date nowTime = new Date();
        String currentUrl = (String) session.getAttribute("currentUrl");
        String currentFunction = (String) session.getAttribute("currentFunction");
        String nextUrl = (String) session.getAttribute("nextUrl");
        String nextFunction = (String) session.getAttribute("nextFunction");
        if (currentUrl == null)
        {
            session.setAttribute("currentUrl", request.getHeader("Referer"));
            
        }
        session.setAttribute("previousUrl", currentUrl);
        session.setAttribute("currentUrl", nextUrl);
        session.setAttribute("nextUrl", url);
        
        session.setAttribute("previousFunction", currentFunction);
        session.setAttribute("currentFunction", nextFunction);
        session.setAttribute("nextFunction", functionId);
        
        MemberEntity principal = (MemberEntity) SecurityUtils.getSubject()
                .getPrincipal();
        if (principal != null)
        {
            MemberLogEntity log = new MemberLogEntity();
            log.setMemberNo(principal.getMemberNo());
            log.setMemberName(principal.getName());
            log.setNowPage(nextUrl);
            log.setNextpage(url);
            log.setPrePage(currentUrl);
            
            MemberFunctionEntity function = functionService.getFunction(nextFunction);
            if (function != null)
            {
                log.setNowName(function.getTitle());
            }
            
            function = functionService.getFunction(functionId);
            if (function != null)
            {
                log.setNextName(function.getTitle());
            }
            
            function = functionService.getFunction(currentFunction);
            if (function != null)
            {
                log.setPreName(function.getTitle());
            }
            
            log.setCallTime(enterTime);
            log.setIp(getIpAddr(request));
            if (enterTime != null)
            {
                long waitTime = (nowTime.getTime() - enterTime.getTime()) / 1000;
                log.setWaitTime(waitTime + "");
            }
            logService.addLog(log);
        }
        session.setAttribute("enterTime", nowTime);
        
    }
    
    @RequestMapping("message")
    public @ResponseBody Map<String, Object> message(
            HttpServletRequest request, HttpServletResponse response,
            String channelName) throws IOException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        if (channelName.contains(MessageChannel.RECORD_MESSAGE)
                || channelName.contains(RfidChannel.RFID_APPLY_MESSAGE)
                || channelName.contains(MessageChannel.WEBSITE_PAY_MESSAGE)
                || channelName.contains(MessageChannel.WEBSITE_FINANCE_MESSAGE)
                || channelName.contains(MessageChannel.MEMBER_MESSAGE)
                || channelName.contains(MessageChannel.MEMBER_PERFECT_MESSAGE)
                || channelName.contains(MessageChannel.MEMBER_SYSTEM_MESSAGE_COUNT)
                || channelName.contains(MessageChannel.MEMBER_TRANS_MESSAGE_COUNT)
                || channelName.contains(MessageChannel.MEMBER_TENDER_MESSAGE_COUNT)
                || channelName.contains(MessageChannel.MEMBER_CONTRACT_MESSAGE_COUNT)
                || channelName.contains(MessageChannel.MEMBER_PAY_MESSAGE_COUNT))
        {
            Long count = CometServiceImpl.getCount(channelName);
            SxjLogger.debug("Sending Message to Comet Client:" + count,
                    getClass());
            //if (count > 0)
            //  {
            map.put("count", count);
            map.put("channelName", channelName);
            return map;
            // }
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
    
    @RequestMapping("menuMessageCount")
    public @ResponseBody Map<String, Long> menuMessageCount(HttpSession session)
            throws WebException
    {
        Map<String, Long> map = new HashMap<>();
        map.put("isNotLogin", 0l);
        try
        {
            SupervisorPrincipal login = getLoginInfo(session);
            if (login == null)
            {
                map.put("isNotLogin", 1l);
                return map;
            }
            MemberEntity member = login.getMember();
            Long transMessageCount = CometServiceImpl.getCount(MessageChannel.MEMBER_TRANS_MESSAGE_COUNT
                    + member.getMemberNo());
            Long sysMessageCount = CometServiceImpl.getCount(MessageChannel.MEMBER_SYSTEM_MESSAGE_COUNT
                    + member.getMemberNo());
            Long tenderMessageCount = CometServiceImpl.getCount(MessageChannel.MEMBER_TENDER_MESSAGE_COUNT
                    + member.getMemberNo());
            if (tenderMessageCount <= 0)
            {
                String key = MessageChannel.MEMBER_TENDER_MESSAGE_COUNT
                        + login.getMember().getMemberNo();
                List<String> userKeys = CometServiceImpl.get(MessageChannel.MEMBER_READTENDER_MESSAGE_KEYS);
                Long totalCount = CometServiceImpl.getCount(MessageChannel.MEMBER_TENDER_MESSAGE_COUNT);
                if (userKeys != null && userKeys.size() > 0)
                {
                    if (!userKeys.contains(key))
                    {
                        CometServiceImpl.setCount(key, totalCount);
                        tenderMessageCount = totalCount;
                        userKeys.add(key);
                        CometServiceImpl.sendMessage(MessageChannel.MEMBER_READTENDER_MESSAGE_KEYS, userKeys);
                    }
                }
                else
                {
                    CometServiceImpl.setCount(key, totalCount);
                    tenderMessageCount = totalCount;
                }
            }
            Long contractMessageCount = CometServiceImpl.getCount(MessageChannel.MEMBER_CONTRACT_MESSAGE_COUNT
                    + member.getMemberNo());
            Long payMessageCount = CometServiceImpl.getCount(MessageChannel.MEMBER_PAY_MESSAGE_COUNT
                    + member.getMemberNo());
            map.put("transMessageCount", transMessageCount);
            map.put("sysMessageCount", sysMessageCount);
            map.put("tenderMessageCount", tenderMessageCount);
            map.put("contractMessageCount", contractMessageCount);
            map.put("payMessageCount", payMessageCount);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
        }
        return map;
        
    }
    
    /**
     * 获取招标门窗型号
     * 
     * @param request
     * @param response
     * @param keyword
     * @return
     * @throws IOException
     */
    @RequestMapping("grtWindowType")
    public @ResponseBody Map<String, String> getRefContractWindwoType(
            HttpSession session, HttpServletRequest request,
            HttpServletResponse response, String keyword, String refContractNo)
            throws IOException
    {
        if (StringUtils.isEmpty(refContractNo))
        {
            return null;
        }
        List<ContractItemEntity> list = contractService.getRefContractItem(refContractNo,
                keyword);
        List strlist = new ArrayList();
        String sb = "";
        for (ContractItemEntity ci : list)
        {
            if (ci != null)
            {
                sb = "{\"title\":\"" + ci.getWindowType() + "\",\"result\":\""
                        + ci.getId() + "\"}";
                strlist.add(sb);
            }
            
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
     * 获取图片验证码
     * 
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("getAuthImg")
    public @ResponseBody Map<String, String> getAuthImg(HttpSession session,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        try
        {
            AuthImg img = new AuthImg();
            ValidateImage image = img.getImage();
            String str = image.getImgStr();
            BufferedImage bImg = image.getImg();
            session.setAttribute("imgStr", str);
            System.out.println("第一：" + str);
            ImageIO.write(bImg, "JPEG", response.getOutputStream());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        return null;
    }
    
    /**
     * 获取图片验证码字符
     * 
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("getImgStr")
    public @ResponseBody Map<String, String> getImgStr(HttpSession session,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
    	Map<String, String> map = new HashMap<String, String>();
        String res = session.getAttribute("imgStr").toString();
//        PrintWriter out = response.getWriter();
        System.out.println("第二：" + res);
//        out.print(res);
//        out.flush();
//        out.close();
        map.put("imgStr", res);
        return map;
    }
    
    /**
     * 证书联想
     * 
     * @param request
     * @param response
     * @param keyword
     * @return
     * @throws IOException
     */
    @RequestMapping("autoCertificate")
    public @ResponseBody Map<String, String> autoCertificate(
            HttpServletRequest request, HttpServletResponse response,
            String keyword) throws IOException
    {
        List<CertificateEntity> list = memberImageService.getCertificate(keyword);
        List strlist = new ArrayList();
        String sb = "";
        for (CertificateEntity certificate : list)
        {
            sb = "{\"title\":\"" + certificate.getCertificateName()
                    + "\",\"result\":\"" + certificate.getCertificateLevel()
                    + "\"}";
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
