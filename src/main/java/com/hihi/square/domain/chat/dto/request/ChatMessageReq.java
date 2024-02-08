package com.hihi.square.domain.chat.dto.request;

import com.hihi.square.domain.chat.entity.ChatMessageType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public class ChatMessageReq {
    @Enumerated(EnumType.STRING)
    private ChatMessageType type; // TALK, FILE, IMAGE, LINK
    private Integer senderId;
    private Integer receiverId;
    private String message;
    private MultipartFile file;
    private LocalDateTime time;
}
