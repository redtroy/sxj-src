package com.sxj.supervisor.entity;

import java.io.Serializable;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.supervisor.dao.contract.IContractDao;

@Entity(mapper = IContractDao.class)
@Table(name = "M_IMAGE")
public class ImageEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8455840456664872614L;

	@Id(column = "ID")
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(name = "MD5")
	private String md5;

	@Column(name = "IMAGE_ID")
	private String imageId;

	@Column(name = "STATE")
	private Integer state;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}
