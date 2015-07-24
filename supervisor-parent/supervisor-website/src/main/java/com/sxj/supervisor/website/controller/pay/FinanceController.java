package com.sxj.supervisor.website.controller.pay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.entity.financ.FinancEntity;
import com.sxj.supervisor.enu.record.ContractTypeEnum;
import com.sxj.supervisor.model.comet.MessageChannel;
import com.sxj.supervisor.model.login.SupervisorPrincipal;
import com.sxj.supervisor.model.statistics.AccountingModel;
import com.sxj.supervisor.service.contract.IContractPayService;
import com.sxj.supervisor.service.financ.IFinancService;
import com.sxj.supervisor.website.controller.BaseController;
import com.sxj.util.comet.CometServiceImpl;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller("suFinanceController")
@RequestMapping("/finance")
public class FinanceController extends BaseController
{
    @Autowired
    private IContractPayService payService;
    
    @Autowired
    private IFinancService financeService;
    
    @RequestMapping("add")
    public String add(FinancEntity entity, HttpSession session)
            throws WebException
    {
        try
        {
            SupervisorPrincipal info = getLoginInfo(session);
            String memberNo = info.getMember().getMemberNo();
            entity.setMemberNo(memberNo);
            financeService.addFinance(entity);
            
        }
        catch (Exception e)
        {
            throw new WebException(e.getMessage());
        }
        return "site/finance/appsucc";
    }
    
    @RequestMapping("finance")
    public String finance(ModelMap map, AccountingModel query,
            String startDate, String del, String endDate, HttpSession session)
            throws WebException
    {
        try
        {
            if (query != null)
            {
                query.setPagable(true);
            }
            SupervisorPrincipal info = getLoginInfo(session);
            String memberNo = info.getMember().getMemberNo();
            ContractTypeEnum[] types = ContractTypeEnum.values();
            List<AccountingModel> list = payService.query_finance(query,
                    startDate,
                    endDate,
                    memberNo);
            map.put("list", list);
            map.put("types", types);
            map.put("query", query);
            if (info.getMember().getType().getId() == 0)
            {
                map.put("state", "a");
            }
            else
            {
                map.put("state", "b");
            }
            String channelName = MessageChannel.WEBSITE_FINANCE_MESSAGE
                    + memberNo;
            if ("1".equals(del))
            {
                CometServiceImpl.setCount(channelName, 0l);
            }
            map.put("channelName", channelName);
            // 注册监听
            
            //registChannel(channelName);
        }
        catch (Exception e)
        {
            throw new WebException(e.getMessage());
        }
        return "site/pay/finance";
    }
    
    @RequestMapping("cleanChannel")
    public @ResponseBody Map<String, String> cleanChannel(String channelName)
            throws WebException
    {
        try
        {
            Map<String, String> map = new HashMap<String, String>();
            CometServiceImpl.setCount(channelName, 0l);
            map.put("isOk", "ok");
            return map;
        }
        catch (Exception e)
        {
            SxjLogger.error("清除频道错误", e, this.getClass());
            throw new WebException("清除频道错误");
        }
        
    }
}
