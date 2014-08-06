package com.sxj.mybatis.pagination;

public abstract class Pagable {
	private int showCount = 10; // 每页显示记录数
	private int totalPage; // 总页数
	private int totalResult; // 总记录数
	private int currentPage; // 当前页

	private boolean pagable = false;

	// private int currentResult; // 当前记录起始索引

	public int getShowCount() {
		return showCount;
	}

	public void setShowCount(int showCount) {
		this.showCount = showCount;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalResult() {
		return totalResult;
	}

	public void setTotalResult(int totalResult) {
		this.totalResult = totalResult;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public boolean isPagable() {
		return pagable;
	}

	public void setPagable(boolean pagable) {
		this.pagable = pagable;
	}

}
