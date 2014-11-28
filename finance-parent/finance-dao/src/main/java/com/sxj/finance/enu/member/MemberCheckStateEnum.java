package com.sxj.finance.enu.member;

/**
 * 会员状态
 * 
 * @author Administrator
 *
 */
public enum MemberCheckStateEnum {

	unaudited("未审核", 0), unrecognized("未认证", 1), certified("已认证", 2);

	// 成员变量
	private Integer id;

	private String name;

	private MemberCheckStateEnum(String name, Integer id) {
		this.name = name;
		this.id = id;
	}

	// 普通方法
	public String getName(Integer id) {
		for (MemberCheckStateEnum c : MemberCheckStateEnum.values()) {
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

	public static MemberCheckStateEnum getEnum(Integer id) {
		for (MemberCheckStateEnum c : MemberCheckStateEnum.values()) {
			if (c.getId() == id) {
				return c;
			}
		}
		return null;
	}

	public static void main(String args[]) {
		Enum.valueOf(MemberCheckStateEnum.class, "1");
	}

}
