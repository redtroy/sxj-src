package com.sxj.supervisor.enu.rfid.ref;


public enum AssociationTypesEnum {
	APPLY("批次申请", 0), RFID_ADD("已使用", 1), CONTRACTOR_ADD("已破损", 2);

	// 成员变量
	private Integer id;

	private String name;

	private AssociationTypesEnum(String name, Integer id) {
		this.name = name;
		this.id = id;
	}

	// 普通方法
	public static String getName(Integer id) {
		for (AssociationTypesEnum c : AssociationTypesEnum.values()) {
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
