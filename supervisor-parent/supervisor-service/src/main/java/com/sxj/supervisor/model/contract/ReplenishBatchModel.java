package com.sxj.supervisor.model.contract;

import java.io.Serializable;
import java.util.List;

import com.sxj.supervisor.entity.contract.ReplenishBatchEntity;

/**
 * 合同补损批次信息模型
 * @author Ann
 *
 */
public class ReplenishBatchModel implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -1420459713047138620L;
    
    /**
     * 合同补损批次信息
     */
    private ReplenishBatchEntity replenishBatch;
    
    /**
     * 批次条目JSON
     **/
    private List<BatchItemModel> replenishBatchItems;
    
    public ReplenishBatchEntity getReplenishBatch()
    {
        return replenishBatch;
    }
    
    public void setReplenishBatch(ReplenishBatchEntity replenishBatch)
    {
        this.replenishBatch = replenishBatch;
    }
    
    public List<BatchItemModel> getReplenishBatchItems()
    {
        return replenishBatchItems;
    }
    
    public void setReplenishBatchItems(List<BatchItemModel> replenishBatchItems)
    {
        this.replenishBatchItems = replenishBatchItems;
    }
    
}
