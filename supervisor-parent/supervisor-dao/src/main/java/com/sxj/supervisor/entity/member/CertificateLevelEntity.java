package com.sxj.supervisor.entity.member;

import java.io.Serializable;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.dao.member.ICertificateLevelDao;
import com.sxj.supervisor.dao.member.IMemberDao;

/**
 * 证书等级
 * 
 * @author anshaoshuai
 *
 */
@Entity(mapper = ICertificateLevelDao.class)
@Table(name = "M_CERTIFICATE_LEVEL")
public class CertificateLevelEntity extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2535767406866861162L;

	/**
	 * 标识
	 **/
	@Id(column = "ID")
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	/**
	 * 图片id
	 */
	@Column(name = "IMAGE_ID")
	private String imageId;

	/**
	 * 等级
	 */
	@Column(name = "LEVEL")
	private String level;
	/**
	 * 备注
	 */
	@Column(name = "REMARK")
	private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "CertificateLevelEntity [id=" + id + ", imageId=" + imageId
				+ ", level=" + level + ", remark=" + remark + "]";
	}

}
