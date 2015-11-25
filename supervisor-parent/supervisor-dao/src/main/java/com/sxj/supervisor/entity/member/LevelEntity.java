package com.sxj.supervisor.entity.member;

import java.io.Serializable;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.dao.member.ILevelDao;

@Entity(mapper = ILevelDao.class)
@Table(name = "M_LEVEL")
public class LevelEntity extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6049485705944706087L;
	/**
	 * 主键标识
	 **/
	@Id(column = "ID")
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(name = "LEVEL_NAME")
	private String levelName;

	@Column(name = "LEVEL_INFO")
	private String levelInfo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getLevelInfo() {
		return levelInfo;
	}

	public void setLevelInfo(String levelInfo) {
		this.levelInfo = levelInfo;
	}

}
