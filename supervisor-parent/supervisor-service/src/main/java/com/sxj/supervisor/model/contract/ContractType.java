package com.sxj.supervisor.model.contract;
/**
 * 合同类型
 * @author Administrator
 *
 */
public enum ContractType {
	bidding("门窗招标", 0), glass("玻璃采购", 1),extrusions("型材采购", 2);

	// 成员变量
	private Integer id;

	private String name;

	private ContractType(String name, Integer id) {
		this.name = name;
		this.id = id;
	}

	// 普通方法
	public static String getName(Integer id) {
		for (ContractType c : ContractType.values()) {
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