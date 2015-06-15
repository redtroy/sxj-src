package com.sxj.supervisor.entity.contract;

import java.io.Serializable;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;

@Entity(mapper = IContractBatchNewDao.class)
@Table(name = "M_CONTRACT_BATCH_NEW")
public class ContractBatchNewEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -6851465243935975439L;
    
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
    @Column(name = "CONTRACT_NO")
    private String contractNo;
    
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
     * 类型
     **/
    @Column(name = "TYPE")
    private BatchTypeEnum type;
    
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
    
    /**
     * 新RFID号
     **/
    @Column(name = "NEW_RFID_NO")
    private String newRfidNo;
    
    /**
     * 备案号
     **/
    @Column(name = "RECORD_NO")
    private String recordNo;
    
    public BatchTypeEnum getType()
    {
        return type;
    }
    
    public void setType(BatchTypeEnum type)
    {
        this.type = type;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getBatchNo()
    {
        return batchNo;
    }
    
    public void setBatchNo(String batchNo)
    {
        this.batchNo = batchNo;
    }
    
    public String getContractNo()
    {
        return contractNo;
    }
    
    public void setContractNo(String contractNo)
    {
        this.contractNo = contractNo;
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
    
    public Integer getUpdateState()
    {
        return updateState;
    }
    
    public void setUpdateState(Integer updateState)
    {
        this.updateState = updateState;
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
    
    public String getNewRfidNo()
    {
        return newRfidNo;
    }
    
    public void setNewRfidNo(String newRfidNo)
    {
        this.newRfidNo = newRfidNo;
    }
    
    public String getRecordNo()
    {
        return recordNo;
    }
    
    public void setRecordNo(String recordNo)
    {
        this.recordNo = recordNo;
    }
    
}
