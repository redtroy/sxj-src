package com.sxj.supervisor.entity.contract;

import java.io.Serializable;


import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.dao.contract.IContractBatchDao;

/**
 * 批次条目实体
 * 
 * @author Administrator
 *
 */
@Entity(mapper = IContractBatchDao.class)
@Table(name = "M_CONTRACT_BATCH")
public class ContractBatchEntity extends Pagable implements Serializable
{
    
    /**
     *  
     */
    private static final long serialVersionUID = -918867108456859467L;
    
    /**
     * 主键
     **/
    @Id(column = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    /**
     * 批次号
     */
    @Column(name = "BATCH_NO")
    private String batchNo;
    
    /**
     * 合同编号
     **/
    @Column(name = "CONTRACT_ID")
    private String contractId;
    
    /**
     * RFID号
     **/
    @Column(name = "RFID_NO")
    private String rfidNo;
    
    /**
     * 金额
     **/
    @Column(name = "AMOUNT")
    private Double amount;
    
    /**
     * 批次条目JSON
     **/
    @Column(name = "BATCH_ITEMS")
    private String batchItems;
    
    /**
     * 标识状态
     */
    @Column(name = "UPDATE_STATE")
    private Integer updateState;
    
    /**
     * 补损状态
     */
    @Column(name = "REPLENISH_STATE")
    private Integer replenishState;
    
    /**
     * 支付标识状态
     */
    @Column(name = "PAY_STATE")
    private Integer payState;
    
    private Integer type;
    
    /**
     * 出库标识状态
     */
    @Column(name = "WAREHOUSE_STATE")
    private Integer warehouseState;
    
    /**
     *支付单号
     */
    @Column(name = "PAY_NO")
    private String payNo;
    
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
    
    public String getBatchNo()
    {
        return batchNo;
    }
    
    public void setBatchNo(String batchNo)
    {
        this.batchNo = batchNo;
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
    
    public String getRfidNo()
    {
        return rfidNo;
    }
    
    public void setRfidNo(String rfidNo)
    {
        this.rfidNo = rfidNo;
    }
    
    public Double getAmount()
    {
        return amount;
    }
    
    public void setAmount(Double amount)
    {
        this.amount = amount;
    }
    
    public String getBatchItems()
    {
        return batchItems;
    }
    
    public void setBatchItems(String batchItems)
    {
        this.batchItems = batchItems;
    }
    
    public Integer getType()
    {
        return type;
    }
    
    public void setType(Integer type)
    {
        this.type = type;
    }
    
    public Integer getReplenishState()
    {
        return replenishState;
    }
    
    public void setReplenishState(Integer replenishState)
    {
        this.replenishState = replenishState;
    }
    
    public Integer getPayState()
    {
        return payState;
    }
    
    public void setPayState(Integer payState)
    {
        this.payState = payState;
    }
    
    public Integer getWarehouseState()
    {
        return warehouseState;
    }
    
    public void setWarehouseState(Integer warehouseState)
    {
        this.warehouseState = warehouseState;
    }
    
    public String getPayNo()
    {
        return payNo;
    }
    
    public void setPayNo(String payNo)
    {
        this.payNo = payNo;
    }
    
}
