<<<<<<< HEAD:supervisor-parent/supervisor-dao/src/main/java/com/sxj/supervisor/entity/contract/ContractStateEnum.java
package com.sxj.supervisor.entity.contract;
/**
 * 状态
 * @author Administrator
 *
 */
public enum ContractStateEnum {
	approval("已审批", 0), noapproval("未审批", 1);

	// 成员变量
	private Integer id;

	private String name;

	private ContractStateEnum(String name, Integer id) {
		this.name = name;
		this.id = id;
	}

	// 普通方法
	public static String getName(Integer id) {
		for (ContractStateEnum c : ContractStateEnum.values()) {
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
=======
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
>>>>>>> a8bae8522e95d071900a2970b784ec9704f871b7:supervisor-parent/supervisor-service/src/main/java/com/sxj/supervisor/model/contract/ContractState.java
