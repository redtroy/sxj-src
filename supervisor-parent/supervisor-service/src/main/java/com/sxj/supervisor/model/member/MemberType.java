package com.sxj.supervisor.model.member;
/**
 * 会员类型
 * @author Administrator
 *
 */
public enum MemberType {
	DAWP("门窗厂", 0), glassFactory ("玻璃厂", 1), genresFactory("类型厂", 2);

	// 成员变量
	private Integer id;

	private String name;

	private MemberType(String name, Integer id) {
		this.name = name;
		this.id = id;
	}

	// 普通方法
	public static String getName(Integer id) {
		for (MemberType c : MemberType.values()) {
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
