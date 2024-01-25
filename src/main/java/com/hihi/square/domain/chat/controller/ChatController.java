package com.hihi.square.domain.chat.controller;

import com.hihi.square.domain.chat.dto.ChatMessageDto;
import com.hihi.square.domain.chat.entity.ChatMessageType;
import com.hihi.square.domain.chat.service.ChatRoomService;
import com.hihi.square.domain.chat.service.ChatService;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatRoomService chatRoomService;
    private final ChatService chatService;

    @MessageMapping("/chat/{chat-room-id}") // 여기로 전송되면 메서드 호출 -> websocketConfig prefixes에서 적용한건(send) 앞에 생략
    @SendTo("/sub/chat/{chat-room-id}") // 구독하고 있는 장소로 메세지 호출 (목적지) -> websocketConfig broker에서 적용한건 앞에 붙여줘야함
    public ChatMessageDto chat(@DestinationVariable("chat-room-id") Long roomId, @Payload ChatMessageDto message) {
        System.out.println("ws chat 들어옴");
        ChatMessageDto res = chatService.addChat(roomId, message);
        return res;

    }


}

