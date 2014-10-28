package com.sxj.supervisor.enu.member;

/**
 * 会员子帐号状态
 * 
 * @author Administrator
 *
 */
public enum AccountStatesEnum {
	stop("已冻结", 0), normal("未冻结", 1);

	// 成员变量
	private Integer id;

	private String name;

	private AccountStatesEnum(String name, Integer id) {
		this.name = name;
		this.id = id;
	}

	// 普通方法
	public static String getName(Integer id) {
		for (AccountStatesEnum c : AccountStatesEnum.values()) {
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

	public static AccountStatesEnum getEnum(Integer id) {
		for (AccountStatesEnum c : AccountStatesEnum.values()) {
			if (c.getId() == id) {
				return c;
			}
		}
		return null;
	}
}
