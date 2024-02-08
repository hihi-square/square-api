package com.hihi.square.domain.chat.repository;

import com.hihi.square.domain.chat.entity.ChatRoom;
import com.hihi.square.domain.store.entity.Store;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("select distinct room from ChatRoom room join fetch room.lastMessage join fetch room.store1 join fetch room.store2 where room.store1 = :store or room.store2 = :store")
    List<ChatRoom> findByStore(Store store);

}
