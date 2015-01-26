package com.sxj.supervisor.model.contract;

import java.io.Serializable;
import java.util.List;

import com.sxj.supervisor.entity.contract.ContractBatchEntity;

/**
 * 合同批次信息
 * @author Ann
 *
 */
public class ContractBatchModel implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 2009473407892728810L;
    
    /**
     * 合同批次信息
     */
    private ContractBatchEntity batch;
    
    /**
     * 批次条目JSON
     **/
    private List<BatchItemModel> batchItems;
    
    public ContractBatchEntity getBatch()
    {
        return batch;
    }
    
    public void setBatch(ContractBatchEntity batch)
    {
        this.batch = batch;
    }
    
    public List<BatchItemModel> getBatchItems()
    {
        return batchItems;
    }
    
    public void setBatchItems(List<BatchItemModel> batchItems)
    {
        this.batchItems = batchItems;
    }
    
}
