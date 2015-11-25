package com.sxj.supervisor.manage.controller.pay;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.entity.financ.FinancEntity;
import com.sxj.supervisor.enu.record.ContractTypeEnum;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.comet.MessageChannel;
import com.sxj.supervisor.model.financ.FinanceQuery;
import com.sxj.supervisor.model.login.SupervisorPrincipal;
import com.sxj.supervisor.model.statistics.AccountingModel;
import com.sxj.supervisor.service.financ.IFinancService;
import com.sxj.util.comet.CometServiceImpl;
import com.sxj.util.exception.WebException;

@Controller
@RequestMapping("/finance")
public class FinanceController extends BaseController
{
    @Autowired
    private IFinancService financeService;
    
    @RequestMapping("financeList")
    public String finance(ModelMap map, FinanceQuery query,
            String startDate, String del, String endDate, HttpSession session)
            throws WebException
    {
        try
        {
            if (query != null)
            {
                query.setPagable(true);
            }
            List<FinancEntity> list=financeService.financeList(query);
            map.put("list", list);
            map.put("query", query);
        }
        catch (Exception e)
        {
            throw new WebException(e.getMessage());
        }
        return "manage/financ/finacelistman";
    }
}
