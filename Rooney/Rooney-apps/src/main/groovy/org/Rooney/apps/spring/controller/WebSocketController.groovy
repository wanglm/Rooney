package org.Rooney.apps.spring.controller

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/WebSocket")
class WebSocketController {
	@RequestMapping(params="method=test")
	String test(){
		return "hdfs/WebSocket"
	}

}
