package com.sxj.supervisor.website.controller.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.entity.message.MessageConfigEntity;
import com.sxj.supervisor.enu.message.MessageStateEnum;
import com.sxj.supervisor.enu.message.MessageTypeEnum;
import com.sxj.supervisor.model.comet.MessageChannel;
import com.sxj.supervisor.model.login.SupervisorPrincipal;
import com.sxj.supervisor.model.message.SystemMessageModel;
import com.sxj.supervisor.model.message.SystemMessageQuery;
import com.sxj.supervisor.service.message.IMessageConfigService;
import com.sxj.supervisor.service.message.ISystemMessageService;
import com.sxj.supervisor.website.controller.BaseController;
import com.sxj.util.comet.CometServiceImpl;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/sysMessage")
public class SystemMessageController extends BaseController
{
    
    @Autowired
    private IMessageConfigService configService;
    
    @Autowired
    private ISystemMessageService service;
    
    @RequestMapping("/query")
    public String query(ModelMap map, HttpSession session) throws WebException
    {
        SupervisorPrincipal user = getLoginInfo(session);
        if (user == null)
        {
            return LOGIN;
        }
        String memberNo = user.getMember().getMemberNo();
        List<String> infoIdList = CometServiceImpl.get(MessageChannel.MEMBER_SYSTEM_MESSAGE_INFO
                + memberNo);
        if (infoIdList != null && infoIdList.size() > 0)
        {
            service.addSystemMessage(memberNo,
                    user.getMember().getName(),
                    user.getMember().getType(),
                    infoIdList);
            CometServiceImpl.clear(MessageChannel.MEMBER_SYSTEM_MESSAGE_INFO
                    + memberNo);
        }
        SystemMessageQuery query = new SystemMessageQuery();
        query.setPagable(true);
        query.setMemberNo(memberNo);
        List<SystemMessageModel> list = service.querySystemMessageList(query);
        map.put("messageList", list);
        MessageConfigEntity config = null;
        List<MessageConfigEntity> configList = configService.queryConfigList(memberNo);
        if (configList != null && configList.size() > 0)
        {
            for (Iterator<MessageConfigEntity> iterator = configList.iterator(); iterator.hasNext();)
            {
                MessageConfigEntity messageConfigEntity = (MessageConfigEntity) iterator.next();
                if (messageConfigEntity.getMessageType()
                        .equals(MessageTypeEnum.SYSTEM))
                {
                    config = messageConfigEntity;
                    break;
                }
                
            }
        }
        map.put("messageConfig", config);
        map.put("query", query);
        return "site/message/systemmessage";
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
    
    @RequestMapping("/changeConfig")
    public @ResponseBody Map<String, String> changeConfig(String configId,
            Boolean isAccept, HttpSession session) throws WebException
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
            config.setMessageType(MessageTypeEnum.SYSTEM);
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
}
