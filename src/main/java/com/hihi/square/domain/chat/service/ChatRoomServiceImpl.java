package com.hihi.square.domain.chat.service;

import com.hihi.square.domain.chat.dto.response.ChatMessageRes;
import com.hihi.square.domain.chat.dto.response.ChatRoomIdRes;
import com.hihi.square.domain.chat.dto.response.ChatRoomRes;
import com.hihi.square.domain.chat.entity.ChatMessage;
import com.hihi.square.domain.chat.entity.ChatMessageType;
import com.hihi.square.domain.chat.entity.ChatRoom;
import com.hihi.square.domain.chat.repository.ChatMessageRepository;
import com.hihi.square.domain.chat.repository.ChatRoomRepository;
import com.hihi.square.domain.store.dto.response.StoreInfoRes;
import com.hihi.square.domain.store.dto.response.StoreRes;
import com.hihi.square.domain.store.entity.Store;
import com.hihi.square.domain.store.repository.StoreRepository;
import com.hihi.square.global.error.type.EntityNotFoundException;
import com.hihi.square.global.error.type.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService{

    private final StoreRepository storeRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;


    @Override
    public List<ChatRoomRes> getAllRoom(Integer stoId) {
        Store store = storeRepository.findById(stoId).orElseThrow(() -> new UserNotFoundException("가게 회원을 찾을 수 없습니다."));
        List<ChatRoom> chatRoomList = chatRoomRepository.findByStore(store);
        List<ChatRoomRes> res = new ArrayList<>();
        for(ChatRoom chatRoom: chatRoomList) {
            List<ChatMessageRes> cmRes = new ArrayList<>();
            List<ChatMessage> messages = chatMessageRepository.findAllByChatRoom(chatRoom);
            for(ChatMessage m : messages) cmRes.add(ChatMessageRes.toRes(m));
            res.add(ChatRoomRes.builder()
                    .id(chatRoom.getId())
                    .chatMessageList(cmRes)
                    .store(chatRoom.getStore1().getUsrId() == store.getUsrId() ? StoreInfoRes.toRes(chatRoom.getStore2()):StoreInfoRes.toRes(chatRoom.getStore1()))
                    .notReadNum(0)
                    .build());
        }
        return res;
    }

    @Override
    @Transactional
    public ChatRoomIdRes getRoomIdByStoIds(Integer stoId1, Integer stoId2) {
        Store store1 = storeRepository.findById(stoId1).orElseThrow(() -> new UserNotFoundException("가게 회원을 찾을 수 없습니다."));
        Store store2 = storeRepository.findById(stoId2).orElseThrow(() -> new EntityNotFoundException("검색한 가게 회원을 찾을 수 없습니다."));
        
        // 없으면 만들기
        ChatRoom chatRoom = getOrCreate(store1, store2);

        // 데이터에 넣기
        return ChatRoomIdRes.builder().id(chatRoom.getId()).type(chatRoom.getLastMessage().getType()).build();
    }

    @Override
    public ChatRoomRes getRoom(Integer stoId, Long roomId) {
        Store store = storeRepository.findById(stoId).orElseThrow(() -> new UserNotFoundException("가게 회원을 찾을 수 없습니다."));
        ChatRoom chatRoom = chatRoomRepository.findByIdAndStore(roomId, store).orElseThrow(() -> new EntityNotFoundException("채팅룸이 없습니다."));

        return null;
    }

    @Transactional
    public ChatRoom getOrCreate(Store store1, Store store2) {
        Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findByStores(store1, store2);
        if(chatRoomOptional.isEmpty()) { // 객체가 없으면 만들어서 리턴
            ChatRoom chatRoom = ChatRoom.builder()
                    .store1(store1)
                    .store2(store2)
                    .build();
            chatRoomRepository.save(chatRoom);
            ChatMessage message = ChatMessage.builder()
                    .type(ChatMessageType.ENTER)
                    .chatRoom(chatRoom)
                    .author(store1)
                    .receiver(store2)
                    .build();
            chatMessageRepository.save(message);
            chatRoom.updateLastMessage(message);
            return chatRoom;
        } else { // 있으면 꺼내서 리턴
            return chatRoomOptional.get();
        }
    }

}

