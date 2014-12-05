package com.sxj.supervisor.website.controller.pay;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.enu.record.ContractTypeEnum;
import com.sxj.supervisor.model.comet.MessageChannel;
import com.sxj.supervisor.model.login.SupervisorPrincipal;
import com.sxj.supervisor.model.statistics.AccountingModel;
import com.sxj.supervisor.service.contract.IContractPayService;
import com.sxj.supervisor.website.controller.BaseController;
import com.sxj.util.exception.WebException;

@Controller("suFinanceController")
@RequestMapping("/finance")
public class FinanceController extends BaseController {
	@Autowired
	private IContractPayService payService;

	@RequestMapping("finance")
	public String finance(ModelMap map, AccountingModel query,
			String startDate, String endDate, HttpSession session)
			throws WebException {
		try {
			if (query != null) {
				query.setPagable(true);
			}
			SupervisorPrincipal info = getLoginInfo(session);
			String memberNo = info.getMember().getMemberNo();
			ContractTypeEnum[] types = ContractTypeEnum.values();
			List<AccountingModel> list = payService.query_finance(query,
					startDate, endDate);
			map.put("list", list);
			map.put("types", types);
			map.put("query", query);
			String channelName = MessageChannel.WEBSITE_PAY_MESSAGE + memberNo;
			map.put("channelName", channelName);
			// 注册监听

			registChannel(channelName);
		} catch (Exception e) {
			throw new WebException(e.getMessage());
		}
		return "site/pay/finance";
	}
}
