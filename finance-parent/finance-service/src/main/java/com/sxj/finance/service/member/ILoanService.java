package com.sxj.finance.service.member;

import com.sxj.finance.model.member.LoanQuery;
import com.sxj.util.exception.ServiceException;

public interface ILoanService {

	public void addLoanInfo(LoanQuery loan) throws ServiceException;
	
	public void modifyLoanInfo(LoanQuery loan) throws ServiceException;
	
	public LoanQuery queryLoanInfo(String memberNo) throws ServiceException;
	
}
