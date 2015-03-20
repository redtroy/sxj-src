package com.sxj.supervisor.website.controller.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.entity.message.MessageConfigEntity;
import com.sxj.supervisor.entity.message.TransMessageEntity;
import com.sxj.supervisor.enu.message.MessageStateEnum;
import com.sxj.supervisor.enu.message.MessageTypeEnum;
import com.sxj.supervisor.model.login.SupervisorPrincipal;
import com.sxj.supervisor.model.message.TransMessageQuery;
import com.sxj.supervisor.service.message.IMessageConfigService;
import com.sxj.supervisor.service.message.ITransMessageService;
import com.sxj.supervisor.website.controller.BaseController;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/transMessage")
public class TransMessageController extends BaseController
{
    @Autowired
    private ITransMessageService service;
    
    @Autowired
    private IMessageConfigService configService;
    
    @RequestMapping("/query")
    public String query(TransMessageQuery query, ModelMap map,
            HttpSession session, HttpServletRequest request)
            throws WebException
    {
        SupervisorPrincipal user = getLoginInfo(session);
        if (user == null)
        {
            return LOGIN;
        }
        String memberNo = user.getMember().getMemberNo();
        query.setMemberNo(memberNo);
        List<TransMessageEntity> list = service.queryMessageList(query);
        map.put("messageList", list);
        List<MessageConfigEntity> config = configService.queryConfigList(memberNo);
        map.put("types", MessageTypeEnum.values());
        map.put("messageConfig", config);
        map.put("query", query);
        return "site/message/transmessage";
        
    }
    
    @RequestMapping("/changeConfig")
    public @ResponseBody Map<String, String> changeConfig(String configId,
            String configType, Boolean isAccept, HttpSession session)
            throws WebException
    {
        Map<String, String> map = new HashMap<String, String>();
        try
        {
            SupervisorPrincipal user = getLoginInfo(session);
            String memberNo = user.getMember().getMemberNo();
            MessageConfigEntity config = new MessageConfigEntity();
            if (StringUtils.isEmpty(configId))
            {
                configId = StringUtils.getUUID();
            }
            config.setId(configId);
            config.setIsAccetp(isAccept);
            config.setMemberNo(memberNo);
            config.setMessageType(MessageTypeEnum.valueOf(configType));
            config.setPhone(user.getMember().getPhoneNo());
            List<MessageConfigEntity> list = new ArrayList<MessageConfigEntity>();
            list.add(config);
            configService.updateConfig(list);
            map.put("isOK", "ok");
            map.put("configId", configId);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            map.put("error", e.getMessage());
        }
        return map;
    }
    
    @RequestMapping("/modifyState")
    public @ResponseBody Map<String, String> modifyState(String id,
            HttpSession session) throws WebException
    {
        Map<String, String> map = new HashMap<String, String>();
        try
        {
            service.modifyState(id, MessageStateEnum.READ);
            map.put("isOK", "ok");
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            map.put("error", e.getMessage());
        }
        return map;
    }
}
