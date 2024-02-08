package com.hihi.square.domain.chat.dto.response;

import com.hihi.square.domain.chat.entity.ChatMessage;
import com.hihi.square.domain.store.dto.response.StoreInfoRes;
import com.hihi.square.domain.store.dto.response.StoreRes;
import com.hihi.square.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@Getter
public class ChatRoomRes {
    private Long id;
    private ChatMessageRes lastMessage;
    private StoreRes store;
}
