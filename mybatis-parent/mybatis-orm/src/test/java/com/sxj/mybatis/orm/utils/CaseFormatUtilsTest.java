/*
 * @(#)CaseFormatUtilsTest.java 2013年12月31日 下午23:33:33
 *
 * Copyright (c) 2011-2013 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package com.sxj.mybatis.orm.utils;

import org.junit.Assert;
import org.junit.Test;

import com.sxj.mybatis.orm.utils.CaseFormatUtils;

/**
 * unit test
 */
public class CaseFormatUtilsTest {

	@Test
	public void testCamelToUnderScore(){
		String camelCaseStr = "myName";
		
		Assert.assertTrue("my_name".equals(CaseFormatUtils.camelToUnderScore(camelCaseStr)));
	}
}
