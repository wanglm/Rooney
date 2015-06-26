package org.Rooney.apps.spring.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

/**配置自动注入发送信息模板类
 * 与spring-websocket不是同一个通道
 * 这是为了做服务端主动推送的功能
 * @author ming
 *
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		//通道连接url，但是需要通过mvc的方式
		registry.addEndpoint("/test").withSockJS();
	}

	@Override
	public void configureWebSocketTransport(
			WebSocketTransportRegistration registry) {
		// TODO Auto-generated method stub

	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		// TODO Auto-generated method stub

	}

	@Override
	public void configureClientOutboundChannel(ChannelRegistration registration) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addArgumentResolvers(
			List<HandlerMethodArgumentResolver> argumentResolvers) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addReturnValueHandlers(
			List<HandlerMethodReturnValueHandler> returnValueHandlers) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean configureMessageConverters(
			List<MessageConverter> messageConverters) {
		//消息转换器
		messageConverters.add(new MappingJackson2MessageConverter());
		return false;
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		//发送的url
		registry.setApplicationDestinationPrefixes("/app");
		//接收的url
		registry.enableSimpleBroker("/queue", "/topic");
	}

}
