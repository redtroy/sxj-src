package com.sxj.supervisor.enu.rfid.apply;

public enum ReceiptStateEnum {
	shipments("发货中", 0), Goods_receipt("已收货", 1);

	// 成员变量
	private Integer id;

	private String name;

	private ReceiptStateEnum(String name, Integer id) {
		this.name = name;
		this.id = id;
	}

	// 普通方法
	public static String getName(Integer id) {
		for (ReceiptStateEnum c : ReceiptStateEnum.values()) {
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
