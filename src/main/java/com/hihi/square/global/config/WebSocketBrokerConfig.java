package com.hihi.square.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker // 웹소켓 메세지 핸들링 활성화
public class WebSocketBrokerConfig implements WebSocketMessageBrokerConfigurer { // websocket + stomp

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub"); // 해당 주소를 구독하고 있는 클라이언트들에게 메세지 전달
        registry.setApplicationDestinationPrefixes("/send"); // 클라이언트에서 보낸 메세지를 받을 prefix
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp") // SockJS 연결주소
                .setAllowedOrigins("*") // 일단 모든 경로에 대해서 CORS 허용
                .withSockJS(); // 낮은 버전의 브라우저에서도 적용 가능
        // 주소: ws:localhost:8080/ws-stomp
    }
}
