package com.sxj.supervisor.website.controller.message;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.entity.gov.GovEntity;
import com.sxj.supervisor.model.login.SupervisorPrincipal;
import com.sxj.supervisor.service.gov.IGovService;
import com.sxj.supervisor.website.controller.BaseController;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/government")
public class GovListController extends BaseController
{
    @Autowired
    private IGovService govService;
    
    @RequestMapping("/query")
    public String query(GovEntity query, ModelMap map, HttpSession session)
            throws WebException
    {
        SupervisorPrincipal user = getLoginInfo(session);
        if (user == null)
        {
            return LOGIN;
        }
        try
        {
            query.setPagable(true);
            List<GovEntity> list = govService.queryGovList(query);
            map.put("query", query);
            map.put("list", list);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            SxjLogger.error(e.getMessage(), e, this.getClass());
            map.put("error", e.getMessage());
        }
        return "site/message/govlist";
    }
}
