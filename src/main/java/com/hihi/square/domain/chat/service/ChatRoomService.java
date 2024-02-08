package com.hihi.square.domain.chat.service;

import com.hihi.square.domain.chat.dto.response.ChatRoomRes;
import com.hihi.square.domain.chat.entity.ChatRoom;

import java.util.List;

public interface ChatRoomService {
    void addChatRoom(); // 채팅방 생성
    List<ChatRoomRes> getAllRoom(Integer stoId); // 유저 채팅방 찾기
    ChatRoom getRoomByStoId(Integer stoId1, Integer stoId2); // 유저가 한 유저와 하고 있는 채팅방 찾기
}
