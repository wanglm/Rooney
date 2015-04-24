package org.Rooney.apps.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Login")
public class LoginController {
	@RequestMapping(params="method=login")
	public String login(){
		return "hdfs/WebSocket";
	}

}
