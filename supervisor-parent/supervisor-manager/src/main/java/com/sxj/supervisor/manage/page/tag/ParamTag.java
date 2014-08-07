package com.sxj.supervisor.manage.page.tag;

import com.sxj.supervisor.manage.page.tag.support.ParamSupport;

public class ParamTag extends ParamSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public void setName(String name) {
		this.name = name;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public void setEncode(boolean encode) {
		this.encode = encode;
	}
	
}
