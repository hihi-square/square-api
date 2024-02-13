package com.hihi.square.domain.chat.service;

import com.hihi.square.domain.chat.dto.request.ChatMessageReq;
import com.hihi.square.domain.chat.dto.response.ChatMessageRes;

public interface ChatService {
    ChatMessageRes addChat(Integer stoId, Long roomId, ChatMessageReq req);
}
