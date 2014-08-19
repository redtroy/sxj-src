package com.sxj.supervisor.model.member;
/**
 * 会员子帐号状态
 * @author Administrator
 *
 */
public enum MemberStates {
	normal("正常", 0), stop("停用", 1);

	// 成员变量
	private Integer id;

	private String name;

	private MemberStates(String name, Integer id) {
		this.name = name;
		this.id = id;
	}

	// 普通方法
	public static String getName(Integer id) {
		for (MemberStates c : MemberStates.values()) {
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
