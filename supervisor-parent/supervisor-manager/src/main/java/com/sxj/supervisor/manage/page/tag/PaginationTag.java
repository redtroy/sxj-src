package com.sxj.supervisor.manage.page.tag;

import com.sxj.supervisor.manage.page.tag.support.PaginationSupport;


public class PaginationTag extends PaginationSupport {

	private static final long serialVersionUID = -9195913216282184518L;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getNumEntries() {
		return numEntries;
	}

	public void setNumEntries(int numEntries) {
		this.numEntries = numEntries;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getNumEdgeEntries() {
		return numEdgeEntries;
	}

	public void setNumEdgeEntries(int numEdgeEntries) {
		this.numEdgeEntries = numEdgeEntries;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getNextText() {
		return nextText;
	}

	public void setNextText(String nextText) {
		this.nextText = nextText;
	}

	public String getPrevText() {
		return prevText;
	}

	public void setPrevText(String prevText) {
		this.prevText = prevText;
	}

	public boolean isNextShowAlways() {
		return nextShowAlways;
	}

	public void setNextShowAlways(boolean nextShowAlways) {
		this.nextShowAlways = nextShowAlways;
	}

	public boolean isPrevShowAlways() {
		return prevShowAlways;
	}

	public void setPrevShowAlways(boolean prevShowAlways) {
		this.prevShowAlways = prevShowAlways;
	}

	public String getEllipseText() {
		return ellipseText;
	}

	public void setEllipseText(String ellipseText) {
		this.ellipseText = ellipseText;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public boolean isEncode() {
		return encode;
	}

	public void setEncode(boolean encode) {
		this.encode = encode;
	}
	
	
}
