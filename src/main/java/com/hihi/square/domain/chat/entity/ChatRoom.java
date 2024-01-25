package com.hihi.square.domain.chat.entity;

import com.hihi.square.common.BaseEntity;
import com.hihi.square.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "chat_room")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class ChatRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "last_message_id")
    private ChatMessage lastMessage;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name="ChatMember", joinColumns = @JoinColumn(name="chat_room_id"))
    private Set<Store> chatRoomMembers = new HashSet<>();

}
