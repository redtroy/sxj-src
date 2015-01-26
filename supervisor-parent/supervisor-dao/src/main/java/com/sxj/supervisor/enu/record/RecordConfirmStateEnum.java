package com.sxj.supervisor.enu.record;

/**
 * 备案审核状态
 * 
 * @author Administrator
 *
 */
public enum RecordConfirmStateEnum {
	accepted("受理中", 0), unconfirmed("未确认", 1), confirmedA("甲方确认", 2), confirmedB(
			"乙方确认", 3), hasRecord("已备案", 4);
	// 成员变量
	private Integer id;

	private String name;

	private RecordConfirmStateEnum(String name, Integer id) {
		this.name = name;
		this.id = id;
	}

	// 普通方法
	public static String getName(Integer id) {
		for (RecordConfirmStateEnum c : RecordConfirmStateEnum.values()) {
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
