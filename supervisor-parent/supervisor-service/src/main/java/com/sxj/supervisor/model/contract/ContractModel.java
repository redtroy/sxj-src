package com.sxj.supervisor.model.contract;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.sxj.supervisor.entity.contract.ContractEntity;
import com.sxj.supervisor.entity.contract.ContractItemEntity;

/**
 * 合同信息模型
 * @author Ann
 *
 */
public class ContractModel implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 980424780684626879L;
    
    /**
     * 合同主体信息
     */
    private ContractEntity contract;
    
    /**
     * 合同产品条目信息
     */
    private List<ContractItemEntity> itemList;
    
    /**
     * 合同批次条目信息
     */
    private List<ContractBatchModel> batchList;
    
    /**
     * 合同变更产品条目信息
     */
    private List<ContractModifyModel> modifyList;
    
    /**
     * 合同补损条目信息
     */
    
    private List<ContractReplenishModel> replenishList;
    
    /**
     * 变更记录
     */
    private List<StateLogModel> stateLogList;
    
    public ContractEntity getContract()
    {
        return contract;
    }
    
    public void setContract(ContractEntity contract)
    {
        this.contract = contract;
    }
    
    public List<ContractItemEntity> getItemList()
    {
        return itemList;
    }
    
    public void setItemList(List<ContractItemEntity> itemList)
    {
        this.itemList = itemList;
    }
    
    public List<ContractBatchModel> getBatchList()
    {
        if (batchList == null)
        	batchList= new ArrayList<ContractBatchModel>();
        return batchList;
    }
    
    public void setBatchList(List<ContractBatchModel> batchList)
    {
        this.batchList = batchList;
    }
    
    public List<ContractModifyModel> getModifyList()
    {
    	if(modifyList==null)
    		modifyList= new ArrayList<ContractModifyModel>();
        return modifyList;
    }
    
    public void setModifyList(List<ContractModifyModel> modifyList)
    {
        this.modifyList = modifyList;
    }
    
    public List<ContractReplenishModel> getReplenishList()
    {
        if (replenishList == null)
        	replenishList= new ArrayList<ContractReplenishModel>();
        return replenishList;
    }
    
    public void setReplenishList(List<ContractReplenishModel> replenishList)
    {
        this.replenishList = replenishList;
    }
    
    public List<StateLogModel> getStateLogList()
    {
        return stateLogList;
    }
    
    public void setStateLogList(List<StateLogModel> stateLogList)
    {
        this.stateLogList = stateLogList;
    }
    
    @Override
    public String toString()
    {
        return "ContractModel [contract=" + contract + ", itemList=" + itemList
                + ", batchList=" + batchList + ", modifyList=" + modifyList
                + ", replenishList=" + replenishList + ", stateLogList="
                + stateLogList + "]";
    }
    
}
