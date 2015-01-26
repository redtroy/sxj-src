package com.sxj.finance.model.finance;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;

public class FinanceModel extends Pagable implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 6176313337926256618L;
    
    private String id;
    
    private String payNo;
    
    private String memberNo;
    
    private String contractNo;
    
    private String batchNo;
    
    private Double payAmount;
    
    private Double financeAmount;
    
    private Double creditAmount;
    
    private String content;
    
    private Integer state;
    
    private String startDate;
    
    private String endDate;
    
    public String getMemberNo()
    {
        return memberNo;
    }
    
    public void setMemberNo(String memberNo)
    {
        this.memberNo = memberNo;
    }
    
    public String getStartDate()
    {
        return startDate;
    }
    
    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }
    
    public String getEndDate()
    {
        return endDate;
    }
    
    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getPayNo()
    {
        return payNo;
    }
    
    public void setPayNo(String payNo)
    {
        this.payNo = payNo;
    }
    
    public String getContractNo()
    {
        return contractNo;
    }
    
    public void setContractNo(String contractNo)
    {
        this.contractNo = contractNo;
    }
    
    public String getBatchNo()
    {
        return batchNo;
    }
    
    public void setBatchNo(String batchNo)
    {
        this.batchNo = batchNo;
    }
    
    public Double getPayAmount()
    {
        return payAmount;
    }
    
    public void setPayAmount(Double payAmount)
    {
        this.payAmount = payAmount;
    }
    
    public Double getFinanceAmount()
    {
        return financeAmount;
    }
    
    public void setFinanceAmount(Double financeAmount)
    {
        this.financeAmount = financeAmount;
    }
    
    public Double getCreditAmount()
    {
        return creditAmount;
    }
    
    public void setCreditAmount(Double creditAmount)
    {
        this.creditAmount = creditAmount;
    }
    
    public String getContent()
    {
        return content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
    
    public Integer getState()
    {
        return state;
    }
    
    public void setState(Integer state)
    {
        this.state = state;
    }
}
