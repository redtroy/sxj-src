package com.sxj.supervisor.entity.system;

import java.io.Serializable;
import java.util.Date;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.dao.system.IOperatorLogDao;

@Entity(mapper = IOperatorLogDao.class)
@Table(name = "M_SYS_LOG")
public class OperatorLogEntity extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3906185582503441078L;

	/**
	 * 主键
	 **/
	@Id(column = "ID")
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	/**
	 * 管理員賬號
	 */
	@Column(name = "ACCOUNT_NO")
	private String accountNo;

	/**
	 * 操作時間
	 */
	@Column(name = "OPER_TIME")
	private Date operatorTime;

	/**
	 * 操作內容
	 */
	@Column(name = "LOGS")
	private String logs;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public Date getOperatorTime() {
		return operatorTime;
	}

	public void setOperatorTime(Date operatorTime) {
		operatorTime = operatorTime;
	}

	public String getLogs() {
		return logs;
	}

	public void setLogs(String logs) {
		this.logs = logs;
	}

}
