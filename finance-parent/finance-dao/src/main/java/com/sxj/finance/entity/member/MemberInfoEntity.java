package com.sxj.finance.entity.member;

import java.io.Serializable;

import com.sxj.finance.dao.member.IMemberDao;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;

/**
 * 基本信息
 * 
 * @author dujinxin
 *
 */
@Entity(mapper = IMemberDao.class)
@Table(name = "M_MEMBER_INFO")
public class MemberInfoEntity extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2262265581991391325L;

	/**
	 * 
	 */
	private String id;

	/**
	 * 
	 */
	private String memberNo;

	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 性别
	 */
	private Enum sex;
	/**
	 * 年龄
	 */
	private Integer age;
	/**
	 * 家庭住址
	 */
	private String homeAddress;
	/**
	 * 联系电话
	 */
	private String telNum;
	/**
	 * 身份证号
	 */
	private String cardNum;
	/**
	 * 电子邮箱
	 */
	private String email;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 营业执照编码
	 */
	private String bLicence;
	/**
	 * 注册资本
	 */
	private Double registered;
	/**
	 * 经营主体
	 */
	private String manage;
	/**
	 * 经营范围
	 */
	private String manageRange;

}
