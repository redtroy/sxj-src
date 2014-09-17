package com.sxj.supervisor.enu.rfid.window;
/**
 * 标签进度
 * @author Ann
 *
 */
public enum LabelProgressEnum {
	unfilled("标签未发货", 0),shipped("标签已发货", 1),hasReceipt("标签已收货", 2),installed("门窗已安装", 3),hasQuality("门窗已质检", 4);

	// 成员变量
	private Integer id;

	private String name;

	private LabelProgressEnum(String name, Integer id) {
		this.name = name;
		this.id = id;
	}

	// 普通方法
	public static String getName(Integer id) {
		for (LabelProgressEnum c : LabelProgressEnum.values()) {
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
