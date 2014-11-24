package com.sxj.supervisor.enu.contract;

public enum PayContentStateEnum {
	deposit("定金", 0), payment("货款", 1);

	// 成员变量
	private Integer id;

	private String name;

	private PayContentStateEnum(String name, Integer id) {
		this.name = name;
		this.id = id;
	}

	// 普通方法
	public static String getName(Integer id) {
		for (PayContentStateEnum c : PayContentStateEnum.values()) {
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
