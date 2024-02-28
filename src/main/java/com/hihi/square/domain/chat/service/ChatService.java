package com.hihi.square.domain.chat.service;

import com.hihi.square.domain.chat.dto.request.ChatMessageReq;
import com.hihi.square.domain.chat.dto.response.ChatMessageRes;
import com.hihi.square.domain.chat.entity.ChatMessage;

import java.util.List;

public interface ChatService {
    ChatMessageRes addChat(Integer stoId, Long roomId, ChatMessageReq req);
    List<ChatMessageRes> getAllChats(Integer stoId, Long roomId);
}
