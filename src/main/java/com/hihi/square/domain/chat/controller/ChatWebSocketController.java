package com.hihi.square.domain.chat.controller;

import com.hihi.square.domain.chat.dto.request.ChatMessageReq;
import com.hihi.square.domain.chat.dto.response.ChatMessageRes;
import com.hihi.square.domain.chat.service.ChatRoomService;
import com.hihi.square.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Collections;

@Controller
@RequiredArgsConstructor
@CrossOrigin("*")
public class ChatWebSocketController {

    private final ChatRoomService chatRoomService;
    private final ChatService chatService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat/{roomId}") // 여기로 전송되면 메서드 호출 -> websocketConfig prefixes에서 적용한건(send) 앞에 생략
    public void chat(Authentication authentication,  @DestinationVariable Long roomId, @Payload ChatMessageReq message) {
        Integer stoId = Integer.parseInt(authentication.getName());
        ChatMessageRes res = chatService.addChat(stoId, roomId, message);
        MessageHeaders headers = new MessageHeaders(Collections.singletonMap(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE));
        simpMessagingTemplate.convertAndSend("/sub/chat/" + roomId, res, headers);
    }

}
