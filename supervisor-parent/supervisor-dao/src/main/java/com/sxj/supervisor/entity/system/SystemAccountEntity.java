package com.sxj.supervisor.entity.system;

import java.io.Serializable;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Sn;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.dao.system.ISystemAccountDao;

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
	@Sn(pattern = "0000", step = 1, table = "T_SN", stubValue = "SYS", stub = "F_SN_NAME", sn = "F_SN_NUMBER")
	private String accountNo;

	@Column(name = "NAME")
	private String name;

	@Column(name = "ACCOUNT")
	private String account;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "DEL_STATE")
	private Boolean delState=false;

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

	public Boolean getDelState() {
		return delState;
	}

	public void setDelState(Boolean delState) {
		this.delState = delState;
	}

	

}
