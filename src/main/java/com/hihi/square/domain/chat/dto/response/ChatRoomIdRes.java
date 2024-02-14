package com.hihi.square.domain.chat.dto.response;

import com.hihi.square.domain.chat.entity.ChatMessageType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatRoomIdRes {
    Long id;
    ChatMessageType type;
}
