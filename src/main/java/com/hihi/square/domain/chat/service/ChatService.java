package com.hihi.square.domain.chat.service;

import com.hihi.square.domain.chat.dto.ChatMessageDto;

public interface ChatService {
    ChatMessageDto addChat(Long roomId, ChatMessageDto req);
}
