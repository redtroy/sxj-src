package com.sxj.finance.entity.member;

import java.io.Serializable;

import com.sxj.finance.dao.member.IAssetsInfoDao;
import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;

/**
 * 资产信息
 * 
 * @author dujinxin
 *
 */
@Entity(mapper = IAssetsInfoDao.class)
@Table(name = "M_MEMBER_ASSETSINFO")
public class AssetsInfoEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -7535349859280866177L;
    
    @Id(column = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "MEMBER_NO")
    private String memberNo;
    
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
     * 固定资产净值
     */
    @Column(name = "FIXED_ASSETS")
    private Double fixedAssets;
    
    /**
     * 应收账款总额
     */
    @Column(name = "RECEVICE_SUM")
    private Double receviceSum;
    
    /**
     * 净资产
     */
    @Column(name = "NET_ASSETS")
    private Double netAssets;
    
    /**
     * 资产负债率
     */
    @Column(name = "ASSET_RATIO")
    private Double assetRatio;
    
    /**
     * 其他资产情况备注
     */
    @Column(name = "REMARK")
    private String remark;
    
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
    
    public Double getReceviceSum()
    {
        return receviceSum;
    }
    
    public void setReceviceSum(Double receviceSum)
    {
        this.receviceSum = receviceSum;
    }
    
    public Double getNetAssets()
    {
        return netAssets;
    }
    
    public void setNetAssets(Double netAssets)
    {
        this.netAssets = netAssets;
    }
    
    public Double getAssetRatio()
    {
        return assetRatio;
    }
    
    public void setAssetRatio(Double assetRatio)
    {
        this.assetRatio = assetRatio;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
}
