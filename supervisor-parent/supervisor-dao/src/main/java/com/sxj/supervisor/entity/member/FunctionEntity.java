package com.sxj.supervisor.entity.member;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;
/**
 * 系统功能
 * @author AnShaoshuai
 *
 */
public class FunctionEntity extends Pagable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7200023021577640226L;

	/**
	 * 主键标识
	**/
	private String id;
	
	/**
	 * 功能名称
	**/
	private String title;
	
	/**
	 * 功能请求URL
	**/
	private String url;
	
	/**
	 * 父系统功能ID
	**/
	private String parentId;
	/**
	 * 级别
	**/
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
