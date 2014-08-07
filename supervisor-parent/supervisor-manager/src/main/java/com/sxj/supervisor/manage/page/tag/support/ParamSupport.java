package com.sxj.supervisor.manage.page.tag.support;

import java.nio.channels.IllegalSelectorException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import com.sxj.util.common.StringUtils;


public class ParamSupport extends BodyTagSupport {
	
	private static final long serialVersionUID = 4489198322558586253L;
	
	protected String name;
	protected String value;
	protected boolean encode = true;
	
	public ParamSupport(){
		super();
		init();
	}
	/**
	 * 初始化属性
	 */
	private void init(){
		name = value = null;
		encode = true;
	}
	/**
	 * 在标签结束时返回标签的@Param信息,其中包括name和value
	 * 如果value为空，那么value就为<param></param>中的内容
	 */
	public int doEndTag() throws JspException{
		Tag t = findAncestorWithClass(this, ParamParent.class);
		if(t == null){
			throw new JspTagException("没有找到上层JSP标签!");
		}
		if(StringUtils.isEmpty(name)){
			return EVAL_PAGE;
		}
		ParamParent parent = (ParamParent)t;
		if(value == null){
			if(bodyContent == null || bodyContent.getString() == null){
				value = "";
			}else{
				value = bodyContent.getString().trim();
			}
		}
		if(encode){
			String enc = pageContext.getResponse().getCharacterEncoding();
			parent.addParameter(StringUtils.urlEncode(name, enc), StringUtils.urlEncode(value, enc));
		}else{
			parent.addParameter(name, value);
		}
		return EVAL_PAGE;		
	}
	
	/**
	 * 标签参数管理
	 * @author gykl
	 *
	 */
	public static class ParamManager{
		private List<String> names = new LinkedList<String>();
		private List<String> values = new LinkedList<String>();
		private boolean done = false;
		
		/**
		 * 添加参数
		 * @param name
		 * @param value
		 */
		public void addParameter(String name,String value){
			if(done){
				throw new IllegalStateException();
			}
			if(name != null){
				names.add(name);
			}
			if(value != null){
				values.add(value);
			}else{
				values.add("");
			}
		}
		
		/**
		 * 将参数合成Url返回
		 * @param url
		 * @return
		 */
		public String aggRegateParams(String url){
			if(done){
				throw new IllegalSelectorException();
			}
			done = true;
			StringBuffer newParams = new StringBuffer();
			for(int i = 0;i < names.size();i++){
				newParams.append(names.get(i) + "=" + values.get(i));
				if(i < (names.size() - 1)){
					newParams.append("&");
				}
			}
			if(newParams.length() > 0){
				int questionMark = url.indexOf("?");
				if(questionMark == -1){
					return (url + "?" + newParams);
				}else{
					StringBuffer workingUrl = new StringBuffer(url);
					workingUrl.insert(questionMark + 1, (newParams + "&"));
					return workingUrl.toString();
				}
			}
			return url;
		}

		public List<String> getNames() {
			return names;
		}

		public List<String> getValues() {
			return values;
		}
		
	}
}
