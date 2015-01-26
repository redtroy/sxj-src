package com.sxj.supervisor.enu.record;

/**
 * 备案绑定状态
 * 
 * @author Administrator
 *
 */
public enum RecordStateEnum {
	noBinding("未绑定", 0), Binding("已绑定", "已变更", "已补损", 1);
	// nochange ("未变更", 2),change("已变更", 3),nosupplement("未补损",
	// 4),supplement("已补损", 5);

	// 成员变量
	private Integer id;

	private String name;

	private String changeName;

	private String supplementName;

	private RecordStateEnum(String name, String changeName,
			String supplementName, Integer id) {
		this.id = id;
		this.name = name;
		this.changeName = changeName;
		this.supplementName = supplementName;
	}

	private RecordStateEnum(String name, Integer id) {
		this.name = name;
		this.id = id;
	}

	// 普通方法
	public static String getName(Integer id) {
		for (RecordStateEnum c : RecordStateEnum.values()) {
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

	public String getChangeName() {
		return changeName;
	}

	public void setChangeName(String changeName) {
		this.changeName = changeName;
	}

	public String getSupplementName() {
		return supplementName;
	}

	public void setSupplementName(String supplementName) {
		this.supplementName = supplementName;
	}

}
