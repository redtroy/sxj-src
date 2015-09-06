package com.sxj.supervisor.entity.contract;

import java.io.Serializable;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.dao.contract.IContractItemDao;
import com.sxj.supervisor.enu.contract.ContractWindowTypeEnum;

/**
 * 合同产品实体
 * 
 * @author Administrator
 *
 */
@Entity(mapper = IContractItemDao.class)
@Table(name = "M_CONTRACT_ITEM")
public class ContractItemEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 7060391136830389446L;
    
    /**
     * 主键
     **/
    @Id(column = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    /**
     * 合同ID
     **/
    @Column(name = "CONTRACT_ID")
    private String contractId;
    
    /**
     * 产品名称
     **/
    @Column(name = "PRODUCT_NAME")
    private String productName;
    
    /**
     * 规格型号
     **/
    @Column(name = "PRODUCT_MODEL")
    private String productModel;
    
    /**
     * 数量
     **/
    @Column(name = "QUANTITY")
    private Float quantity;
    
    /**
     * 单价
     **/
    @Column(name = "PRICE")
    private Double price;
    
    /**
     * 金额
     **/
    @Column(name = "AMOUNT")
    private Double amount;
    
    /**
     * 备注
     **/
    @Column(name = "REMARKS")
    private String remarks;
    
    /**
     * 标识状态
     */
    @Column(name = "UPDATE_STATE")
    private Integer updateState;
    
    /**
     * 门窗类型
     */
    @Column(name = "WINDOW_TYPE")
    private String windowType;
    
    public String getWindowType()
    {
        return windowType;
    }
    
    public void setWindowType(String windowType)
    {
        this.windowType = windowType;
    }
    
    public Integer getUpdateState()
    {
        return updateState;
    }
    
    public void setUpdateState(Integer updateState)
    {
        this.updateState = updateState;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getContractId()
    {
        return contractId;
    }
    
    public void setContractId(String contractId)
    {
        this.contractId = contractId;
    }
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    public String getProductModel()
    {
        return productModel;
    }
    
    public void setProductModel(String productModel)
    {
        this.productModel = productModel;
    }
    
    public Float getQuantity()
    {
        return quantity;
    }
    
    public void setQuantity(Float quantity)
    {
        this.quantity = quantity;
    }
    
    public Double getPrice()
    {
        return price;
    }
    
    public void setPrice(Double price)
    {
        this.price = price;
    }
    
    public Double getAmount()
    {
        return amount;
    }
    
    public void setAmount(Double amount)
    {
        this.amount = amount;
    }
    
    public String getRemarks()
    {
        return remarks;
    }
    
    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }
}
