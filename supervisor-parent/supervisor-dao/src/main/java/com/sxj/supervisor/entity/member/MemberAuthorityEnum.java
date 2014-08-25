package com.sxj.supervisor.entity.member;
/**
 * 权限
 * @author Administrator
 *
 */
public enum MemberAuthorityEnum {
	all("全部", 0), ContractManagement("合同管理", 1),management("备案管理", 2), RDIDManagement("RFID跟踪管理", 3),memberManagement("会员行为跟踪管理", 4), payment ("支付管理", 5);

	// 成员变量
	private Integer id;

	private String name;

	private MemberAuthorityEnum(String name, Integer id) {
		this.name = name;
		this.id = id;
	}

	// 普通方法
	public static String getName(Integer id) {
		for (MemberAuthorityEnum c : MemberAuthorityEnum.values()) {
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
