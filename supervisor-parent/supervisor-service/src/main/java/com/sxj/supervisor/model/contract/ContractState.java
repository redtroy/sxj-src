package com.sxj.supervisor.model.contract;
/**
 * 状态
 * @author Administrator
 *
 */
public enum ContractState {
	approval("已审核", 0), noapproval("未审核", 1);

	// 成员变量
	private Integer id;

	private String name;

	private ContractState(String name, Integer id) {
		this.name = name;
		this.id = id;
	}

	// 普通方法
	public static String getName(Integer id) {
		for (ContractState c : ContractState.values()) {
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
