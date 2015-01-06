package com.sxj.finance.website.controller.member;



import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.finance.entity.member.MemberEntity;
import com.sxj.finance.enu.member.SexStatesEnum;
import com.sxj.finance.model.finance.FinancePrincipal;
import com.sxj.finance.model.member.LoanQuery;
import com.sxj.finance.service.member.ILoanService;
import com.sxj.finance.service.member.IMemberService;
import com.sxj.finance.website.controller.BaseController;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@RequestMapping("/member")
@Controller
public class MemberController extends BaseController {
	
	@Autowired
	private IMemberService memberService;
	
	@Autowired
	private ILoanService loanService;
	
	/**
	 * 根据会员号获取会员信息
	 * 
	 * @param map
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("/info")
	public String memberList(ModelMap map, HttpSession session,
			HttpServletRequest request) throws WebException {
		try {
			
			FinancePrincipal info = getLoginInfo(session);
			if (info != null) {
				MemberEntity member = memberService.getMember(info.getMember()
						.getId());
				if (member.getAccountNum() == null) {
					member.setAccountNum(0);
				}
				LoanQuery loan =loanService.queryLoanInfo(member.getMemberNo());
				map.put("member", member);
				map.put("loan", loan);
				if(loan.getMemberInfo()==null){
					return "site/member/member";
				}else{
					return "site/member/memberInfo";
				}
				
			}
			return LOGIN;
		} catch (Exception e) {
			SxjLogger.error("获取会员信息错误", e, this.getClass());
			throw new WebException(e.getMessage());
		}

	}
	
	/**
	 * 添加贷款信息
	 * 
	 * @param map
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("/addLoan")
	public @ResponseBody Map<String, String> addLoan(LoanQuery loan) throws WebException {
		Map<String, String> map = new HashMap<String, String>();
		try {
				loanService.addLoanInfo(loan);
				map.put("isOK", "ok");
				
		} catch (Exception e) {
			map.put("isOK", "no");
			SxjLogger.error("添加贷款信息错误", e, this.getClass());
			throw new WebException(e.getMessage());
		}
		return map;
	}
	
	/**
	 * 跳转修改页面
	 * @param map
	 * @param session
	 * @param request
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("/toModify")
	public String toModify(ModelMap map, HttpSession session,
			HttpServletRequest request) throws WebException {
		try {
			
			FinancePrincipal info = getLoginInfo(session);
			if (info != null) {
				MemberEntity member = memberService.getMember(info.getMember()
						.getId());
				if (member.getAccountNum() == null) {
					member.setAccountNum(0);
				}
				LoanQuery loan =loanService.queryLoanInfo(member.getMemberNo());
				map.put("member", member);
				map.put("loan", loan);
					return "site/member/edit-memberInfo";
			}
			return LOGIN;
		} catch (Exception e) {
			SxjLogger.error("获取会员信息错误", e, this.getClass());
			throw new WebException(e.getMessage());
		}

	}
	/**
	 * 修改贷款信息
	 * 
	 * @param map
	 * @return
	 * @throws WebException
	 */
	@RequestMapping("/modify")
	public @ResponseBody Map<String, String> modifyLoan(LoanQuery loan) throws WebException {
		Map<String, String> map = new HashMap<String, String>();
		try {
				loanService.modifyLoanInfo(loan);
				map.put("isOK", "ok");
		} catch (Exception e) {
			map.put("isOK", "no");
			SxjLogger.error("修改贷款信息错误", e, this.getClass());
			throw new WebException(e.getMessage());
		}
		return map;
	}
}
