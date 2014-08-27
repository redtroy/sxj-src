package com.sxj.supervisor.website.controller;

import java.beans.PropertyEditorSupport;

public class EnumPropertyEditorSupport<E extends Enum<E>> extends
		PropertyEditorSupport {
	private Class<E> clzz;

	public EnumPropertyEditorSupport(Class<E> clzz) {
		super();
		this.clzz = clzz;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (text != null && text.length() > 0) {
			int index = Integer.parseInt(text);
			setValue(clzz.getEnumConstants()[index]);
		}
	}

}
