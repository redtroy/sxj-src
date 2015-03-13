package com.sxj.supervisor.website.controller.message;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.entity.message.MessageConfigEntity;
import com.sxj.supervisor.entity.message.TransMessageEntity;
import com.sxj.supervisor.enu.message.MessageTypeEnum;
import com.sxj.supervisor.model.login.SupervisorPrincipal;
import com.sxj.supervisor.model.message.TransMessageQuery;
import com.sxj.supervisor.service.meaagae.IMessageConfigService;
import com.sxj.supervisor.service.meaagae.ITransMessageService;
import com.sxj.supervisor.website.controller.BaseController;
import com.sxj.util.exception.WebException;

@Controller
@RequestMapping("/transMessage")
public class TransMessageController extends BaseController
{
    @Autowired
    private ITransMessageService service;
    
    @Autowired
    private IMessageConfigService configService;
    
    @RequestMapping("/memberInfo")
    public String query(ModelMap map, HttpSession session,
            HttpServletRequest request) throws WebException
    {
        SupervisorPrincipal user = getLoginInfo(session);
        if (user == null)
        {
            return LOGIN;
        }
        String memberNo = user.getMember().getMemberNo();
        TransMessageQuery query = new TransMessageQuery();
        query.setMemberNo(memberNo);
        List<TransMessageEntity> list = service.queryMessageList(query);
        map.put("messageList", list);
        List<MessageConfigEntity> config = configService.queryConfigList(memberNo);
        map.put("types", MessageTypeEnum.values());
        map.put("config", config);
        return "site/message/transmessage";
        
    }
}
