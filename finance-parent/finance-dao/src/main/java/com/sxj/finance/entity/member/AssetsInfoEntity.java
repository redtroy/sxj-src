package com.sxj.finance.entity.member;

import java.io.Serializable;

import com.sxj.finance.dao.member.IMemberDao;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;

/**
 * 资产信息
 * 
 * @author dujinxin
 *
 */
@Entity(mapper = IMemberDao.class)
@Table(name = "M_MEMBER_ASSETSINFO")
public class AssetsInfoEntity extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7535349859280866177L;

	private String id;

	private String memberNo;

	/**
	 * 资产总额
	 */
	private Double assetsSum;

	/**
	 * 负债总额
	 */
	private Double liabilities;

	/**
	 * 固定资产净值
	 */
	private Double fixedAssets;

	/**
	 * 应收账款总额
	 */
	private Double receviceSum;

	/**
	 * 净资产
	 */
	private Double netAssets;

	/**
	 * 资产负债率
	 */
	private Double assetRatio;

	/**
	 * 其他资产情况备注
	 */
	private String remark;

}
