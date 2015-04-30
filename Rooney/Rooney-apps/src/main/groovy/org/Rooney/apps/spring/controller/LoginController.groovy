package org.Rooney.apps.spring.controller


import javax.servlet.http.HttpSession;

import org.Rooney.apps.spring.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/Login")
class LoginController {
	@Autowired
	@Qualifier("loginService")
	private LoginService service
	
	@RequestMapping(params="method=login")
	String login(@RequestParam("username")String username,@RequestParam("userpass")String userpass,HttpSession session){
		def status=2
		def page=null
		def user=null
		try {
			user=service.login(username, userpass)
			if(user){
				status=1
			}else{
				status=0
			}
		} catch (Exception e) {
			e.printStackTrace()
		}
		switch(status){
			case 1:
				page="main/Main"
				session.setAttribute("user", user)
				break
			case 2:page="main/Error"
				break
			default :page="main/Login_false"
		}
		return page
	}
}
