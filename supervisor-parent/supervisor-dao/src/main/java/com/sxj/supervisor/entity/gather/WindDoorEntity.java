package com.sxj.supervisor.entity.gather;

import java.io.Serializable;
import java.util.Date;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.dao.gather.WindDoorDao;

@Entity(mapper = WindDoorDao.class)
@Table(name = "M_WIND_DOOR")
public class WindDoorEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1925746437498634553L;
    
    /**
     * 主键标识
     **/
    @Id(column = "ID")
    private String id;
    
    /**
     * 标段分类
     */
    @Column(name = "BDFL")
    private String bdfl;
    
    /**
     * 发布单位
     */
    @Column(name = "PUBLISH_FIRM")
    private String publishFirm;
    
    /**
     * 项目名称
     */
    @Column(name = "XMMC")
    private String xmmc;
    
    /**
     * 区域
     */
    @Column(name = "QY")
    private String qy;
    
    /**
     * 截至日期
     */
    @Column(name = "JZRQ")
    private String jzrq;
    
    /**
     * 内容路径
     */
    @Column(name = "FILE_PATH")
    private String filePath;
    
    /**
     * word备份路径
     */
    @Column(name = "FILE_PATH_BACK")
    private String filePathBack;
    
    /**
     * 附件地址
     */
    @Column(name = "ADJUNCT_PATH")
    private String adjunctPath;
    
    /**
     * 图片路径
     */
    @Column(name = "GIF_PATH")
    private String gifPath;
    
    /**
     * 抓去数据时间
     */
    @Column(name = "NOW_DATE")
    private Date nowDate;
    
    /**
     * 抓去最新数据的标记
     */
    @Column(name = "FLAG")
    private Integer flag;
    
    /**
     * 状态：0代表生效，1代表失效
     */
    @Column(name = "STATE")
    private Integer state;
    
    public Date getNowDate()
    {
        return nowDate;
    }
    
    public void setNowDate(Date nowDate)
    {
        this.nowDate = nowDate;
    }
    
    public String getFilePath()
    {
        return filePath;
    }
    
    public String getPublishFirm()
    {
        return publishFirm;
    }
    
    public void setPublishFirm(String publishFirm)
    {
        this.publishFirm = publishFirm;
    }
    
    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }
    
    public String getGifPath()
    {
        return gifPath;
    }
    
    public void setGifPath(String gifPath)
    {
        this.gifPath = gifPath;
    }
    
    public String getFilePathBack()
    {
        return filePathBack;
    }
    
    public void setFilePathBack(String filePathBack)
    {
        this.filePathBack = filePathBack;
    }
    
    public String getAdjunctPath()
    {
        return adjunctPath;
    }
    
    public void setAdjunctPath(String adjunctPath)
    {
        this.adjunctPath = adjunctPath;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getBdfl()
    {
        return bdfl;
    }
    
    public void setBdfl(String bdfl)
    {
        this.bdfl = bdfl;
    }
    
    public String getXmmc()
    {
        return xmmc;
    }
    
    public void setXmmc(String xmmc)
    {
        this.xmmc = xmmc;
    }
    
    public String getQy()
    {
        return qy;
    }
    
    public void setQy(String qy)
    {
        this.qy = qy;
    }
    
    public String getJzrq()
    {
        return jzrq;
    }
    
    public void setJzrq(String jzrq)
    {
        this.jzrq = jzrq;
    }
    
    public Integer getFlag()
    {
        return flag;
    }
    
    public void setFlag(Integer flag)
    {
        this.flag = flag;
    }
    
    public Integer getState()
    {
        return state;
    }
    
    public void setState(Integer state)
    {
        this.state = state;
    }
    
}
