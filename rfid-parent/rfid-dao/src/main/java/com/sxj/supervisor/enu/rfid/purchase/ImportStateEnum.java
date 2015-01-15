package com.sxj.supervisor.enu.rfid.purchase;
/**
 * 导入状态
 * @author Ann
 *
 */
public enum ImportStateEnum {
	NOT_IMPORTED("未导入", 0),IMPORTED("已导入",1) ;

	// 成员变量
	private Integer id;

	private String name;

	private ImportStateEnum(String name, Integer id) {
		this.name = name;
		this.id = id;
	}

	// 普通方法
	public static String getName(Integer id) {
		for (ImportStateEnum c : ImportStateEnum.values()) {
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
