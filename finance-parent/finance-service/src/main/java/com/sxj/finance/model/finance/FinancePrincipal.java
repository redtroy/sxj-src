package com.sxj.finance.model.finance;

import java.io.Serializable;

import com.sxj.finance.entity.member.AccountEntity;
import com.sxj.finance.entity.member.MemberEntity;

public class FinancePrincipal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7928061127084410598L;

	private MemberEntity member;

	private AccountEntity account;

	public MemberEntity getMember() {
		return member;
	}

	public void setMember(MemberEntity member) {
		this.member = member;
	}

	public AccountEntity getAccount() {
		return account;
	}

	public void setAccount(AccountEntity account) {
		this.account = account;
	}

}
