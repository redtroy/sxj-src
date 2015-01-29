package com.sxj.supervisor.dao.contract;

import java.util.List;
import java.util.Map;

import com.sxj.mybatis.orm.annotations.Delete;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.supervisor.entity.contract.ContractEntity;
import com.sxj.util.persistent.QueryCondition;

public interface IContractDao
{
    /**
     * 添加合同
     *
     * @param contract
     *            添加合同
     **/
    @Insert
    public void addContract(ContractEntity contract);
    
    /**
     * 修改合同
     *
     * @param contract
     **/
    @Update
    public void updateContract(ContractEntity contract);
    
    /**
     * 获取合同信息
     *
     * @param id
     **/
    @Get
    public ContractEntity getContract(String id);
    
    /**
     * 查询合同
     *
     * @param query
     **/
    public List<ContractEntity> queryContract(
            QueryCondition<ContractEntity> query);
    
    /**
     * 删除合同
     * 
     * @param id
     */
    @Delete
    public void deleteContract(String id);
    
    /**
     * 更新合同RFID数量
     * 
     * @param id
     */
    public int updateContractRfid(Map<String, Object> map);
    
    /**
     * 根据合同号获取合同
     * @param contractNo
     * @return
     */
    public ContractEntity getContractByContractNo(String contractNo);
}
