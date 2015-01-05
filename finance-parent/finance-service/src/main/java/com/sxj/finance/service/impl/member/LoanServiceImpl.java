package com.sxj.finance.service.impl.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.finance.dao.member.IAssetsInfoDao;
import com.sxj.finance.dao.member.ICreditInfoDao;
import com.sxj.finance.dao.member.IGuaranteeDao;
import com.sxj.finance.dao.member.IManagementDao;
import com.sxj.finance.dao.member.IMemberInfoDao;
import com.sxj.finance.model.member.LoanQuery;
import com.sxj.finance.service.member.ILoanService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;

@Service
@Transactional
public class LoanServiceImpl implements ILoanService {

	@Autowired
	private IAssetsInfoDao assetsInfoDao;
	@Autowired
	private ICreditInfoDao creditInfoDao;
	@Autowired
	private IGuaranteeDao guaranteeDao;
	@Autowired
	private IManagementDao managementDao;
	@Autowired
	private IMemberInfoDao memberInfoDao;

	/**
	 * 新增贷款信息
	 */
	@Override
	@Transactional
	public void addLoanInfo(LoanQuery loan) throws ServiceException {
		try {
			assetsInfoDao.addAssetsInfo(loan.getAssetsInfo());
			creditInfoDao.addCreditInfo(loan.getCreditInfo());
			guaranteeDao.addGuarantee(loan.getGuarantee());
			managementDao.addManagement(loan.getManagement());
			memberInfoDao.addMemberInfo(loan.getMemberInfo());
			
		} catch (Exception e) {
			SxjLogger.error("新增贷款申请表错误", e, this.getClass());
			throw new ServiceException("新增贷款申请表错误", e);
		}

	}

	/**
	 * 更新贷款信息
	 */
	@Override
	@Transactional
	public void modifyLoanInfo(LoanQuery loan) throws ServiceException {
		try {
			assetsInfoDao.updateAssetsInfo(loan.getAssetsInfo());
			creditInfoDao.updateCreditInfo(loan.getCreditInfo());
			guaranteeDao.updateGuarantee(loan.getGuarantee());
			managementDao.updateManagement(loan.getManagement());
			memberInfoDao.updateMemberInfo(loan.getMemberInfo());
			
		} catch (Exception e) {
			SxjLogger.error("更新贷款申请表错误", e, this.getClass());
			throw new ServiceException("更新贷款申请表错误", e);
		}

	}

	@Override
	public LoanQuery modifyLoanInfo(String memberNo) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
