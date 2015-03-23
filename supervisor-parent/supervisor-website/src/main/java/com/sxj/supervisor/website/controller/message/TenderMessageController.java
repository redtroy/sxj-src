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
import com.sxj.supervisor.entity.message.TenderMessageEntity;
import com.sxj.supervisor.enu.message.MessageStateEnum;
import com.sxj.supervisor.enu.message.MessageTypeEnum;
import com.sxj.supervisor.model.comet.MessageChannel;
import com.sxj.supervisor.model.login.SupervisorPrincipal;
import com.sxj.supervisor.model.message.TenderMessageModel;
import com.sxj.supervisor.model.message.TenderMessageQuery;
import com.sxj.supervisor.service.message.IMessageConfigService;
import com.sxj.supervisor.service.message.ITenderMessageService;
import com.sxj.supervisor.website.controller.BaseController;
import com.sxj.util.comet.CometServiceImpl;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/systender")
public class TenderMessageController extends BaseController
{
    @Autowired
    private ITenderMessageService service;
    
    @Autowired
    private IMessageConfigService configService;
    
    @RequestMapping("/query")
    public String query(TenderMessageQuery query, ModelMap map,
            HttpSession session) throws WebException
    {
        SupervisorPrincipal user = getLoginInfo(session);
        if (user == null)
        {
            return LOGIN;
        }
        String memberNo = user.getMember().getMemberNo();
        List<String> infoIdList = CometServiceImpl.get(MessageChannel.MEMBER_TENDER_MESSAGE_INFO
                + memberNo);
        if (infoIdList != null && infoIdList.size() > 0)
        {
            List<TenderMessageEntity> messageList = new ArrayList<TenderMessageEntity>();
            for (Iterator<String> iterator = infoIdList.iterator(); iterator.hasNext();)
            {
                String infoId = iterator.next();
                TenderMessageEntity message = new TenderMessageEntity();
                message.setInfoId(infoId);
                message.setMemberNo(memberNo);
                message.setState(MessageStateEnum.UNREAD);
                messageList.add(message);
            }
            service.addMessage(messageList);
            CometServiceImpl.clear(MessageChannel.MEMBER_TENDER_MESSAGE_COUNT
                    + memberNo);
        }
        query.setPagable(true);
        query.setMemberNo(memberNo);
        List<TenderMessageModel> list = service.queryMessageList(query);
        map.put("messageList", list);
        MessageConfigEntity config = null;
        List<MessageConfigEntity> configList = configService.queryConfigList(memberNo);
        if (configList != null && configList.size() > 0)
        {
            for (Iterator<MessageConfigEntity> iterator = configList.iterator(); iterator.hasNext();)
            {
                MessageConfigEntity messageConfigEntity = (MessageConfigEntity) iterator.next();
                if (messageConfigEntity.getMessageType()
                        .equals(MessageTypeEnum.TENDER))
                {
                    config = messageConfigEntity;
                    break;
                }
                
            }
        }
        map.put("messageConfig", config);
        map.put("query", query);
        return "site/message/tendermessage";
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
            config.setMessageType(MessageTypeEnum.TENDER);
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
