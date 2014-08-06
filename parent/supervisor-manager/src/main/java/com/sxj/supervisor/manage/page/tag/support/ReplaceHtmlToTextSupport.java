package com.sxj.supervisor.manage.page.tag.support;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.sxj.util.common.StringUtils;

public class ReplaceHtmlToTextSupport extends BodyTagSupport {

	private static final long serialVersionUID = -1173097410161182720L;

	protected String html;
	
	protected int length;
	
	public void init(){
		html = "";
		length = 0;
	}
	
	public ReplaceHtmlToTextSupport(){
		super();
		init();
	}
	
	public int doStartTag() throws JspException{
		if(StringUtils.isEmpty(html)){
			html = "";
		}
		return EVAL_BODY_BUFFERED;
	}
	
	public int doEndTag() throws JspException{
		try {
			//System.out.println(replaceHtmlToText(html));
			String text = replaceHtmlToText(html);
			if(text.length() <= length){
				pageContext.getOut().print(text);
			}else{
				pageContext.getOut().print(replaceHtmlToText(html).substring(0, length));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
	
	private static String replaceHtmlToText(String html){
		String text = html.replaceAll("<[^>]*>", "");
		return text;
	}
}
