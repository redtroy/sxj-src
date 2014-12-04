package com.sxj.finance.entity.member;

import java.io.Serializable;
import java.util.Date;

import com.sxj.finance.dao.member.IAccountDao;
import com.sxj.finance.enu.member.AccountStatesEnum;
import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Sn;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.orm.annotations.Version;
import com.sxj.mybatis.pagination.Pagable;

/**
 * 子账号实体
 * 
 * @author Administrator
 *
 */
@Entity(mapper = IAccountDao.class)
@Table(name = "M_MEMBER_ACCOUNT")
public class AccountEntity extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 67779520871345440L;

	/**
	 * 主键标识
	 **/
	@Id(column = "ID")
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	/**
	 * 子账户ID
	 **/
	@Column(name = "ACCOUNT_NO")
	@Sn(pattern = "0000", step = 1, table = "T_SN", stub = "F_SN_NAME", sn = "F_SN_NUMBER", stubValueProperty = "noType")
	private String accountNo;

	/**
	 * 所属会员ID
	 **/
	@Column(name = "PARENT_ID")
	private String parentId;

	/**
	 * 子账户名称
	 **/
	@Column(name = "ACCOUNT_NAME")
	private String accountName;

	/**
	 * 姓名
	 **/
	@Column(name = "NAME")
	private String name;

	/**
	 * 子账户状态
	 **/
	@Column(name = "STATE")
	private AccountStatesEnum state;

	/**
	 * 注册日期
	 **/
	@Column(name = "REG_DATE")
	private Date regDate;

	/**
	 * 子账户密码
	 **/
	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "LAST_LOGIN")
	private Date lastLogin;

	@Column(name = "VERSION_LOCK")
	@Version
	private Long version;

	private String noType;

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AccountStatesEnum getState() {
		return state;
	}

	public void setState(AccountStatesEnum state) {
		this.state = state;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getNoType() {
		return noType;
	}

	public void setNoType(String noType) {
		this.noType = noType;
	}

}
