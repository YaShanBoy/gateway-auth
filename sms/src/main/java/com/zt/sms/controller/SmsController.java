package com.zt.sms.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sms")
public class SmsController {
	
	@RequestMapping("sendMsg")
	public void sendMsg(String msg) {
		System.out.println("发送短信内容："+msg);
	}
	
}
