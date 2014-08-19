package com.sxj.supervisor.model.record;
/**
 * 备案状态
 * @author Administrator
 *
 */
public enum RecordState {
	noBinding("未绑定", 0),Binding ("已绑定", 1),nochange ("未变更", 2),change("已变更", 0),nosupplement("未补损", 1),supplement("已补损", 2);

	// 成员变量
	private Integer id;

	private String name;

	private RecordState(String name, Integer id) {
		this.name = name;
		this.id = id;
	}

	// 普通方法
	public static String getName(Integer id) {
		for (RecordState c : RecordState.values()) {
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
