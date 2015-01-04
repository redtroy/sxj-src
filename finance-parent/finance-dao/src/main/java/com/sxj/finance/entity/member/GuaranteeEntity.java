package com.sxj.finance.entity.member;

import java.io.Serializable;

import com.sxj.finance.dao.member.IMemberDao;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;

/**
 * 担保信息
 * 
 * @author dujinxin
 *
 */
@Entity(mapper = IMemberDao.class)
@Table(name = "M_MEMBER_GUARANTEE")
public class GuaranteeEntity extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1571744639433883689L;

	private String id;

	private String memberNo;

	/**
	 * 产权人
	 */
	private String property;
	/**
	 * 房屋座落
	 */
	private String houseAddress;
	/**
	 * 建筑面积
	 */
	private String houseArea;
	/**
	 * 房价估算
	 */
	private Double houseAmount;
	/**
	 * 担保企业
	 */
	private String enterprise;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 企业法人
	 */
	private String legal;
	/**
	 * 注册资本
	 */
	private Double registered;

	/**
	 * 经营范围
	 */
	private String manageRange;

	/**
	 * 资产总额
	 */
	private Double assetsSum;

	/**
	 * 负债总额
	 */
	private Double liabilities;

	/**
	 * 固定资产总额
	 */
	private Double fixedAssets;

	/**
	 * 资产负债率
	 */
	private Double assetRatio;

	/**
	 * 本年度累计销售额
	 */
	private Double saleSum;
	/**
	 * 本年度累计净利润
	 */
	private Double nowNetProfit;

}
