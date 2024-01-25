package com.hihi.square.domain.chat.service;

import com.hihi.square.domain.chat.entity.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService{
    @Override
    public void addChatRoom() {

    }

    @Override
    public List<ChatRoom> getAllRoom(Integer stoId) {
        return null;
    }

    @Override
    public ChatRoom getRoomByStoId(Integer stoId1, Integer stoId2) {
        return null;
    }
}
