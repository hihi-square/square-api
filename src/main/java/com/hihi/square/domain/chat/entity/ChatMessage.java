package com.hihi.square.domain.chat.entity;

import com.hihi.square.common.BaseEntity;
import com.hihi.square.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_message")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@EntityListeners(AuditingEntityListener.class)
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ChatMessageType type; // TALK, FILE, IMAGE, LINK

    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="chat_room_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sto_id")
    private Store author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="receiver_id")
    private Store receiver;

    private String url;

    @CreationTimestamp
    @Column(name = "time", updatable = false)
    private LocalDateTime time;
}
