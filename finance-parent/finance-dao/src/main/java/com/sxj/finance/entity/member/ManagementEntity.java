package com.sxj.finance.entity.member;

import java.io.Serializable;

import com.sxj.finance.dao.member.IMemberDao;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;

/**
 * 经营状况
 * 
 * @author dujinxin
 *
 */
@Entity(mapper = IMemberDao.class)
@Table(name = "M_MEMBER_MANAGEMENT")
public class ManagementEntity extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3061532970612685325L;

	private String id;

	private String memberNo;

	/**
	 * 上年度主营业务收入
	 */
	private Double mainIncome;
	/**
	 * 上年度净利润
	 */
	private Double preNetProfit;
	/**
	 * 本年度累计销售额
	 */
	private Double saleSum;
	/**
	 * 本年度累计净利润
	 */
	private Double nowNetProfit;
	/**
	 * 近3个月资金流入
	 */
	private Double inFlow;
	/**
	 * 近3个月资金流出
	 */
	private Double outFlow;

}
