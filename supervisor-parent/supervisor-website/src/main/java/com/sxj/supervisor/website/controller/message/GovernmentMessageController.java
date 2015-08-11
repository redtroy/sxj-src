package com.sxj.supervisor.website.controller.message;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.model.login.SupervisorPrincipal;
import com.sxj.supervisor.model.message.TenderMessageQuery;
import com.sxj.supervisor.service.message.IMessageConfigService;
import com.sxj.supervisor.service.message.ITenderMessageService;
import com.sxj.supervisor.website.controller.BaseController;
import com.sxj.util.exception.WebException;

@Controller
@RequestMapping("/government")
public class GovernmentMessageController extends BaseController
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
        
        return "site/message/government-message";
    }
    
}
