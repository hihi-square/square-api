package com.hihi.square.domain.chat.service;

import com.hihi.square.domain.chat.dto.response.ChatMessageRes;
import com.hihi.square.domain.chat.dto.response.ChatRoomRes;
import com.hihi.square.domain.chat.entity.ChatRoom;
import com.hihi.square.domain.chat.repository.ChatRoomRepository;
import com.hihi.square.domain.store.dto.response.StoreInfoRes;
import com.hihi.square.domain.store.dto.response.StoreRes;
import com.hihi.square.domain.store.entity.Store;
import com.hihi.square.domain.store.repository.StoreRepository;
import com.hihi.square.global.error.type.EntityNotFoundException;
import com.hihi.square.global.error.type.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService{

    private final StoreRepository storeRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Override
    public void addChatRoom() {

    }

    @Override
    public List<ChatRoomRes> getAllRoom(Integer stoId) {
        Store store = storeRepository.findById(stoId).orElseThrow(() -> new UserNotFoundException("가게 회원을 찾을 수 없습니다."));
        List<ChatRoom> chatRoomList = chatRoomRepository.findByStore(store);
        List<ChatRoomRes> res = new ArrayList<>();
        for(ChatRoom chatRoom: chatRoomList) {
            res.add(ChatRoomRes.builder()
                    .id(chatRoom.getId())
                    .lastMessage(ChatMessageRes.toRes(chatRoom.getLastMessage()))
                    .store(chatRoom.getStore1().equals(store) ? StoreRes.toRes(chatRoom.getStore2()):StoreRes.toRes(chatRoom.getStore1()))
                    .build());
            chatRoomList.add(chatRoomRepository.findById(chatRoom.getId()).orElseThrow(()-> new EntityNotFoundException("chat room을 찾을 수 없습니다.")));
        }
        return res;
    }

    @Override
    public ChatRoom getRoomByStoId(Integer stoId1, Integer stoId2) {
        return null;
    }
}
