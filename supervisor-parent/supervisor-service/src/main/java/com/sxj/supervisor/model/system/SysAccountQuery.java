package com.sxj.supervisor.model.system;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;

public class SysAccountQuery extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5244394958371135819L;

	private String id;

	private String name;

	private String account;

	private String functionId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

}
