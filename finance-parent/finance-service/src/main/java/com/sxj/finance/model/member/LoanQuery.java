package com.sxj.finance.model.member;

import java.io.Serializable;

import com.sxj.finance.entity.member.AssetsInfoEntity;
import com.sxj.finance.entity.member.CreditInfoEntity;
import com.sxj.finance.entity.member.GuaranteeEntity;
import com.sxj.finance.entity.member.ManagementEntity;
import com.sxj.finance.entity.member.MemberInfoEntity;
/**
 * 贷款申报表Model
 * 
 * @author Ann
 *
 */
public class LoanQuery  implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -6910916929086257361L;

	/**
	 * 基本信息
	 */
	private MemberInfoEntity memberInfo;

	/**
	 * 资产情况
	 */
	private AssetsInfoEntity assetsInfo;

	/**
	 * 经营情况
	 */
	private ManagementEntity management;

	/**
	 * 信用记录
	 */
	private CreditInfoEntity creditInfo;

	/**
	 * 担保情况
	 */
	private GuaranteeEntity guarantee;

	public MemberInfoEntity getMemberInfo() {
		return memberInfo;
	}

	public void setMemberInfo(MemberInfoEntity memberInfo) {
		this.memberInfo = memberInfo;
	}

	public AssetsInfoEntity getAssetsInfo() {
		return assetsInfo;
	}

	public void setAssetsInfo(AssetsInfoEntity assetsInfo) {
		this.assetsInfo = assetsInfo;
	}

	public ManagementEntity getManagement() {
		return management;
	}

	public void setManagement(ManagementEntity management) {
		this.management = management;
	}

	public CreditInfoEntity getCreditInfo() {
		return creditInfo;
	}

	public void setCreditInfo(CreditInfoEntity creditInfo) {
		this.creditInfo = creditInfo;
	}

	public GuaranteeEntity getGuarantee() {
		return guarantee;
	}

	public void setGuarantee(GuaranteeEntity guarantee) {
		this.guarantee = guarantee;
	}

}
