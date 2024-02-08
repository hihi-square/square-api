package com.hihi.square.domain.chat.controller;

import com.hihi.square.domain.chat.dto.request.ChatMessageReq;
import com.hihi.square.domain.chat.dto.response.ChatMessageRes;
import com.hihi.square.domain.chat.service.ChatRoomService;
import com.hihi.square.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@RequiredArgsConstructor
@CrossOrigin("*")
public class ChatWebSocketController {
    private final ChatRoomService chatRoomService;
    private final ChatService chatService;


    @MessageMapping("/chat/{roomId}") // 여기로 전송되면 메서드 호출 -> websocketConfig prefixes에서 적용한건(send) 앞에 생략
    @SendTo("/sub/chat/{roomId}") // 구독하고 있는 장소로 메세지 호출 (목적지) -> websocketConfig broker에서 적용한건 앞에 붙여줘야함
    public void chat(@DestinationVariable Long roomId, @Payload ChatMessageReq message) {
        System.out.println("ws chat 들어옴");
        ChatMessageReq res = chatService.addChat(roomId, message);
//        return res;
    }


}
