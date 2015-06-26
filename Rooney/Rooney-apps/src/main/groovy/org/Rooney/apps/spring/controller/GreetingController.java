package org.Rooney.apps.spring.controller;

import org.Rooney.api.websocket.Greeting;
import org.Rooney.api.websocket.HelloMsg;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {

	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Greeting greeting(HelloMsg message) throws Exception {
		return new Greeting("Hello, " + message.getName() + "!");
	}
}
