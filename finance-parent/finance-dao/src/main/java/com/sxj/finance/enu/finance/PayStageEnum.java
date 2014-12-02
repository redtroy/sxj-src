package com.sxj.finance.enu.finance;

public enum PayStageEnum {
	Stage1("未支付", "支付中"), Stage2_0("融资受理中", "支付中"), Stage2_1("融资已放款", "支付中"), Stage2_2(
			"融资已搁置", "支付中"), Stage3("乙方确认中", "确认收款"), Stage4("已完成支付", "已完成支付");
	private String name_a;
	private String name_b;

	private PayStageEnum(String name_a, String name_b) {
		this.name_a = name_a;
		this.name_b = name_b;
	}

	public String getName_a() {
		return name_a;
	}

	public void setName_a(String name_a) {
		this.name_a = name_a;
	}

	public String getName_b() {
		return name_b;
	}

	public void setName_b(String name_b) {
		this.name_b = name_b;
	}

	public static void main(String... args) {
		PayStageEnum[] payState = PayStageEnum.values();
		for (int i = 0; i < payState.length; i++)
			System.out.println(payState[i].name_b);
		for (PayStageEnum payStageEnum : payState) {
			System.out.println(payStageEnum.name_b);
		}
	}
}
