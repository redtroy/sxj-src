package com.sxj.finance.service.impl.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.finance.dao.member.IAssetsInfoDao;
import com.sxj.finance.dao.member.ICreditInfoDao;
import com.sxj.finance.dao.member.IGuaranteeDao;
import com.sxj.finance.dao.member.IManagementDao;
import com.sxj.finance.dao.member.IMemberInfoDao;
import com.sxj.finance.entity.member.AssetsInfoEntity;
import com.sxj.finance.entity.member.CreditInfoEntity;
import com.sxj.finance.entity.member.GuaranteeEntity;
import com.sxj.finance.entity.member.ManagementEntity;
import com.sxj.finance.entity.member.MemberInfoEntity;
import com.sxj.finance.model.member.LoanQuery;
import com.sxj.finance.service.member.ILoanService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;

@Service
@Transactional
public class LoanServiceImpl implements ILoanService
{
    
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
    public void addLoanInfo(LoanQuery loan) throws ServiceException
    {
        try
        {
            assetsInfoDao.addAssetsInfo(loan.getAssetsInfo());
            creditInfoDao.addCreditInfo(loan.getCreditInfo());
            guaranteeDao.addGuarantee(loan.getGuarantee());
            managementDao.addManagement(loan.getManagement());
            memberInfoDao.addMemberInfo(loan.getMemberInfo());
            
        }
        catch (Exception e)
        {
            SxjLogger.error("新增贷款申请表错误", e, this.getClass());
            throw new ServiceException("新增贷款申请表错误", e);
        }
        
    }
    
    /**
     * 更新贷款信息
     */
    @Override
    @Transactional
    public void modifyLoanInfo(LoanQuery loan) throws ServiceException
    {
        try
        {
        	if(loan.getAssetsInfo()!=null){
        		 assetsInfoDao.updateAssetsInfo(loan.getAssetsInfo());
        	}
        	if(loan.getCreditInfo()!=null){
        		 creditInfoDao.updateCreditInfo(loan.getCreditInfo());
        	}
        	if(loan.getGuarantee()!=null){
        		 guaranteeDao.updateGuarantee(loan.getGuarantee());
        	}
        	if(loan.getManagement()!=null){
        		managementDao.updateManagement(loan.getManagement());
        	}
        	if(loan.getMemberInfo()!=null){
        		 memberInfoDao.updateMemberInfo(loan.getMemberInfo());
        	}
        }
        catch (Exception e)
        {
            SxjLogger.error("更新资料错误", e, this.getClass());
            throw new ServiceException("更新资料错误", e);
        }
        
    }
    
    @Override
    public LoanQuery queryLoanInfo(String memberNo) throws ServiceException
    {
        try
        {
            LoanQuery loan = new LoanQuery();
            AssetsInfoEntity assetsInfo = assetsInfoDao.getAssetsInfo(memberNo);
            CreditInfoEntity creditInfo = creditInfoDao.getCreditInfo(memberNo);
            GuaranteeEntity guarantee = guaranteeDao.getGuarantee(memberNo);
            ManagementEntity management = managementDao.getManagement(memberNo);
            MemberInfoEntity memberInfo = memberInfoDao.getMemberInfo(memberNo);
            loan.setAssetsInfo(assetsInfo);
            loan.setCreditInfo(creditInfo);
            loan.setGuarantee(guarantee);
            loan.setManagement(management);
            loan.setMemberInfo(memberInfo);
            return loan;
        }
        catch (Exception e)
        {
            SxjLogger.error("获取贷款申请表错误", e, this.getClass());
            throw new ServiceException("获取贷款申请表错误", e);
        }
    }
    
}
