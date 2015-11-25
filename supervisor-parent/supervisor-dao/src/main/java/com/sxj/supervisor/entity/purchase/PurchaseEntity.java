package com.sxj.supervisor.entity.purchase;

import java.io.Serializable;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.dao.purchase.IPurchaseDao;

/**
 * 不同采购类型的最终采购量
 * 
 * @author anshaoshuai
 *
 */
@Entity(mapper = IPurchaseDao.class)
@Table(name = "M_PURCHASE")
public class PurchaseEntity extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8254497307870149846L;

	@Id(column = "C_ID")
	private String cId;

	/**
	 * 普通玻璃的最终采购量
	 */
	@Column(name = "ORDINARY_GLASS")
	private Integer ordinaryGlass;

	/**
	 * 深加工玻璃的最终采购量
	 */
	@Column(name = "DEEP_GLASS")
	private Integer deepGlass;

	/**
	 * 型材的最终采购的量
	 */
	@Column(name = "PROFILES")
	private Integer profiles;

	/**
	 * 配件的最终采购的量
	 */
	@Column(name = "FITTING")
	private Integer fitting;

	/**
	 * 截止某个时间节点到现在所增加的普通玻璃的量
	 */
	@Column(name = "ORDINARY_INCREASE")
	private Integer ordinaryIncrease;

	/**
	 * 截止某个时间节点到现在所减少的普通玻璃的量
	 */
	@Column(name = "ORDINARY_REDUCE")
	private Integer ordinaryReduce;

	/**
	 * 截止某个时间节点到现在所增加的深加工玻璃的量
	 */
	@Column(name = "DEEP_INCREASE")
	private Integer deepIncrease;

	/**
	 * 截止某个时间节点到现在所减少的深加工玻璃的量
	 */
	@Column(name = "DEEP_REDUCE")
	private Integer deepReduce;

	/**
	 * 截止某个时间节点到现在所增加的型材的量
	 */
	@Column(name = "PROFILES_INCREASE")
	private Integer profilesIncrease;

	/**
	 * 截止某个时间节点到现在所减少的型材的量
	 */
	@Column(name = "PROFILES_REDUCE")
	private Integer profilesReduce;

	/**
	 * 截止某个时间节点到现在所增加的配件的量
	 */
	@Column(name = "FITTING_INCREASE")
	private Integer fittingIncrease;

	/**
	 * 截止某个时间节点到现在所减少的配件的量
	 */
	@Column(name = "FITTING_REDUCE")
	private Integer fittingReduce;

	public String getcId() {
		return cId;
	}

	public void setcId(String cId) {
		this.cId = cId;
	}

	public Integer getOrdinaryGlass() {
		return ordinaryGlass;
	}

	public void setOrdinaryGlass(Integer ordinaryGlass) {
		this.ordinaryGlass = ordinaryGlass;
	}

	public Integer getDeepGlass() {
		return deepGlass;
	}

	public void setDeepGlass(Integer deepGlass) {
		this.deepGlass = deepGlass;
	}

	public Integer getProfiles() {
		return profiles;
	}

	public void setProfiles(Integer profiles) {
		this.profiles = profiles;
	}

	public Integer getFitting() {
		return fitting;
	}

	public void setFitting(Integer fitting) {
		this.fitting = fitting;
	}

	public Integer getOrdinaryIncrease() {
		return ordinaryIncrease;
	}

	public void setOrdinaryIncrease(Integer ordinaryIncrease) {
		this.ordinaryIncrease = ordinaryIncrease;
	}

	public Integer getOrdinaryReduce() {
		return ordinaryReduce;
	}

	public void setOrdinaryReduce(Integer ordinaryReduce) {
		this.ordinaryReduce = ordinaryReduce;
	}

	public Integer getDeepIncrease() {
		return deepIncrease;
	}

	public void setDeepIncrease(Integer deepIncrease) {
		this.deepIncrease = deepIncrease;
	}

	public Integer getDeepReduce() {
		return deepReduce;
	}

	public void setDeepReduce(Integer deepReduce) {
		this.deepReduce = deepReduce;
	}

	public Integer getProfilesIncrease() {
		return profilesIncrease;
	}

	public void setProfilesIncrease(Integer profilesIncrease) {
		this.profilesIncrease = profilesIncrease;
	}

	public Integer getProfilesReduce() {
		return profilesReduce;
	}

	public void setProfilesReduce(Integer profilesReduce) {
		this.profilesReduce = profilesReduce;
	}

	public Integer getFittingIncrease() {
		return fittingIncrease;
	}

	public void setFittingIncrease(Integer fittingIncrease) {
		this.fittingIncrease = fittingIncrease;
	}

	public Integer getFittingReduce() {
		return fittingReduce;
	}

	public void setFittingReduce(Integer fittingReduce) {
		this.fittingReduce = fittingReduce;
	}

	@Override
	public String toString() {
		return "PurchaseEntity [cId=" + cId + ", ordinaryGlass="
				+ ordinaryGlass + ", deepGlass=" + deepGlass + ", profiles="
				+ profiles + ", fitting=" + fitting + "]";
	}

}
