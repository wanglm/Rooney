package org.Rooney.apps.spring.controller

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**主页controller
 * @author ming
 *
 */
@Controller
@RequestMapping("/Main")
class MainController {
	@RequestMapping(params="method=main")
   	String main(){
		   return "main/Main"
	   }
}
