package com.sxj.supervisor.enu.contract;

public enum ContractWindowTypeEnum {
	type0("C120 120X60", 0),type1("C150 150X80", 1),type2("C180 180X50", 2);

	// 成员变量
	private Integer id;

	private String name;

	private ContractWindowTypeEnum(String name, Integer id) {
		this.name = name;
		this.id = id;
	}

	// 普通方法
	public static String getName(Integer id) {
		for (ContractWindowTypeEnum c : ContractWindowTypeEnum.values()) {
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
