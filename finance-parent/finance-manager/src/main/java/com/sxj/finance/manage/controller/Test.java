package com.sxj.finance.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Test {

	@RequestMapping("test")
	public String test() {
		return "manage/jsp";
	}
}
