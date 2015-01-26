package com.sxj.finance.entity.member;

import java.io.Serializable;

import com.sxj.finance.dao.member.IGuaranteeDao;
import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;

/**
 * 担保信息
 * 
 * @author dujinxin
 *
 */
@Entity(mapper = IGuaranteeDao.class)
@Table(name = "M_MEMBER_GUARANTEE")
public class GuaranteeEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1571744639433883689L;
    
    @Id(column = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "MEMBER_NO")
    private String memberNo;
    
    /**
     * 产权人
     */
    @Column(name = "PROPERTY")
    private String property;
    
    /**
     * 房屋座落
     */
    @Column(name = "HOUSE_ADDRESS")
    private String houseAddress;
    
    /**
     * 建筑面积
     */
    @Column(name = "HOUSE_AREA")
    private String houseArea;
    
    /**
     * 房价估算
     */
    @Column(name = "HOUSE_AMOUNT")
    private Double houseAmount;
    
    /**
     * 担保企业
     */
    @Column(name = "ENTERPRISE")
    private String enterprise;
    
    /**
     * 地址
     */
    @Column(name = "ADDRESS")
    private String address;
    
    /**
     * 企业法人
     */
    @Column(name = "LEGAL")
    private String legal;
    
    /**
     * 注册资本
     */
    @Column(name = "REGISTERED")
    private Double registered;
    
    /**
     * 经营范围
     */
    @Column(name = "MANAGE_RANGE")
    private String manageRange;
    
    /**
     * 资产总额
     */
    @Column(name = "ASSETS_SUM")
    private Double assetsSum;
    
    /**
     * 负债总额
     */
    @Column(name = "LIABILITIES")
    private Double liabilities;
    
    /**
     * 固定资产总额
     */
    @Column(name = "FIXED_ASSETS")
    private Double fixedAssets;
    
    /**
     * 资产负债率
     */
    @Column(name = "ASSET_RATIO")
    private Double assetRatio;
    
    /**
     * 本年度累计销售额
     */
    @Column(name = "SALE_SUM")
    private Double saleSum;
    
    /**
     * 本年度累计净利润
     */
    @Column(name = "NOW_NET_PROFIT")
    private Double nowNetProfit;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getMemberNo()
    {
        return memberNo;
    }
    
    public void setMemberNo(String memberNo)
    {
        this.memberNo = memberNo;
    }
    
    public String getProperty()
    {
        return property;
    }
    
    public void setProperty(String property)
    {
        this.property = property;
    }
    
    public String getHouseAddress()
    {
        return houseAddress;
    }
    
    public void setHouseAddress(String houseAddress)
    {
        this.houseAddress = houseAddress;
    }
    
    public String getHouseArea()
    {
        return houseArea;
    }
    
    public void setHouseArea(String houseArea)
    {
        this.houseArea = houseArea;
    }
    
    public Double getHouseAmount()
    {
        return houseAmount;
    }
    
    public void setHouseAmount(Double houseAmount)
    {
        this.houseAmount = houseAmount;
    }
    
    public String getEnterprise()
    {
        return enterprise;
    }
    
    public void setEnterprise(String enterprise)
    {
        this.enterprise = enterprise;
    }
    
    public String getAddress()
    {
        return address;
    }
    
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    public String getLegal()
    {
        return legal;
    }
    
    public void setLegal(String legal)
    {
        this.legal = legal;
    }
    
    public Double getRegistered()
    {
        return registered;
    }
    
    public void setRegistered(Double registered)
    {
        this.registered = registered;
    }
    
    public String getManageRange()
    {
        return manageRange;
    }
    
    public void setManageRange(String manageRange)
    {
        this.manageRange = manageRange;
    }
    
    public Double getAssetsSum()
    {
        return assetsSum;
    }
    
    public void setAssetsSum(Double assetsSum)
    {
        this.assetsSum = assetsSum;
    }
    
    public Double getLiabilities()
    {
        return liabilities;
    }
    
    public void setLiabilities(Double liabilities)
    {
        this.liabilities = liabilities;
    }
    
    public Double getFixedAssets()
    {
        return fixedAssets;
    }
    
    public void setFixedAssets(Double fixedAssets)
    {
        this.fixedAssets = fixedAssets;
    }
    
    public Double getAssetRatio()
    {
        return assetRatio;
    }
    
    public void setAssetRatio(Double assetRatio)
    {
        this.assetRatio = assetRatio;
    }
    
    public Double getSaleSum()
    {
        return saleSum;
    }
    
    public void setSaleSum(Double saleSum)
    {
        this.saleSum = saleSum;
    }
    
    public Double getNowNetProfit()
    {
        return nowNetProfit;
    }
    
    public void setNowNetProfit(Double nowNetProfit)
    {
        this.nowNetProfit = nowNetProfit;
    }
    
}
