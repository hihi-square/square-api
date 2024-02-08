package com.hihi.square.domain.chat.service;

import com.hihi.square.domain.chat.dto.request.ChatMessageReq;

public interface ChatService {
    ChatMessageReq addChat(Long roomId, ChatMessageReq req);
}
