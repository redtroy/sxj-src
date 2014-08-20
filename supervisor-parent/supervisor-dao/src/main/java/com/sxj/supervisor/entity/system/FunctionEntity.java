package com.sxj.supervisor.entity.system;

import java.io.Serializable;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.dao.system.IFunctionDao;
/**
 * 系统功能
 * @author AnShaoshuai
 *
 */
@Entity(mapper = IFunctionDao.class)
@Table(name = "M_SYS_FUNCTION")
public class FunctionEntity extends Pagable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7200023021577640226L;

	/**
	 * 主键标识
	**/
	@Id(column = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	/**
	 * 功能名称
	**/
	@Column(name = "TITLE")
	private String title;
	
	/**
	 * 功能请求URL
	**/
	@Column(name = "URL")
	private String url;
	
	/**
	 * 父系统功能ID
	**/
	@Column(name = "PARENT_ID")
	private String parentId;
	/**
	 * 级别
	**/
	@Column(name = "LEVEL")
	private Integer level;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	
}
