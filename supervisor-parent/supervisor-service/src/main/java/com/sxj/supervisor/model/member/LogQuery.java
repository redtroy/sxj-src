package com.sxj.supervisor.model.member;

import java.io.Serializable;
import java.util.Date;

import com.sxj.mybatis.pagination.Pagable;

public class LogQuery extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1305713025441918429L;

	private String memberNo;

	private String nowPage;

	private String prePage;

	private String nextpage;

	private Date startTime;

	private Date endTime;

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
