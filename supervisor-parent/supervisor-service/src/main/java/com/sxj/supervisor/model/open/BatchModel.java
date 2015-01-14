package com.sxj.supervisor.model.open;

import java.io.Serializable;

public class BatchModel  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3269003516339562334L;

	private Contract contract;
	
	private Bacth batchList;

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public Bacth getBatchList() {
		return batchList;
	}

	public void setBatchList(Bacth batchList) {
		this.batchList = batchList;
	}
	
	
}
