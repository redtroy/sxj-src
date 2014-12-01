package com.sxj.supervisor.website.controller;

import java.beans.PropertyEditorSupport;

import org.springframework.util.StringUtils;

public class TrimStringPropertyEditorSupport extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.hasText(text))
			setValue(text.trim());
		// super.setAsText(text);
	}

}
