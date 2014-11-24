package com.sxj.supervisor.enu.contract;

public enum PayModeEnum {
	cash("现金支付", 0), financing("融资支付", 1);

	// 成员变量
	private Integer id;

	private String name;

	private PayModeEnum(String name, Integer id) {
		this.name = name;
		this.id = id;
	}

	// 普通方法
	public static String getName(Integer id) {
		for (PayModeEnum c : PayModeEnum.values()) {
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
