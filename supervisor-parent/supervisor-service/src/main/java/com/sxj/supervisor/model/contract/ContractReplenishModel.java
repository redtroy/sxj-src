package com.sxj.supervisor.model.contract;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.sxj.supervisor.entity.contract.ReplenishContractEntity;

/**
 * 合同补损信息
 * @author Ann
 *
 */
public class ContractReplenishModel implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 2009473407892728810L;
    
    /**
     * 合同补损信息
     */
    private ReplenishContractEntity replenishContract;
    
    /**
     * 补损信息明细
     **/
    private List<ReplenishBatchModel> batchItems;
    
    public ReplenishContractEntity getReplenishContract()
    {
        return replenishContract;
    }
    
    public void setReplenishContract(ReplenishContractEntity replenishContract)
    {
        this.replenishContract = replenishContract;
    }
    
    public List<ReplenishBatchModel> getBatchItems()
    {
        if (batchItems == null)
            batchItems = new ArrayList<ReplenishBatchModel>();
        return batchItems;
    }
    
    public void setBatchItems(List<ReplenishBatchModel> batchItems)
    {
        
        this.batchItems = batchItems;
    }
    
}
