package com.sxj.finance.entity.member;

import java.io.Serializable;

import com.sxj.finance.dao.member.IMemberDao;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;

/**
 * 信用信息
 * 
 * @author dujinxin
 *
 */
@Entity(mapper = IMemberDao.class)
@Table(name = "M_MEMBER_CREDITINFO")
public class CreditInfoEntity extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6827970324466184681L;

	private String id;

	/**
	 * 
	 */
	private String memberNo;
	/**
	 * 未结清贷款笔数
	 */
	private Integer unLoan;
	/**
	 * 未结清贷款总额
	 */
	private Double unLoanAmount;
	/**
	 * 有无贷款逾期记录
	 */
	private Boolean isOverdue;
	/**
	 * 逾期贷款期数
	 */
	private Integer overdueNum;
	/**
	 * 单笔最高逾期金额
	 */
	private Double maxOverdueAmount;
	/**
	 * 最长逾期月数
	 */
	private Integer maxOverdueMonth;
	/**
	 * 信用卡逾期记录
	 */
	private Integer cardOverdue;
	/**
	 * 单笔最高逾期金额
	 */
	private Double cardMaxOverdueAmount;
	/**
	 * 对外担保总额
	 */
	private Double guarantee;
	/**
	 * 未结清贷款到期明细
	 */
	private String loanItem;
	/**
	 * 对外担保到期明细
	 */
	private String guaranteeItem;

}
