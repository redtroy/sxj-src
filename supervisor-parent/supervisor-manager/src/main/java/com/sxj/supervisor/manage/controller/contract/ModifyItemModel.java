package com.sxj.supervisor.manage.controller.contract;

import java.io.Serializable;

public class ModifyItemModel  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7012420127414982616L;

	/**
	 * 变更ID
	 */
	private  String id;
	/**
	 * 变更状态
	 */
	private String updateState;
	/**
	 * 变更类型(0合同1变更)
	 */
	private String type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUpdateState() {
		return updateState;
	}

	public void setUpdateState(String updateState) {
		this.updateState = updateState;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
