package com.sxj.supervisor.entity.member;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;
/**
 * 子账户关联系统功能
 * @author AnShaoshuai
 *
 */
public class RoleEntity extends Pagable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3954228102127813375L;

	/**
	 * 主键标识
	**/
	private String id;
	
	/**
	 * 系统功能ID
	**/
	private String functionId;
	
	/**
	 * 子账户ID
	**/
	private String accountId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
}
