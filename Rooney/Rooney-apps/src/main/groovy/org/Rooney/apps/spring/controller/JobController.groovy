package org.Rooney.apps.spring.controller

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**定时任务controller
 * @author ming
 *
 */
@Controller
@RequestMapping("/Jobs")
class JobController {
	
	String show(){
		return "quartz/Main"
	}

}
