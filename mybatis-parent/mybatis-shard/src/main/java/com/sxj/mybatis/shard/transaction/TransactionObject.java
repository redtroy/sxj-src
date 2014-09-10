package com.sxj.mybatis.shard.transaction;

import org.springframework.jdbc.datasource.ConnectionHolder;

public class TransactionObject {
	private boolean newConnectionHolder;

	private boolean mustRestoreAutoCommit;

	public void setConnectionHolder(ConnectionHolder connectionHolder, boolean newConnectionHolder) {
		this.newConnectionHolder = newConnectionHolder;
	}

	public boolean isNewConnectionHolder() {
		return this.newConnectionHolder;
	}

	public boolean hasTransaction() {
		return false;
	}

	public void setMustRestoreAutoCommit(boolean mustRestoreAutoCommit) {
		this.mustRestoreAutoCommit = mustRestoreAutoCommit;
	}

	public boolean isMustRestoreAutoCommit() {
		return this.mustRestoreAutoCommit;
	}

}
