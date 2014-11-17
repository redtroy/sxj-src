package com.sxj.supervisor.enu.rfid.window;

public enum WindowTypeEnum {
	type0("C120 60B*120H", 0), type1("C120 120B*120H", 1), type2(
			"C120 150B*120H", 2), type3("C150 60B*150H", 3), type4(
			"C150 120B*150H", 4), type5("C150 150B*150H", 5), type6(
			"C150 180B*150H", 6), type7("C180 60B*180H", 7), type8(
			"C180 150B*180H", 8), type9("C180 180B*180H", 9);

	// 成员变量
	private Integer id;

	private String name;

	private WindowTypeEnum(String name, Integer id) {
		this.name = name;
		this.id = id;
	}

	// 普通方法
	public static String getName(Integer id) {
		for (WindowTypeEnum c : WindowTypeEnum.values()) {
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
