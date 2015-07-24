package com.sxj.supervisor.website.controller.member;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.cache.manager.CacheLevel;
import com.sxj.cache.manager.HierarchicalCacheManager;
import com.sxj.redis.core.RAtomicLong;
import com.sxj.redis.core.collections.RedisCollections;
import com.sxj.redis.core.concurrent.RedisConcurrent;
import com.sxj.redis.core.pubsub.RedisTopics;
import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.entity.system.AreaEntity;
import com.sxj.supervisor.enu.member.MemberCheckStateEnum;
import com.sxj.supervisor.enu.member.MemberStatesEnum;
import com.sxj.supervisor.enu.member.MemberTypeEnum;
import com.sxj.supervisor.model.comet.MessageChannel;
import com.sxj.supervisor.model.login.SupervisorPrincipal;
import com.sxj.supervisor.model.member.MemberQuery;
import com.sxj.supervisor.service.member.IMemberService;
import com.sxj.supervisor.service.system.IAreaService;
import com.sxj.supervisor.website.controller.BaseController;
import com.sxj.util.Constraints;
import com.sxj.util.comet.CometServiceImpl;
import com.sxj.util.common.DateTimeUtils;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/member")
public class MemberController extends BaseController
{
    
    @Autowired
    private IMemberService memberService;
    
    @Autowired
    private IAreaService areaService;
    
    @Autowired
    private RedisCollections collections;
    
    @Autowired
    private RedisConcurrent redisConcurrent;
    
    @Autowired
    private RedisTopics topics;
    
    @Autowired
    private CachingSessionDAO sessionDAO;
    
    /**
     * 根据会员号获取会员信息
     * 
     * @param map
     * @return
     * @throws WebException
     */
    @RequestMapping("/memberInfo")
    public String memberList(ModelMap map, HttpSession session,
            HttpServletRequest request) throws WebException
    {
        try
        {
            SupervisorPrincipal info = getLoginInfo(session);
            if (info != null)
            {
                MemberEntity member = memberService.getMember(info.getMember()
                        .getId());
                if (member.getAccountNum() == null)
                {
                    member.setAccountNum(0);
                }
                List<AreaEntity> cityList = areaService.getChildrenAreas("32");
                map.put("cityList", cityList);
                map.put("member", member);
                if (member.getFlag())
                {
                    Long systemMessageCount = CometServiceImpl.getCount(MessageChannel.MEMBER_SYSTEM_MESSAGE_COUNT
                            + member.getMemberNo());
                    Long transMessageCount = CometServiceImpl.getCount(MessageChannel.MEMBER_TRANS_MESSAGE_COUNT
                            + member.getMemberNo());
                    Long tenderMessageCount = CometServiceImpl.getCount(MessageChannel.MEMBER_TENDER_MESSAGE_COUNT
                            + member.getMemberNo());
                    map.put("systemMessageCount", systemMessageCount);
                    map.put("transMessageCount", transMessageCount);
                    map.put("tenderMessageCount", tenderMessageCount);
                    
                    map.put("channelName_sys",
                            MessageChannel.MEMBER_SYSTEM_MESSAGE_COUNT
                                    + member.getMemberNo());
                    map.put("channelName_trans",
                            MessageChannel.MEMBER_TRANS_MESSAGE_COUNT
                                    + member.getMemberNo());
                    map.put("channelName_tender",
                            MessageChannel.MEMBER_TENDER_MESSAGE_COUNT
                                    + member.getMemberNo());
                    return "site/member/member-profile";
                }
                else
                {
                    return "site/member/edit-member";
                }
            }
            return LOGIN;
        }
        catch (Exception e)
        {
            SxjLogger.error("修改会员信息错误 ", e, this.getClass());
            throw new WebException(e.getMessage());
        }
        
    }
    
    /**
     * 修改密码
     * 
     * @param id
     * @param password
     * @return
     */
    @RequestMapping("/edit_pwd")
    public @ResponseBody Map<String, String> edit_pwd(String id, String password)
    {
        Map<String, String> map = new HashMap<String, String>();
        memberService.editPwd(id, password);
        return map;
    }
    
    /**
     * 修改会员信息界面
     * 
     * @return
     */
    @RequestMapping("edit_member")
    public String edit_member(String id, ModelMap map)
    {
        MemberEntity member = memberService.getMember(id);
        List<AreaEntity> cityList = areaService.getChildrenAreas("32");
        map.put("cityList", cityList);
        map.put("member", member);
        return "site/member/edit-member";
    }
    
    @RequestMapping("leave")
    @ResponseBody
    public void leave(HttpSession session)
    {
        session.setAttribute("leaveTime", new Date());
        
    }
    
    @RequestMapping("save_member")
    public @ResponseBody Map<String, Object> save_member(MemberEntity member,
            HttpSession session) throws WebException
    {
        try
        {
            Map<String, Object> map = new HashMap<String, Object>();
            member.setFlag(true);
            member.setUpDate(new Date());
            member = memberService.modifyMember(member);
            SupervisorPrincipal login = getLoginInfo(session);
            login.setMember(member);
            session.setAttribute("userinfo", login);
            
            topics.getTopic(Constraints.WEBSITE_CHANNEL_NAME)
                    .publish(Constraints.UN_EDIT_CHECK_STATE_SET + ","
                            + member.getId());
            map.put("isOK", "ok");
            return map;
        }
        catch (Exception e)
        {
            SxjLogger.error("修改会员信息错误", e, this.getClass());
            throw new WebException(e.getMessage());
        }
        
    }
    
    /**
     * 注册账户
     */
    @RequestMapping("/regist")
    public String regist(ModelMap map)
    {
        MemberTypeEnum[] type = MemberTypeEnum.values();
        List<AreaEntity> list = areaService.getChildrenAreas("32");
        map.put("type", type);
        map.put("list", list);
        return "site/register";
    }
    
    /**
     * 注册协议页面
     */
    @RequestMapping("/registInfo")
    public String registInfo(ModelMap map)
    {
        return "site/protocal";
    }
    
    /**
     * 添加会员
     */
    @RequestMapping(value = "regist_save", method = RequestMethod.POST)
    public String regist_save(MemberEntity member, ModelMap map, String ms,
            HttpSession session) throws WebException
    {
        try
        {
            String message = (String) HierarchicalCacheManager.get(CacheLevel.REDIS,
                    "checkMs",
                    member.getPhoneNo() + "_checkMs");
            if (StringUtils.isEmpty(message))
            {
                MemberTypeEnum[] type = MemberTypeEnum.values();
                List<AreaEntity> list = areaService.getChildrenAreas("32");
                map.put("type", type);
                map.put("list", list);
                map.put("member", member);
                return "site/register";
            }
            if (message.equals(ms))
            {
                member.setState(MemberStatesEnum.NORMAL);
                member.setCheckState(MemberCheckStateEnum.UNAUDITED);
                member.setRegDate(new Date());
                member.setFlag(false);
                memberService.addMember(member);
                HierarchicalCacheManager.evict(CacheLevel.REDIS,
                        "checkMs",
                        member.getPhoneNo() + "_checkMs");
                return "redirect:/member/regist_succ.htm";
            }
            else
            {
                MemberTypeEnum[] type = MemberTypeEnum.values();
                List<AreaEntity> list = areaService.getChildrenAreas("32");
                map.put("type", type);
                map.put("list", list);
                map.put("member", member);
                return "site/register";
            }
        }
        catch (Exception e)
        {
            SxjLogger.error("添加会员信息错误", e, this.getClass());
            throw new WebException(e.getMessage());
        }
    }
    
    @RequestMapping("regist_succ")
    public String regist_succ()
    {
        return "site/reg-suc";
    }
    
    /**
     * 忘记密码
     */
    @RequestMapping("find_pwd")
    public String find_pwd()
    {
        return "site/find-password";
    }
    
    /**
     * 验证会员是否注册过
     */
    @RequestMapping("check_member")
    public @ResponseBody Map<String, String> check_member(String name)
    {
        Map<String, String> map = new HashMap<String, String>();
        MemberEntity member = memberService.getMemberByName(name);
        if (member == null)
        {
            return null;
        }
        else
        {
            map.put("text", "该会员已存在！");
            return map;
        }
    }
    
    /**
     * 验证手机号是否已注册
     * 
     */
    @RequestMapping("check_phone")
    public @ResponseBody Map<String, String> check_phone(String phone)
            throws WebException
    {
        Map<String, String> map = new HashMap<String, String>();
        MemberQuery query = new MemberQuery();
        query.setContactsPhone(phone);
        List<MemberEntity> list = memberService.queryMembers(query);
        if (list.size() > 0)
        {
            map.put("flag", "true");
            map.put("id", list.get(0).getId());
        }
        else
        {
            map.put("flag", "false");
        }
        return map;
    }
    
    /**
     * 发送验证码
     */
    @RequestMapping("send_ms")
    public @ResponseBody Map<String, String> send_ms(HttpSession session,
            String phoneNo) throws WebException
    {
        
        Map<String, String> map = new HashMap<String, String>();
        try
        {
            if (StringUtils.isEmpty(phoneNo))
            {
                map.put("error", "手机号不能为空");
                return map;
            }
            phoneNo = phoneNo.trim();
            RAtomicLong num = redisConcurrent.getAtomicLong("num_" + phoneNo,
                    59);// 记录次数59秒只能发送一次
            if (num.incrementAndGet() == 1)
            {
                RAtomicLong sendMax = redisConcurrent.getAtomicLong("sendMax_"
                        + phoneNo, DateTimeUtils.getNextZeroTime());
                if (sendMax.incrementAndGet() <= 5)
                {
                    String message = "";
                    message = memberService.createvalidata(phoneNo, message);
                    HierarchicalCacheManager.set(CacheLevel.REDIS,
                            "checkMs",
                            phoneNo + "_checkMs",
                            message,
                            600);
                }
                else
                {
                    map.put("error", "每个号码每天限制发送5次");
                }
            }
            else
            {
                map.put("error", "每分钟只能发送一次");
            }
        }
        catch (Exception e)
        {
            SxjLogger.error("信息发送错误", e, this.getClass());
            throw new WebException("发送信息错误");
        }
        return map;
    }
    
    /**
     * 短信验证
     */
    @RequestMapping("check_ms")
    public @ResponseBody Map<String, String> check_ms(HttpSession session,
            String phoneNo, String ms) throws WebException
    {
        Map<String, String> map = new HashMap<String, String>();
        String message = (String) HierarchicalCacheManager.get(CacheLevel.REDIS,
                "checkMs",
                phoneNo + "_checkMs");
        if (StringUtils.isEmpty(message))
        {
            map.put("flag", "false");
        }
        if (message.equals(ms))
        {
            map.put("flag", "true");
        }
        else
        {
            map.put("flag", "false");
        }
        return map;
    }
    
    @RequestMapping("checkEditState")
    @ResponseBody
    public Boolean checkEditState(HttpSession session)
    {
        if (session.getAttribute("userinfo") == null)
            return false;
        SupervisorPrincipal member = (SupervisorPrincipal) session.getAttribute("userinfo");
        // RSet<Object> set =
        // collections.getSet(Constraints.EDIT_CHECK_STATE_SET);
        // if (set.contains(member.getId())) {
        MemberEntity newMember = memberService.getMember(member.getMember()
                .getId());
        if (newMember.getCheckState().equals(MemberCheckStateEnum.CERTIFIED))
        {
            member.setMember(newMember);
            session.setAttribute("userinfo", member);
            return true;
        }
        else
        {
            return false;
        }
    }
    
    @RequestMapping("getMessageCount")
    public @ResponseBody Map<String, Long> getMessageCount(HttpSession session)
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
            Long systemMessageCount = CometServiceImpl.getCount(MessageChannel.MEMBER_SYSTEM_MESSAGE_COUNT
                    + member.getMemberNo());
            Long transMessageCount = CometServiceImpl.getCount(MessageChannel.MEMBER_TRANS_MESSAGE_COUNT
                    + member.getMemberNo());
            Long tenderMessageCount = CometServiceImpl.getCount(MessageChannel.MEMBER_TENDER_MESSAGE_COUNT
                    + member.getMemberNo());
            map.put("systemMessageCount", systemMessageCount);
            map.put("transMessageCount", transMessageCount);
            map.put("tenderMessageCount", tenderMessageCount);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
        }
        return map;
        
    }
    
    /**
     * 未认证提醒
     * @return
     * @throws WebException
     */
    @RequestMapping("unCertificates")
    public @ResponseBody String unCertificates(String id) throws WebException
    {
        try
        {
            CometServiceImpl.add(MessageChannel.MEMBER_PERFECT_MESSAGE_SET, id);
            CometServiceImpl.setCount(MessageChannel.MEMBER_PERFECT_MESSAGE,
                    (long) CometServiceImpl.get(MessageChannel.MEMBER_PERFECT_MESSAGE_SET)
                            .size());
            topics.getTopic(MessageChannel.TOPIC_NAME)
                    .publish(MessageChannel.MEMBER_PERFECT_MESSAGE);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
        }
        return "";
    }
    
    /**
     * 江苏省信息
     */
    @RequestMapping("info")
    public String info(Integer infoFlag, ModelMap map,MemberQuery query)
    {
        query.setPagable(true);
        query.setShowCount(20);
        //0:门窗 1:型材 2:配件
        if (infoFlag == 0)
        {
            query.setMemberType(MemberTypeEnum.DAWP.getId());
        }
        else if (infoFlag == 1)
        {
            query.setMemberType(MemberTypeEnum.GENRESFACTORY.getId());
        }
        else if (infoFlag == 2)
        {
            query.setMemberType(MemberTypeEnum.PRODUCTS.getId());
        }else if (infoFlag == 3)
        {
            query.setMemberType(MemberTypeEnum.GLASSFACTORY.getId());
        }
        List<MemberEntity> list = memberService.queryMembers(query);
        map.put("list", list);
        map.put("infoFlag", infoFlag);
        map.put("query", query);
        return "site/developers/memberList";
    }
}
