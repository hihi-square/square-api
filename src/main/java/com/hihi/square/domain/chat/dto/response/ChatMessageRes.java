package com.hihi.square.domain.chat.dto.response;

import com.hihi.square.domain.chat.entity.ChatMessage;
import com.hihi.square.domain.chat.entity.ChatMessageType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jdk.jfr.ContentType;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
public class ChatMessageRes {
    @Enumerated(EnumType.STRING)
    private ChatMessageType type; // TALK, FILE, IMAGE, LINK
    private Long roomId;
    private Integer senderId;
    private Integer receiverId;
    private String message;
    private String url;
    private LocalDateTime time;

    public static ChatMessageRes toRes(ChatMessage message) {
        return ChatMessageRes.builder()
                .type(message.getType())
                .roomId(message.getChatRoom().getId())
                .receiverId(message.getReceiver().getUsrId())
                .senderId(message.getAuthor().getUsrId())
                .message(message.getMessage())
                .url(message.getUrl())
                .time(message.getTime())
                .build();
    }
}
