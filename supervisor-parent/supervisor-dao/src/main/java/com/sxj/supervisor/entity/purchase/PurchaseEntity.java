package com.sxj.supervisor.entity.purchase;

import java.io.Serializable;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.dao.purchase.IPurchaseDao;

/**
 * 不同采购类型的最终采购量
 * 
 * @author anshaoshuai
 *
 */
public class PurchaseEntity extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8254497307870149846L;

	@Column(name = "C_ID")
	private String cId;

	/**
	 * 普通玻璃的最终采购量
	 */
	@Column(name = "ORDINARY_GLASS")
	private String ordinaryGlass;

	/**
	 * 深加工玻璃的最终采购量
	 */
	@Column(name = "DEEP_GLASS")
	private String deepGlass;

	/**
	 * 型材的最终采购的量
	 */
	@Column(name = "PROFILES")
	private String profiles;

	/**
	 * 配件的最终采购的量
	 */
	@Column(name = "FITTING")
	private String fitting;

	public String getcId() {
		return cId;
	}

	public void setcId(String cId) {
		this.cId = cId;
	}

	public String getOrdinaryGlass() {
		return ordinaryGlass;
	}

	public void setOrdinaryGlass(String ordinaryGlass) {
		this.ordinaryGlass = ordinaryGlass;
	}

	public String getDeepGlass() {
		return deepGlass;
	}

	public void setDeepGlass(String deepGlass) {
		this.deepGlass = deepGlass;
	}

	public String getProfiles() {
		return profiles;
	}

	public void setProfiles(String profiles) {
		this.profiles = profiles;
	}

	public String getFitting() {
		return fitting;
	}

	public void setFitting(String fitting) {
		this.fitting = fitting;
	}

	@Override
	public String toString() {
		return "PurchaseEntity [cId=" + cId + ", ordinaryGlass="
				+ ordinaryGlass + ", deepGlass=" + deepGlass + ", profiles="
				+ profiles + ", fitting=" + fitting + "]";
	}

}
