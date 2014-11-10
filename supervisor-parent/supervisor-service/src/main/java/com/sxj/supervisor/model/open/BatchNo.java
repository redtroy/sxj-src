package com.sxj.supervisor.model.open;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;

public class BatchNo  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 200787019183580808L;

	private String batchNo;

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	
}
