package com.sxj.supervisor.open.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("/rfid")
public class OpenRfidController {

	
	public void sendGoods(String rfidNo) {

	}

	@RequestMapping(value = "info/{rfidNo}")
	public void getRfidInfo(@PathVariable String rfidNo) {

	}

}
