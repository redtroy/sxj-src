package com.sxj.supervisor.enu.record;
/**
 * (前台)备案状态
 * @author Administrator
 *
 */
public enum RecordConfirmStateEnum {
	ACCEPTED("受理中", 0),UNCONFIRMED ("未确认", 1),CONFIRMEDA ("甲方确认", 2),CONFIRMEDB("乙方确认", 3),HASRECORD("已备案", 4);
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
