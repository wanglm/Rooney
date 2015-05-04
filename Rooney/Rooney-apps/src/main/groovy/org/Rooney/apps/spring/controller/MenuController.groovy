package org.Rooney.apps.spring.controller

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Menu")
class MenuController {
	@RequestMapping(params="method=list")
	String list(){
		return "system/Menu"
	}
}
