package com.sxj.supervisor.enu.member;

public enum CertificateTypeEnum {
	SGZCBX("施工总承包", 0), ZYCB("专业承包", 1), SGLW("施工劳务", 2), SJSGYTH("设计施工一体化", 3);

	// 成员变量
	private Integer id;

	private String name;

	private CertificateTypeEnum(String name, Integer id) {
		this.name = name;
		this.id = id;
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

	public static LevelEnum getEnum(Integer id) {
		for (LevelEnum c : LevelEnum.values()) {
			if (c.getId() == id) {
				return c;
			}
		}
		return null;
	}
}
