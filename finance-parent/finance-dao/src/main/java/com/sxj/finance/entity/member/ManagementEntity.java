package com.sxj.finance.entity.member;

import java.io.Serializable;

import com.sxj.finance.dao.member.IManagementDao;
import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;

/**
 * 经营状况
 * 
 * @author dujinxin
 *
 */
@Entity(mapper = IManagementDao.class)
@Table(name = "M_MEMBER_MANAGEMENT")
public class ManagementEntity extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3061532970612685325L;

	
	@Id(column = "ID")
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(name = "MEMBER_NO")
	private String memberNo;

	/**
	 * 上年度主营业务收入
	 */
	@Column(name = "MAIN_INCOME")
	private Double mainIncome;
	/**
	 * 上年度净利润
	 */
	@Column(name = "PRE_NET_PROFIT")
	private Double preNetProfit;
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
	/**
	 * 近3个月资金流入
	 */
	@Column(name = "IN_FLOW")
	private Double inFlow;
	/**
	 * 近3个月资金流出
	 */
	@Column(name = "OUT_FLOW")
	private Double outFlow;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public Double getMainIncome() {
		return mainIncome;
	}
	public void setMainIncome(Double mainIncome) {
		this.mainIncome = mainIncome;
	}
	public Double getPreNetProfit() {
		return preNetProfit;
	}
	public void setPreNetProfit(Double preNetProfit) {
		this.preNetProfit = preNetProfit;
	}
	public Double getSaleSum() {
		return saleSum;
	}
	public void setSaleSum(Double saleSum) {
		this.saleSum = saleSum;
	}
	public Double getNowNetProfit() {
		return nowNetProfit;
	}
	public void setNowNetProfit(Double nowNetProfit) {
		this.nowNetProfit = nowNetProfit;
	}
	public Double getInFlow() {
		return inFlow;
	}
	public void setInFlow(Double inFlow) {
		this.inFlow = inFlow;
	}
	public Double getOutFlow() {
		return outFlow;
	}
	public void setOutFlow(Double outFlow) {
		this.outFlow = outFlow;
	}

}
