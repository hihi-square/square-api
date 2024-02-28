package com.hihi.square.domain.chat.service;

import com.hihi.square.domain.chat.dto.request.ChatMessageReq;
import com.hihi.square.domain.chat.dto.response.ChatMessageRes;
import com.hihi.square.domain.chat.entity.ChatMessage;
import com.hihi.square.domain.chat.entity.ChatMessageType;
import com.hihi.square.domain.chat.entity.ChatRoom;
import com.hihi.square.domain.chat.repository.ChatMessageRepository;
import com.hihi.square.domain.chat.repository.ChatRoomRepository;
import com.hihi.square.domain.store.entity.Store;
import com.hihi.square.domain.store.repository.StoreRepository;
import com.hihi.square.global.error.type.EntityNotFoundException;
import com.hihi.square.global.error.type.UserNotFoundException;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{

    private final StoreRepository storeRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    // 텍스트 채팅
    @Override
    public ChatMessageRes addChat(Integer stoId, Long roomId, ChatMessageReq req) {
        Store store = storeRepository.findById(stoId).orElseThrow(() -> new UserNotFoundException("가게 회원을 찾을 수 없습니다."));
        ChatRoom chatRoom = chatRoomRepository.findById(req.getRoomId()).orElseThrow(()->new EntityNotFoundException("채팅룸을 찾을 수 없습니다."));
        ChatMessage chatMessage = ChatMessage.builder()
                .type(req.getType())
                .message(req.getMessage())
                .chatRoom(chatRoom)
                .receiver(chatRoom.getStore1().getUsrId() == store.getUsrId() ? chatRoom.getStore2() : chatRoom.getStore1())
                .author(store)
                .build();
        chatMessageRepository.save(chatMessage);

        ChatMessageRes res = ChatMessageRes.toRes(chatMessage);
        return res;
    }

    @Override
    public List<ChatMessageRes> getAllChats(Integer stoId, Long roomId) {
        Store store = storeRepository.findById(stoId).orElseThrow(() -> new UserNotFoundException("가게 회원을 찾을 수 없습니다."));
        ChatRoom chatRoom = chatRoomRepository.findByIdAndStore(roomId, store).orElseThrow(()->new EntityNotFoundException("채팅룸을 찾을 수 없습니다."));
        List<ChatMessage> chatMessageList = chatMessageRepository.findAllByChatRoom(chatRoom);
        List<ChatMessageRes> res = new ArrayList<>();
        for(ChatMessage m : chatMessageList) res.add(ChatMessageRes.toRes(m));
        return res;
    }
}

