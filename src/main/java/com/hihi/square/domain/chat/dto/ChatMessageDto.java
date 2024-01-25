package com.hihi.square.domain.chat.dto;

import com.hihi.square.domain.chat.entity.ChatMessageType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageDto {
    @Enumerated(EnumType.STRING)
    private ChatMessageType type;
    private Integer senderId;
    private Integer receiverId;
    private String message;
    private MultipartFile file;
    private String url;
    private LocalDateTime time;
}
