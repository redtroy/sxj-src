package com.sxj.supervisor.entity.member;

import java.io.Serializable;
import java.util.Date;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.dao.member.IMemberImageDao;
import com.sxj.supervisor.enu.member.CertificateTypeEnum;

/**
 * 会员证书图片
 * 
 * @author anshaoshuai
 *
 */

@Entity(mapper = IMemberImageDao.class)
@Table(name = "M_MEMBER_IMAGE")
public class MemberImageEntity extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8461204497801670416L;

	/**
	 * 标识
	 **/
	@Id(column = "ID")
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	/**
	 * 会员id
	 */
	@Column(name = "MEMBER_NO")
	private String memberNo;

	/**
	 * 图片路径
	 */
	@Column(name = "IMAGE")
	private String image;
	/**
	 * 有效状态
	 */
	@Column(name = "STATE")
	private Integer state;

	/**
	 * 证书类型
	 */
	@Column(name = "CERTIFICATE_TYPE")
	private CertificateTypeEnum certificateType;

	/**
	 * 证书编号
	 */
	@Column(name = "CERTIFICATE_NO")
	private String certificateNo;

	/**
	 * 证书名称
	 */
	@Column(name = "CERTIFICATE_NAME")
	private String certificateName;

	/**
	 * 发证日期
	 */
	@Column(name = "ISSUE_DATE")
	private Date issueDate;

	/**
	 * 截止日期
	 */
	@Column(name = "DUE_DATE")
	private Date dueDate;

	/**
	 * 版本
	 */
	@Column(name = "VERSION")
	private String version;
	/**
	 * 创建时间
	 */
	@Column(name = "CREATION_DATE")
	private String creationDate;

	public String getCertificateName() {
		return certificateName;
	}

	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public CertificateTypeEnum getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(CertificateTypeEnum certificateType) {
		this.certificateType = certificateType;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

}
