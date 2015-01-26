package com.sxj.supervisor.entity.rfid.window;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.spring.modules.mapper.JsonMapper;
import com.sxj.supervisor.dao.rfid.window.IWindowRfidDao;
import com.sxj.supervisor.enu.rfid.RfidStateEnum;
import com.sxj.supervisor.enu.rfid.window.LabelProgressEnum;
import com.sxj.supervisor.enu.rfid.window.WindowTypeEnum;
import com.sxj.supervisor.model.rfid.RfidLog;
import com.sxj.util.common.DateTimeUtils;
import com.sxj.util.common.StringUtils;

/**
 * 门窗RFID管理
 * 
 * @author dujinxin
 *
 */
@Entity(mapper = IWindowRfidDao.class)
@Table(name = "R_WINDOW_RFID")
public class WindowRfidEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1734629786698988694L;
    
    /**
     * ID
     */
    @Id(column = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    /**
     * RFID编号
     */
    @Column(name = "RFID_NO")
    private String rfidNo;
    
    @Column(name = "GENERATE_KEY")
    private Long generateKey;
    
    /**
     * 申请单号
     */
    @Column(name = "APPLY_NO")
    private String applyNo;
    
    /**
     * 采购单号
     */
    @Column(name = "PURCHASE_NO")
    private String purchaseNo;
    
    /**
     * 招标合同号
     */
    @Column(name = "CONTRACT_NO")
    private String contractNo;
    
    /**
     * 申请会员号
     */
    @Column(name = "MEMBER_NO")
    private String memberNo;
    
    /**
     * 申请会员名称
     */
    @Column(name = "MEMBER_NAME")
    private String memberName;
    
    /**
     * 窗型代号
     */
    @Column(name = "WINDOW_TYPE")
    private WindowTypeEnum windowType;
    
    /**
     * 玻璃RFID
     */
    @Column(name = "GLASS_RFID")
    private String glassRfid;
    
    /**
     * 型材RFID
     */
    @Column(name = "PROFILE_RFID")
    private String profileRfid;
    
    /**
     * 导入日期
     */
    @Column(name = "IMPORT_DATE")
    private Date importDate;
    
    /**
     * 关联补损单
     */
    @Column(name = "REPLENISH_NO")
    private String replenishNo;
    
    /**
     * RFID状态
     */
    @Column(name = "RFID_STATE")
    private RfidStateEnum rfidState;
    
    /**
     * 进度状态
     */
    @Column(name = "PROGRESS_STATE")
    private LabelProgressEnum progressState;
    
    /**
     * 执行日志
     */
    @Column(name = "LOG")
    private String log;
    
    /**
     * gid
     */
    @Column(name = "GID")
    private String gid;
    
    @Column(name = "ADDRESS")
    private String address;
    
    private List<RfidLog> logList = new ArrayList<RfidLog>();
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getRfidNo()
    {
        return rfidNo;
    }
    
    public void setRfidNo(String rfidNo)
    {
        this.rfidNo = rfidNo;
    }
    
    public String getPurchaseNo()
    {
        return purchaseNo;
    }
    
    public void setPurchaseNo(String purchaseNo)
    {
        this.purchaseNo = purchaseNo;
    }
    
    public String getContractNo()
    {
        return contractNo;
    }
    
    public void setContractNo(String contractNo)
    {
        this.contractNo = contractNo;
    }
    
    public WindowTypeEnum getWindowType()
    {
        return windowType;
    }
    
    public void setWindowType(WindowTypeEnum windowType)
    {
        this.windowType = windowType;
    }
    
    public String getGlassRfid()
    {
        return glassRfid;
    }
    
    public void setGlassRfid(String glassRfid)
    {
        this.glassRfid = glassRfid;
    }
    
    public String getProfileRfid()
    {
        return profileRfid;
    }
    
    public void setProfileRfid(String profileRfid)
    {
        this.profileRfid = profileRfid;
    }
    
    public Date getImportDate()
    {
        return importDate;
    }
    
    public void setImportDate(Date importDate)
    {
        this.importDate = importDate;
    }
    
    public String getReplenishNo()
    {
        return replenishNo;
    }
    
    public void setReplenishNo(String replenishNo)
    {
        this.replenishNo = replenishNo;
    }
    
    public RfidStateEnum getRfidState()
    {
        return rfidState;
    }
    
    public void setRfidState(RfidStateEnum rfidState)
    {
        this.rfidState = rfidState;
        if (RfidStateEnum.DAMAGED.equals(getRfidState()))
        {
            RfidLog rfidLog = new RfidLog();
            rfidLog.setId(getRfidState().getId());
            rfidLog.setState(getRfidState().getName());
            rfidLog.setDate(DateTimeUtils.getDateTime());
            setLogList(rfidLog);
        }
    }
    
    public String getLog()
    {
        return log;
    }
    
    public void setLog(String log)
    {
        this.log = log;
    }
    
    public Long getGenerateKey()
    {
        return generateKey;
    }
    
    public void setGenerateKey(Long generateKey)
    {
        this.generateKey = generateKey;
    }
    
    public LabelProgressEnum getProgressState()
    {
        return progressState;
    }
    
    public void setProgressState(LabelProgressEnum progressState)
    {
        this.progressState = progressState;
        if (getProgressState() != null)
        {
            RfidLog rfidLog = new RfidLog();
            rfidLog.setId(getProgressState().getId());
            rfidLog.setState(getProgressState().getName());
            rfidLog.setDate(DateTimeUtils.getDateTime());
            setLogList(rfidLog);
        }
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
    
    public String getApplyNo()
    {
        return applyNo;
    }
    
    public void setApplyNo(String applyNo)
    {
        this.applyNo = applyNo;
    }
    
    public List<RfidLog> getLogList()
    {
        if (StringUtils.isEmpty(getLog()))
        {
            return logList;
        }
        else
        {
            logList = JsonMapper.nonEmptyMapper().fromJson(getLog(),
                    new JsonMapper().contructCollectionType(ArrayList.class,
                            RfidLog.class));
            if (logList == null)
            {
                logList = new ArrayList<RfidLog>();
            }
            return logList;
        }
    }
    
    public void removeLog(RfidLog log)
    {
        if (getLogList() != null)
        {
            for (Iterator<RfidLog> iterator = logList.iterator(); iterator.hasNext();)
            {
                RfidLog rfidLog = iterator.next();
                if (rfidLog == null)
                {
                    continue;
                }
                if (rfidLog.getId() == log.getId()
                        && log.getState().equals(rfidLog.getState()))
                {
                    iterator.remove();
                }
            }
            
        }
        String json = JsonMapper.nonEmptyMapper().toJson(logList);
        setLog(json);
    }
    
    private void setLogList(RfidLog log)
    {
        getLogList().add(log);
        String json = JsonMapper.nonEmptyMapper().toJson(logList);
        setLog(json);
        
    }
    
    public String getGid()
    {
        return gid;
    }
    
    public void setGid(String gid)
    {
        this.gid = gid;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtils.trimToNull(this.getId()));
        sb.append("|");
        sb.append(StringUtils.trimToNull(this.getRfidNo()));
        sb.append("|");
        sb.append(StringUtils.trimToNull(this.getGenerateKey()));
        sb.append("|");
        sb.append(StringUtils.trimToNull(this.getMemberName()));
        sb.append("|");
        sb.append(StringUtils.trimToNull(this.getMemberNo()));
        sb.append("|");
        sb.append(StringUtils.trimToNull(this.getApplyNo()));
        sb.append("|");
        if (this.getPurchaseNo() != null)
        {
            sb.append(StringUtils.trimToNull(this.getPurchaseNo()));
        }
        sb.append("|");
        sb.append(StringUtils.trimToNull(this.getContractNo()));
        sb.append("|");
        if (this.getWindowType() != null)
        {
            sb.append(this.getWindowType().getId());
        }
        sb.append("|");
        sb.append(StringUtils.trimToNull(this.getGlassRfid()));
        sb.append("|");
        sb.append(StringUtils.trimToNull(this.getProfileRfid()));
        sb.append("|");
        if (this.getImportDate() != null)
        {
            sb.append(DateTimeUtils.formatFullDate(this.getImportDate()));
        }
        sb.append("|");
        sb.append(StringUtils.trimToNull(this.getReplenishNo()));
        sb.append("|");
        if (this.getRfidState() != null)
        {
            sb.append(this.getRfidState().getId());
        }
        sb.append("|");
        sb.append(StringUtils.trimToNull(this.getLog()));
        sb.append("|");
        if (this.getProgressState() != null)
        {
            sb.append(this.getProgressState().getId());
        }
        sb.append("|");
        sb.append(StringUtils.trimToNull(this.getGid()));
        return sb.toString();
    }
    
    public String getAddress()
    {
        return address;
    }
    
    public void setAddress(String address)
    {
        this.address = address;
    }
    
}
