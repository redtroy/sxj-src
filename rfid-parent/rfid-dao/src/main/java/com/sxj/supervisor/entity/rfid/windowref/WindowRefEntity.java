package com.sxj.supervisor.entity.rfid.windowref;

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
import com.sxj.supervisor.dao.rfid.windowref.IWindowRfidRefDao;
import com.sxj.supervisor.enu.rfid.ref.AuditStateEnum;
import com.sxj.supervisor.enu.rfid.window.WindowTypeEnum;
import com.sxj.supervisor.enu.rfid.windowref.LinkStateEnum;

/**
 * 门窗RFID关联申请
 * 
 * @author dujinxin
 *
 */
@Entity(mapper = IWindowRfidRefDao.class)
@Table(name = "R_WINDOW_REF")
public class WindowRefEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -2653603292849169190L;
    
    /**
     * ID
     */
    @Id(column = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    /**
     * RFID关联申请号
     */
    @Column(name = "RFID_REF_NO")
    @Sn(pattern = "0000", step = 1, table = "T_SN", stub = "F_SN_NAME", sn = "F_SN_NUMBER", stubValueProperty = "dateNo")
    private String rfidRefNo;
    
    private String dateNo;
    
    /**
     * RFID编号区间
     */
    @Column(name = "MIN_RFID_NO")
    private String minRfidNo;
    
    /**
     * RFID编号区间
     */
    @Column(name = "MAX_RFID_NO")
    private String maxRfidNo;
    
    @Column(name = "RFID_NOS")
    private String rfidNos;
    
    /**
     * 申请人
     */
    @Column(name = "MEMBER_NO")
    private String memberNo;
    
    /**
     * 申请人名称
     */
    @Column(name = "MEMBER_NAME")
    private String memberName;
    
    /**
     * 关联类型
     */
    @Column(name = "TYPE")
    private LinkStateEnum type;
    
    /**
     * 窗型代号
     */
    @Column(name = "WINDOWS_NO")
    private String windowsNo;
    
    /**
     * 玻璃批次
     */
    @Column(name = "GLASS_BATCH_NO")
    private String glassBatchNo;
    
    /**
     * 型材批次
     */
    @Column(name = "PROFILE_BATCH_NO")
    private String profileBatchNo;
    
    /**
     * 关联申请时间
     */
    @Column(name = "APPLY_DATE")
    private Date applyDate;
    
    /**
     * 被补损RFID
     */
    @Column(name = "REPLENISH_RFID")
    private String replenishRfid;
    
    /**
     * 合同号
     */
    @Column(name = "CONTRACT_NO")
    private String contractNo;
    
    /**
     * 删除状态
     */
    @Column(name = "DEL_STATE")
    private boolean delState = false;
    
    /**
     * 审核状态
     * 
     * @return
     */
    @Column(name = "STATE")
    private AuditStateEnum state;
    
    public String getWindowsNo()
    {
        return windowsNo;
    }
    
    public void setWindowsNo(String windowsNo)
    {
        this.windowsNo = windowsNo;
    }
    
    public AuditStateEnum getState()
    {
        return state;
    }
    
    public void setState(AuditStateEnum state)
    {
        this.state = state;
    }
    
    public boolean isDelState()
    {
        return delState;
    }
    
    public void setDelState(boolean delState)
    {
        this.delState = delState;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getRfidRefNo()
    {
        return rfidRefNo;
    }
    
    public void setRfidRefNo(String rfidRefNo)
    {
        this.rfidRefNo = rfidRefNo;
    }
    
    public String getMinRfidNo()
    {
        return minRfidNo;
    }
    
    public void setMinRfidNo(String minRfidNo)
    {
        this.minRfidNo = minRfidNo;
    }
    
    public String getMaxRfidNo()
    {
        return maxRfidNo;
    }
    
    public void setMaxRfidNo(String maxRfidNo)
    {
        this.maxRfidNo = maxRfidNo;
    }
    
    public String getMemberNo()
    {
        return memberNo;
    }
    
    public void setMemberNo(String memberNo)
    {
        this.memberNo = memberNo;
    }
    
    public String getMemberName()
    {
        return memberName;
    }
    
    public void setMemberName(String memberName)
    {
        this.memberName = memberName;
    }
    
    public LinkStateEnum getType()
    {
        return type;
    }
    
    public void setType(LinkStateEnum type)
    {
        this.type = type;
    }
    
    public String getGlassBatchNo()
    {
        return glassBatchNo;
    }
    
    public void setGlassBatchNo(String glassBatchNo)
    {
        this.glassBatchNo = glassBatchNo;
    }
    
    public String getProfileBatchNo()
    {
        return profileBatchNo;
    }
    
    public void setProfileBatchNo(String profileBatchNo)
    {
        this.profileBatchNo = profileBatchNo;
    }
    
    public Date getApplyDate()
    {
        return applyDate;
    }
    
    public void setApplyDate(Date applyDate)
    {
        this.applyDate = applyDate;
    }
    
    public String getReplenishRfid()
    {
        return replenishRfid;
    }
    
    public void setReplenishRfid(String replenishRfid)
    {
        this.replenishRfid = replenishRfid;
    }
    
    public String getContractNo()
    {
        return contractNo;
    }
    
    public void setContractNo(String contractNo)
    {
        this.contractNo = contractNo;
    }
    
    public String getDateNo()
    {
        return dateNo;
    }
    
    public void setDateNo(String dateNo)
    {
        this.dateNo = dateNo;
    }
    
    public String getRfidNos()
    {
        return rfidNos;
    }
    
    public void setRfidNos(String rfidNos)
    {
        this.rfidNos = rfidNos;
    }
    
}
