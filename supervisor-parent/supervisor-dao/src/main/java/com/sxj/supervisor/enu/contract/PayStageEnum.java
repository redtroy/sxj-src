package com.sxj.supervisor.enu.contract;

public enum PayStageEnum {
	STAGE1("未付款", "等待付款"), STAGE2("乙方确认中", "确认付款"), STAGE3("已完成支付", "已完成支付");
	private String nameA;

	private String nameB;

	private PayStageEnum(String nameA, String nameB) {
		this.nameA = nameA;
		this.nameB = nameB;
	}

	public String getNameA() {
		return nameA;
	}

	public void setNameA(String nameA) {
		this.nameA = nameA;
	}

	public String getNameB() {
		return nameB;
	}

	public void setNameB(String nameB) {
		this.nameB = nameB;
	}

}
