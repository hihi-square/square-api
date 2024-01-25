package com.hihi.square.domain.chat.entity;

import com.hihi.square.common.BaseEntity;
import com.hihi.square.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "chat_message")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class ChatMessage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @ManyToOne
    @JoinColumn(name="chat_room_id")
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name="sto_id")
    private Store author;

}
