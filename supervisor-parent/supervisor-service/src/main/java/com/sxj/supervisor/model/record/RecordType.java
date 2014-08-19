package com.sxj.supervisor.model.record;
/**
 * 备案类型
 * @author Administrator
 *
 */
public enum RecordType {
	contract("合同备案", 0), change("变更备案", 1),supplement("补损备案", 2);

	// 成员变量
	private Integer id;

	private String name;

	private RecordType(String name, Integer id) {
		this.name = name;
		this.id = id;
	}

	// 普通方法
	public static String getName(Integer id) {
		for (RecordType c : RecordType.values()) {
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
