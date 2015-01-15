package com.sxj.supervisor.enu.rfid;

/**
 * RFID状态
 * @author Ann
 *
 */
public enum RfidStateEnum {
	UN_USED("未使用", 0), USED("已使用", 1), DAMAGED("已破损", 2),DISABLE("已停用", 3),DELETE("已删除", 4);

	// 成员变量
	private Integer id;

	private String name;

	private RfidStateEnum(String name, Integer id) {
		this.name = name;
		this.id = id;
	}

	// 普通方法
	public static String getName(Integer id) {
		for (RfidStateEnum c : RfidStateEnum.values()) {
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
