package com.sxj.supervisor.entity.system;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Sn;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.dao.system.ISystemAccountDao;
import com.sxj.supervisor.validator.hibernate.AddGroup;
import com.sxj.supervisor.validator.hibernate.UpdateGroup;

@Entity(mapper = ISystemAccountDao.class)
@Table(name = "M_SYS_ACCOUNT")
public class SystemAccountEntity extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2256019266546123531L;

	/**
	 * 主键
	 **/
	@Id(column = "ID")
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(name = "ACCOUNT_NO")
	@Sn(pattern = "000", step = 1, table = "T_SN", stubValue = "0", stub = "F_SN_NAME", sn = "F_SN_NUMBER")
	private String accountNo;

	@Column(name = "NAME")
	@NotEmpty(message = "管理员姓名不能为空", groups = { AddGroup.class,
			UpdateGroup.class })
	@Length(max = 50, message = "管理员姓名长度过长", groups = { AddGroup.class,
			UpdateGroup.class })
	private String name;

	@Column(name = "ACCOUNT")
	@NotEmpty(message = "管理员账号不能为空", groups = { AddGroup.class,
			UpdateGroup.class })
	@Length(max = 20, message = "管理员账号长度过长", groups = { AddGroup.class,
			UpdateGroup.class })
	private String account;

	@Column(name = "PASSWORD")
	@NotEmpty(message = "密码不能为空", groups = { AddGroup.class, UpdateGroup.class })
	@Length(max = 20, message = "密码长度过长", groups = { AddGroup.class,
			UpdateGroup.class })
	private String password;

	@Column(name = "LAST_LOGIN")
	private Date lastLogin;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
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

}
