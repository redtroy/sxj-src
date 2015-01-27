package com.sxj.supervisor.entity.contract;

import java.io.Serializable;
import java.util.Date;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Sn;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.dao.contract.IContractDao;
import com.sxj.supervisor.enu.contract.ContractStateEnum;
import com.sxj.supervisor.enu.contract.ContractSureStateEnum;
import com.sxj.supervisor.enu.record.ContractTypeEnum;

/**
 * 合同实体
 * 
 * @author Administrator
 *
 */
@Entity(mapper = IContractDao.class)
@Table(name = "M_CONTRACT")
public class ContractEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -1859300557618319342L;
    
    /**
     * 主键ID
     **/
    @Id(column = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    /**
     * 合同号
     **/
    @Column(name = "CONTRACT_NO")
    @Sn(pattern = "0000", step = 1, table = "T_SN", stub = "F_SN_NAME", sn = "F_SN_NUMBER", stubValueProperty = "dateNo")
    private String contractNo;
    
    private String dateNo;
    
    /**
     * 签订地址
     **/
    @Column(name = "ADDRESS")
    private String address;
    
    /**
     * 甲方
     **/
    @Column(name = "MEMBER_ID_A")
    private String memberIdA;
    
    /**
     * 乙方
     **/
    @Column(name = "MEMBER_ID_B")
    private String memberIdB;
    
    /**
     * 工程名称
     **/
    @Column(name = "ENG_NAME")
    private String engName;
    
    /**
     * 工程地址
     **/
    @Column(name = "ENG_ADDRESS")
    private String engAddress;
    
    /**
     * 合同备案号
     **/
    @Column(name = "RECORD_NO")
    private String recordNo;
    
    /**
     * 签订日期
     **/
    @Column(name = "SIGNED_DATE")
    private Date signedDate;
    
    /**
     * 合同期限
     **/
    @Column(name = "VALIDITY_DATE")
    private Date validityDate;
    
    /**
     * 生效日期
     **/
    @Column(name = "START_DATE")
    private Date startDate;
    
    /**
     * 生成时间
     */
    @Column(name = "CREATE_DATE")
    private Date createDate;
    
    /**
     * 备案时间
     */
    @Column(name = "RECORD_DATE")
    private Date recordDate;
    
    /**
     * 合同定金
     **/
    @Column(name = "DEPOSIT")
    private Double deposit;
    
    /**
     * 备注
     **/
    @Column(name = "REMARKS")
    private String remarks;
    
    /**
     * 交货地址
     **/
    @Column(name = "DELIVERY_ADDRESS")
    private String deliveryAddress;
    
    /**
     * 状态
     **/
    @Column(name = "STATE")
    private ContractStateEnum state;
    
    /**
     * 确认状态状态
     **/
    @Column(name = "CONFIRM_STATE")
    private ContractSureStateEnum confirmState;
    
    /**
     * 合同类型
     **/
    @Column(name = "TYPE")
    private ContractTypeEnum type;
    
    /**
     * 关联合同号
     **/
    @Column(name = "REF_CONTRACT_NO")
    private String refContractNo;
    
    /**
     * 甲方名称
     **/
    @Column(name = "MEMBER_NAME_A")
    private String memberNameA;
    
    /**
     * 乙方名称
     **/
    @Column(name = "MEMBER_NAME_B")
    private String memberNameB;
    
    /**
     * 合同状态变更记录(JOSN)
     **/
    @Column(name = "STATE_LOG")
    private String stateLog;
    
    /**
     * 总批次
     **/
    @Column(name = "BATCH_COUNT")
    private Integer batchCount;
    
    /**
     * 当前更新备案号
     */
    @Column(name = "NOW_RECORD_NO")
    private String nowRecordNo;
    
    /**
     * 扫描件路径
     */
    @Column(name = "IMG_PATH")
    private String imgPath;
    
    /**
     * 合同条目总数量
     */
    @Column(name = "ITEM_QUANTITY")
    private Float itemQuantity;
    
    /**
     * 已经启用RFID数量
     */
    @Column(name = "USE_QUANTITY")
    private Float useQuantity;
    
    /**
     * 删除状态
     **/
    @Column(name = "DELETE_STATE")
    private Boolean deleteState = false;
    
    /**
     * 有效批次
     */
    @Column(name = "EFFECTIVE_BATCH")
    private Integer effectiveBatch;
    
    /**
     * 已支付批次
     */
    @Column(name = "PAY_BATCH")
    private Integer payBatch;
    
    public String getDateNo()
    {
        return dateNo;
    }
    
    public void setDateNo(String dateNo)
    {
        this.dateNo = dateNo;
    }
    
    public Boolean getDeleteState()
    {
        return deleteState;
    }
    
    public void setDeleteState(Boolean deleteState)
    {
        this.deleteState = deleteState;
    }
    
    public Date getCreateDate()
    {
        return createDate;
    }
    
    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }
    
    public Integer getBatchCount()
    {
        return batchCount;
    }
    
    public void setBatchCount(Integer batchCount)
    {
        this.batchCount = batchCount;
    }
    
    public String getNowRecordNo()
    {
        return nowRecordNo;
    }
    
    public void setNowRecordNo(String nowRecordNo)
    {
        this.nowRecordNo = nowRecordNo;
    }
    
    public String getImgPath()
    {
        return imgPath;
    }
    
    public void setImgPath(String imgPath)
    {
        this.imgPath = imgPath;
    }
    
    public Date getRecordDate()
    {
        return recordDate;
    }
    
    public void setRecordDate(Date recordDate)
    {
        this.recordDate = recordDate;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getContractNo()
    {
        return contractNo;
    }
    
    public void setContractNo(String contractNo)
    {
        this.contractNo = contractNo;
    }
    
    public String getAddress()
    {
        return address;
    }
    
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    public String getMemberIdA()
    {
        return memberIdA;
    }
    
    public void setMemberIdA(String memberIdA)
    {
        this.memberIdA = memberIdA;
    }
    
    public String getMemberIdB()
    {
        return memberIdB;
    }
    
    public void setMemberIdB(String memberIdB)
    {
        this.memberIdB = memberIdB;
    }
    
    public String getEngName()
    {
        return engName;
    }
    
    public void setEngName(String engName)
    {
        this.engName = engName;
    }
    
    public String getEngAddress()
    {
        return engAddress;
    }
    
    public void setEngAddress(String engAddress)
    {
        this.engAddress = engAddress;
    }
    
    public String getRecordNo()
    {
        return recordNo;
    }
    
    public void setRecordNo(String recordNo)
    {
        this.recordNo = recordNo;
    }
    
    public Date getSignedDate()
    {
        return signedDate;
    }
    
    public void setSignedDate(Date signedDate)
    {
        this.signedDate = signedDate;
    }
    
    public Date getValidityDate()
    {
        return validityDate;
    }
    
    public void setValidityDate(Date validityDate)
    {
        this.validityDate = validityDate;
    }
    
    public Date getStartDate()
    {
        return startDate;
    }
    
    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }
    
    public Double getDeposit()
    {
        return deposit;
    }
    
    public void setDeposit(Double deposit)
    {
        this.deposit = deposit;
    }
    
    public String getRemarks()
    {
        return remarks;
    }
    
    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }
    
    public String getDeliveryAddress()
    {
        return deliveryAddress;
    }
    
    public void setDeliveryAddress(String deliveryAddress)
    {
        this.deliveryAddress = deliveryAddress;
    }
    
    public ContractStateEnum getState()
    {
        return state;
    }
    
    public void setState(ContractStateEnum state)
    {
        this.state = state;
    }
    
    public ContractSureStateEnum getConfirmState()
    {
        return confirmState;
    }
    
    public void setConfirmState(ContractSureStateEnum confirmState)
    {
        this.confirmState = confirmState;
    }
    
    public ContractTypeEnum getType()
    {
        return type;
    }
    
    public void setType(ContractTypeEnum type)
    {
        this.type = type;
    }
    
    public String getRefContractNo()
    {
        return refContractNo;
    }
    
    public void setRefContractNo(String refContractNo)
    {
        this.refContractNo = refContractNo;
    }
    
    public String getMemberNameA()
    {
        return memberNameA;
    }
    
    public void setMemberNameA(String memberNameA)
    {
        this.memberNameA = memberNameA;
    }
    
    public String getMemberNameB()
    {
        return memberNameB;
    }
    
    public void setMemberNameB(String memberNameB)
    {
        this.memberNameB = memberNameB;
    }
    
    public String getStateLog()
    {
        return stateLog;
    }
    
    public void setStateLog(String stateLog)
    {
        this.stateLog = stateLog;
    }
    
    public Float getItemQuantity()
    {
        return itemQuantity;
    }
    
    public void setItemQuantity(Float itemQuantity)
    {
        this.itemQuantity = itemQuantity;
    }
    
    public Float getUseQuantity()
    {
        return useQuantity;
    }
    
    public void setUseQuantity(Float useQuantity)
    {
        this.useQuantity = useQuantity;
    }
    
    @Override
    public String toString()
    {
        return "ContractEntity [id=" + id + ", contractNo=" + contractNo
                + ", address=" + address + ", memberIdA=" + memberIdA
                + ", memberIdB=" + memberIdB + ", engName=" + engName
                + ", engAddress=" + engAddress + ", recordNo=" + recordNo
                + ", signedDate=" + signedDate + ", validityDate="
                + validityDate + ", startDate=" + startDate + ", createDate="
                + createDate + ", recordDate=" + recordDate + ", deposit="
                + deposit + ", remarks=" + remarks + ", deliveryAddress="
                + deliveryAddress + ", state=" + state + ", deleteState="
                + deleteState + ", confirmState=" + confirmState + ", type="
                + type + ", refContractNo=" + refContractNo + ", memberNameA="
                + memberNameA + ", memberNameB=" + memberNameB + ", stateLog="
                + stateLog + ", batchCount=" + batchCount + ", nowRecordNo="
                + nowRecordNo + ", imgPath=" + imgPath + "]";
    }
    
    public Integer getEffectiveBatch()
    {
        return effectiveBatch;
    }
    
    public void setEffectiveBatch(Integer effectiveBatch)
    {
        this.effectiveBatch = effectiveBatch;
    }
    
    public Integer getPayBatch()
    {
        return payBatch;
    }
    
    public void setPayBatch(Integer payBatch)
    {
        this.payBatch = payBatch;
    }
    
}
