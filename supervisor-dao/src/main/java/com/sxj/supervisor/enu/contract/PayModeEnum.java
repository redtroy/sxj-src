package com.sxj.supervisor.enu.contract;

public enum PayModeEnum {
	MDDE1("待支付", "未付款"), MODE2_0("融资受理中", "支付中"), MODE2_1("融资已搁置", "支付中"), MODE2_2(
			"融资已放款", "支付中"), MDDE3("现金支付", ""), MDDE4("融资支付", "");
	private String nameA;

	private String nameB;

	private PayModeEnum(String nameA, String nameB) {
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
