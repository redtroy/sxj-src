package com.sxj.supervisor.enu.rfid.purchase;
/**
 * 收货状态
 * @author Ann
 *
 */
public enum DeliveryStateEnum {
	UN_FILLED("未发货", 0), SHIPPED("已发货", 1),RECEIVING("已收货", 2);

	// 成员变量
	private Integer id;

	private String name;

	private DeliveryStateEnum(String name, Integer id) {
		this.name = name;
		this.id = id;
	}

	// 普通方法
	public static String getName(Integer id) {
		for (DeliveryStateEnum c : DeliveryStateEnum.values()) {
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
