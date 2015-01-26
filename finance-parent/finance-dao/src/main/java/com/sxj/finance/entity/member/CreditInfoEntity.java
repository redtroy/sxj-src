package com.sxj.finance.entity.member;

import java.io.Serializable;

import com.sxj.finance.dao.member.ICreditInfoDao;
import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;

/**
 * 信用信息
 * 
 * @author dujinxin
 *
 */
@Entity(mapper = ICreditInfoDao.class)
@Table(name = "M_MEMBER_CREDITINFO")
public class CreditInfoEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -6827970324466184681L;
    
    @Id(column = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    /**
     * 
     */
    @Column(name = "MEMBER_NO")
    private String memberNo;
    
    /**
     * 未结清贷款笔数
     */
    @Column(name = "UN_LOAN")
    private Integer unLoan;
    
    /**
     * 未结清贷款总额
     */
    @Column(name = "UN_LOAN_AMOUNT")
    private Double unLoanAmount;
    
    /**
     * 有无贷款逾期记录
     */
    @Column(name = "IS_OVERDUE")
    private Boolean isOverdue;
    
    /**
     * 逾期贷款期数
     */
    @Column(name = "OVERDUE_NUM")
    private Integer overdueNum;
    
    /**
     * 单笔最高逾期金额
     */
    @Column(name = "MAX_OVERDUE_AMOUNT")
    private Double maxOverdueAmount;
    
    /**
     * 最长逾期月数
     */
    @Column(name = "MAX_OVERDUE_MONTH")
    private Integer maxOverdueMonth;
    
    /**
     * 信用卡逾期记录
     */
    @Column(name = "CARD_OVERDUE")
    private Integer cardOverdue;
    
    /**
     * 单笔最高逾期金额
     */
    @Column(name = "CARD_MAX_OVERDUE_AMOUNT")
    private Double cardMaxOverdueAmount;
    
    /**
     * 对外担保总额
     */
    @Column(name = "GUARANTEE")
    private Double guarantee;
    
    /**
     * 未结清贷款到期明细
     */
    @Column(name = "LOAN_ITEM")
    private String loanItem;
    
    /**
     * 对外担保到期明细
     */
    @Column(name = "GUARANTEE_ITEM")
    private String guaranteeItem;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getMemberNo()
    {
        return memberNo;
    }
    
    public void setMemberNo(String memberNo)
    {
        this.memberNo = memberNo;
    }
    
    public Integer getUnLoan()
    {
        return unLoan;
    }
    
    public void setUnLoan(Integer unLoan)
    {
        this.unLoan = unLoan;
    }
    
    public Double getUnLoanAmount()
    {
        return unLoanAmount;
    }
    
    public void setUnLoanAmount(Double unLoanAmount)
    {
        this.unLoanAmount = unLoanAmount;
    }
    
    public Boolean getIsOverdue()
    {
        return isOverdue;
    }
    
    public void setIsOverdue(Boolean isOverdue)
    {
        this.isOverdue = isOverdue;
    }
    
    public Integer getOverdueNum()
    {
        return overdueNum;
    }
    
    public void setOverdueNum(Integer overdueNum)
    {
        this.overdueNum = overdueNum;
    }
    
    public Double getMaxOverdueAmount()
    {
        return maxOverdueAmount;
    }
    
    public void setMaxOverdueAmount(Double maxOverdueAmount)
    {
        this.maxOverdueAmount = maxOverdueAmount;
    }
    
    public Integer getMaxOverdueMonth()
    {
        return maxOverdueMonth;
    }
    
    public void setMaxOverdueMonth(Integer maxOverdueMonth)
    {
        this.maxOverdueMonth = maxOverdueMonth;
    }
    
    public Integer getCardOverdue()
    {
        return cardOverdue;
    }
    
    public void setCardOverdue(Integer cardOverdue)
    {
        this.cardOverdue = cardOverdue;
    }
    
    public Double getCardMaxOverdueAmount()
    {
        return cardMaxOverdueAmount;
    }
    
    public void setCardMaxOverdueAmount(Double cardMaxOverdueAmount)
    {
        this.cardMaxOverdueAmount = cardMaxOverdueAmount;
    }
    
    public Double getGuarantee()
    {
        return guarantee;
    }
    
    public void setGuarantee(Double guarantee)
    {
        this.guarantee = guarantee;
    }
    
    public String getLoanItem()
    {
        return loanItem;
    }
    
    public void setLoanItem(String loanItem)
    {
        this.loanItem = loanItem;
    }
    
    public String getGuaranteeItem()
    {
        return guaranteeItem;
    }
    
    public void setGuaranteeItem(String guaranteeItem)
    {
        this.guaranteeItem = guaranteeItem;
    }
    
}
