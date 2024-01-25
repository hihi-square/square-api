package com.hihi.square.domain.chat.entity;

import com.hihi.square.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chat_message")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "sto_id")
    private Store store;

}
