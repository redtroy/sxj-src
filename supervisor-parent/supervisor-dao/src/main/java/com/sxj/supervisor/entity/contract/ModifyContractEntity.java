package com.sxj.supervisor.entity.contract;

import java.io.Serializable;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.dao.contract.IContractModifyDao;

/**
 * 合同变更信息
 * @author Ann
 *
 */
@Entity(mapper = IContractModifyDao.class)
@Table(name = "M_CONTRACT_MODIFY")
public class ModifyContractEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -5950638462075792173L;
    
    /**
     * 主键
     **/
    @Id(column = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    /**
     * 合同ID
     */
    @Column(name = "CONTRACT_ID")
    private String contractId;
    
    /**
     * 备案号
     */
    @Column(name = "RECORD_NO")
    private String recordNo;
    
    /**
     * 扫描件
     */
    @Column(name = "IMG_PATH")
    private String imgPath;
    
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
    
    public String getRecordNo()
    {
        return recordNo;
    }
    
    public void setRecordNo(String recordNo)
    {
        this.recordNo = recordNo;
    }
    
    public String getImgPath()
    {
        return imgPath;
    }
    
    public void setImgPath(String imgPath)
    {
        this.imgPath = imgPath;
    }
    
}
