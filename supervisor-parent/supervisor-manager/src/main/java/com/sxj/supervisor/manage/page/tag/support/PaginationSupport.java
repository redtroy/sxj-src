package com.sxj.supervisor.manage.page.tag.support;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.sxj.util.common.BigDecimalUtil;
import com.sxj.util.common.StringUtils;

public class PaginationSupport extends BodyTagSupport implements ParamParent {

	private static final long serialVersionUID = 4377400289683416508L;

	// 页面显示的记录数
	protected int pageSize = 20;

	// 分页控件现实的页码数
	protected int numEntries = 10;

	// 当前页数
	protected int currentPage = 1;

	// 显示省略号后面的个数如:1...2 3 4 5...100
	protected int numEdgeEntries = 1;

	// 总记录数
	protected int total = 0;

	// 定义next的名字
	protected String nextText = "下一页";

	// 定义prev的名字
	protected String prevText = "上一页";

	// 当设置为false时，只有当页数可以增加时，next链接才显示出来。比如到了30页，没有下一页了，则next不显示。Default: true
	protected boolean nextShowAlways = true;

	// 同nextShowAlways,控制prev是否显示
	protected boolean prevShowAlways = true;

	// 设置中间省略号的样式
	protected String ellipseText = "...";

	// 页面对应URL地址
	protected String pageUrl = "";

	// 地址是否编码
	protected boolean encode = true;

	private ParamSupport.ParamManager params;

	public PaginationSupport() {
		super();
		init();
	}

	private void init() {
		pageSize = 20;
		numEntries = 10;
		currentPage = 1;
		numEdgeEntries = 1;
		total = 0;
		nextText = "下一页";
		prevText = "上一页";
		nextShowAlways = true;
		prevShowAlways = true;
		ellipseText = "...";
	}

	public int doStartTag() throws JspException {
		params = new ParamSupport.ParamManager();
		validData();
		return EVAL_BODY_BUFFERED;
	}

	@SuppressWarnings("rawtypes")
	public int doEndTag() throws JspException {
		String[] urls = pageUrl.split("[?]");
		pageUrl = urls[0];
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		String enc = pageContext.getResponse().getCharacterEncoding();
		Map paramMap = request.getParameterMap();
		String paramName = "";
		String[] paramValues = null;
		String paramValue = "";
		Iterator iter = paramMap.entrySet().iterator();
		tag: while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			paramValues = (String[]) entry.getValue();
			if (encode) {
				paramName = StringUtils
						.urlEncode(entry.getKey().toString(), enc);
				paramValue = StringUtils.urlEncode(paramValues[0], enc);
			} else {
				paramName = entry.getKey().toString();
				paramValue = paramValues[0];
			}
			if (paramName.equals("curPageNo") || paramName.equals("pageSize")) {
				continue;
			}
			for (int j = 0; j < params.getNames().size(); j++) {
				if (paramName.equals(params.getNames().get(j))) {
					params.getValues().set(j, paramValue);
					continue tag;
				}
			}
			params.addParameter(paramName, paramValue);
		}
		pageUrl = params.aggRegateParams(pageUrl);
		try {
			pageContext.getOut().print(drawPageBar());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return EVAL_PAGE;
	}

	/**
	 * 计算最大页码
	 * 
	 * @return
	 */
	private int numPages() {
		int aaa = (int) Math.ceil(BigDecimalUtil.div(total + "", pageSize + "")
				.doubleValue());
		return aaa;

	}

	private int start;
	private int end;

	private void interval() {
		int neHalf = (int) Math.ceil(numEntries / 2);
		start = (currentPage > neHalf ? Math.max(
				Math.min(currentPage - neHalf, numPages() - numEntries), 0) : 0);
		end = currentPage > neHalf ? Math.min(currentPage + neHalf, numPages())
				: Math.min(numEntries, numPages());
		start = start + 1;
		end = end+1;
	}

	private String drawPageBar() {
		interval();
		StringBuffer pageItem = new StringBuffer();

		int questionMark = pageUrl.indexOf("?");
		if (StringUtils.isNotEmpty(prevText)
				&& (currentPage > 0 || prevShowAlways)) {

			if (currentPage == 1) {
				pageItem.append("<div class='page'><span><a>共");
				pageItem.append(this.total);
				pageItem.append("条记录&nbsp;&nbsp;</a></span><span><a>首页</a></span> <span>");
				pageItem.append(prevText);
				pageItem.append("</span>");
			} else {
				if (questionMark == -1) {
					pageItem.append("<div class='page'><span><a>共");
					pageItem.append(this.total);
					pageItem.append("条记录&nbsp;&nbsp;</a></span><span><a href='");
					pageItem.append(pageUrl);
					pageItem.append("?curPageNo=1&pageSize=");
					pageItem.append(pageSize);
					pageItem.append("'>首页</a></span>");
					pageItem.append(" <span><a href='");
					pageItem.append(pageUrl);
					pageItem.append("?curPageNo=");
					pageItem.append((currentPage - 1));
					pageItem.append("&pageSize=");
					pageItem.append(pageSize);
					pageItem.append("'>");
					pageItem.append(prevText);
					pageItem.append("</a></span>");
				} else {
					pageItem.append("<div class='page'><span><a>共");
					pageItem.append(this.total);
					pageItem.append("条记录&nbsp;&nbsp;</span><span><a href='");
					pageItem.append(pageUrl);
					pageItem.append("&curPageNo=1&pageSize=");
					pageItem.append(pageSize);
					pageItem.append("'>首页</a></span>");
					pageItem.append(" <span><a href='");
					pageItem.append(pageUrl);
					pageItem.append("&curPageNo=");
					pageItem.append((currentPage - 1));
					pageItem.append("&pageSize=");
					pageItem.append(pageSize);
					pageItem.append("'>");
					pageItem.append(prevText);
					pageItem.append("</a></span>");
				}
			}
			if (start > 0 && numEdgeEntries > 0) {
				int end = Math.min(numEdgeEntries, start);
				for (int i = 1; i < end; i++) {
					pageItem.append(appendItem(i, pageItem.toString()));
				}
				if (numEdgeEntries < start
						&& StringUtils.isNotEmpty(ellipseText)) {
					pageItem.append("<span>");
					pageItem.append(ellipseText);
					pageItem.append("</span>");
				}
			}
			for (int i = start; i < end; i++) {
				pageItem.append(appendItem(i, pageItem.toString()));
			}
			if (end <= numPages() && numEdgeEntries > 0) {
				if (numEdgeEntries > 0 && StringUtils.isNotEmpty(ellipseText)) {
					pageItem.append("<span>");
					pageItem.append(ellipseText);
					pageItem.append("</span>");
				}
				int begin = Math.max(end + numEdgeEntries, numPages()
						- numEdgeEntries);
				for (int i = begin+1; i <= numPages(); i++) {
					pageItem.append(appendItem(i, pageItem.toString()));
				}
			}
			if (StringUtils.isNotEmpty(nextText)
					&& (currentPage < (numPages()) || nextShowAlways)) {
				int t = numPages();
				System.out.println(t);
				if (currentPage == numPages()) {
					pageItem.append(" <span><a>末页</a></span> <span>" + nextText
							+ "</span>");
				} else {
					if (questionMark == -1) {
						pageItem.append(" <span><a href='");
						pageItem.append(pageUrl);
						pageItem.append("?curPageNo=");
						pageItem.append((currentPage + 1));
						pageItem.append("&pageSize=");
						pageItem.append(pageSize);
						pageItem.append("'>");
						pageItem.append(nextText);
						pageItem.append("</a></span>");

						pageItem.append(" <span><a href='");
						pageItem.append(pageUrl);
						pageItem.append("?curPageNo=");
						pageItem.append((numPages()));
						pageItem.append("&pageSize=");
						pageItem.append(pageSize);
						pageItem.append("'>末页</a></span>");
					} else {
						pageItem.append(" <span><a href='");
						pageItem.append(pageUrl);
						pageItem.append("&curPageNo=");
						pageItem.append((currentPage + 1));
						pageItem.append("&pageSize=");
						pageItem.append(pageSize);
						pageItem.append("'>");
						pageItem.append(nextText);
						pageItem.append("</a></span>");

						pageItem.append(" <span><a href='");
						pageItem.append(pageUrl);
						pageItem.append("&curPageNo=");
						pageItem.append((numPages()));
						pageItem.append("&pageSize=");
						pageItem.append(pageSize);
						pageItem.append("'>末页</a></span>");
					}

				}
			}
		}
		pageItem.append("<form name='pageform' action='");
		pageItem.append(pageUrl);
		pageItem.append("' method='post' style='display:inline;margin-left:16px'>");
		pageItem.append("到 <input type='text' onkeypress='if(event.keyCode==13){return false;}' onkeyup=\"value=value.replace(/[^\\d]/g,'')\"  class='inp' name='curPageNo' value='");
		pageItem.append(currentPage);
		pageItem.append("'/> 页");
		pageItem.append("<input type='submit' class='button1' value='跳转' onkeypress='if(event.keyCode==13){return false;}' onclick=\"if(document.getElementsByName('curPageNo')[0].value==''){alert('页数不能为空');return false;};\"/>，");
		pageItem.append("每页显示 <select style='width:55px;' name='pageSize' onchange=\"var val=this.value;location.href='");
		pageItem.append(pageUrl);
		if (questionMark == -1) {
			pageItem.append("?pageSize='+val;\">");
		} else {
			pageItem.append("&pageSize='+val;\">"); 
		}

		switch (pageSize) {
		case 10:
			pageItem.append("<option selected='true' value='10'>10</option><option value='20'>20</option><option value='30'>30</option><option value='40'>40</option><option value='50'>50</option>");
			break;
		case 20:
			pageItem.append("<option value='10'>10</option><option selected='true' value='20'>20</option><option value='30'>30</option><option value='40'>40</option><option value='50'>50</option>");

			break;
		case 30:
			pageItem.append("<option value='10'>10</option><option value='20'>20</option><option selected='true' value='30'>30</option><option value='40'>40</option><option value='50'>50</option>");

			break;
		case 40:
			pageItem.append("<option value='10'>10</option><option value='20'>20</option><option value='30'>30</option><option selected='true' value='40'>40</option><option value='50'>50</option>");

			break;
		case 50:
			pageItem.append("<option value='10'>10</option><option value='20'>20</option><option value='30'>30</option><option value='40'>40</option><option selected='true' value='50'>50</option>");

			break;

		default:
			pageItem.append("<option value='10'>10</option><option value='20'>20</option><option value='30'>30</option><option value='40'>40</option><option value='50'>50</option>");

			break;
		}
		pageItem.append("</select> 条</form></div>");
		return pageItem.toString();
	}

	private String appendItem(int page_id, String pageItem) {
		StringBuffer items = new StringBuffer();
		// items.append(pageItem);
		String style = "";
		if (page_id == (currentPage)) {
			style = "selected";
		}
		int questionMark = pageUrl.indexOf("?");
		if (questionMark == -1) {
			items.append(" <a class='");
			items.append(style);
			items.append("' href='");
			items.append(pageUrl);
			items.append("?curPageNo=");
			items.append(page_id);
			items.append("&pageSize=");
			items.append(pageSize);
			items.append("'>");
			items.append((page_id));
			items.append("</a>");
		} else {
			items.append(" <a class='");
			items.append(style);
			items.append("' href='");
			items.append(pageUrl);
			items.append("&curPageNo=");
			items.append(page_id);
			items.append("&pageSize=");
			items.append(pageSize);
			items.append("'>");
			items.append((page_id));
			items.append("</a>");
		}
		return items.toString();
	}

	private void validData() throws JspException {

		if (pageSize < 0) {
			pageSize = 20;
		}
		if (numEntries < 0) {
			numEntries = 10;
		}
		if (currentPage < 0) {
			currentPage = 1;
		}
		// currentPage = currentPage + 1;
		if (numEdgeEntries < 0) {
			numEdgeEntries = 0;
		}
		if (total < 0) {
			total = 0;
		}
	}

	@Override
	public void addParameter(String name, String value) {
		// TODO Auto-generated method stub

	}

}
