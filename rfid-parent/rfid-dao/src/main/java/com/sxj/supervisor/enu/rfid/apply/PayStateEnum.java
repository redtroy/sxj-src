package com.sxj.supervisor.enu.rfid.apply;

public enum PayStateEnum {
	PAYMENT("已收款", 0), NOT_PAYMENT("未收款", 1);

	// 成员变量
	private Integer id;

	private String name;

	private PayStateEnum(String name, Integer id) {
		this.name = name;
		this.id = id;
	}

	// 普通方法
	public static String getName(Integer id) {
		for (PayStateEnum c : PayStateEnum.values()) {
			if (c.getId() == id) {
				return c.name;
			}
		}
		return null;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
