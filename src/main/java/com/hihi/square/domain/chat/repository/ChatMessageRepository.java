package com.hihi.square.domain.chat.repository;

import com.hihi.square.domain.chat.entity.ChatMessage;
import com.hihi.square.domain.chat.entity.ChatRoom;
import com.hihi.square.domain.store.entity.Store;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    @Query("select message from ChatMessage message join fetch message.author join fetch message.receiver where message.chatRoom = :chatRoom")
    List<ChatMessage> findAllByChatRoom(ChatRoom chatRoom);
}
