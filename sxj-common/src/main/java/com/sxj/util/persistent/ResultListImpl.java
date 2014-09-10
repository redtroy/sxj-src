/**
 * 
 */
package com.sxj.util.persistent;

import java.util.List;

import com.sxj.mybatis.pagination.Pagable;

/**
 * 持久化结果集列表接口实现类
 */
public class ResultListImpl<T> implements ResultList<T> {

	private static final long serialVersionUID = -4590321979224243763L;

	public ResultListImpl() {

	}
	/** 返回记录列表 */
	private List<T> results;

	/** 返回分页信息 */
	private Pagable page;

	public Pagable getPage() {
		return page;
	}

	public List<T> getResults() {
		return results;
	}

	public void setPage(Pagable page) {
		this.page = page;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

}
