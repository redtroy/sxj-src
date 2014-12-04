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
import com.sxj.supervisor.dao.member.IMemberLogDao;

@Entity(mapper = IMemberLogDao.class)
@Table(name = "M_MEMBER_LOG")
public class MemberLogEntity extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7133632642509031954L;

	/**
	 * 主键标识
	 **/
	@Id(column = "ID")
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(name = "MEMBER_NO")
	private String memberNo;

	@Column(name = "MEMBER_NAME")
	private String memberName;

	@Column(name = "NOW_PAGE")
	private String nowPage;

	@Column(name = "NOW_NAME")
	private String nowName;

	@Column(name = "PRE_PAGE")
	private String prePage;

	@Column(name = "PRE_NAME")
	private String preName;

	@Column(name = "NEXT_PAGE")
	private String nextpage;

	@Column(name = "NEXT_NAME")
	private String nextName;

	@Column(name = "WAIT_TIME")
	private String waitTime;

	@Column(name = "CALL_TIME")
	private Date callTime;

	@Column(name = "IP")
	private String ip;

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

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getNowPage() {
		return nowPage;
	}

	public void setNowPage(String nowPage) {
		this.nowPage = nowPage;
	}

	public String getPrePage() {
		return prePage;
	}

	public void setPrePage(String prePage) {
		this.prePage = prePage;
	}

	public String getNextpage() {
		return nextpage;
	}

	public void setNextpage(String nextpage) {
		this.nextpage = nextpage;
	}

	public String getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(String waitTime) {
		this.waitTime = waitTime;
	}

	public Date getCallTime() {
		return callTime;
	}

	public void setCallTime(Date callTime) {
		this.callTime = callTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getNowName() {
		return nowName;
	}

	public void setNowName(String nowName) {
		this.nowName = nowName;
	}

	public String getPreName() {
		return preName;
	}

	public void setPreName(String preName) {
		this.preName = preName;
	}

	public String getNextName() {
		return nextName;
	}

	public void setNextName(String nextName) {
		this.nextName = nextName;
	}

}
