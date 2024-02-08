package com.hihi.square.domain.chat.repository;

import com.hihi.square.domain.chat.entity.ChatRoom;
import com.hihi.square.domain.store.entity.Store;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("select distinct room from ChatRoom room join fetch room.lastMessage join fetch room.store1 join fetch room.store2 join fetch room.store1.address join fetch room.store2.address join fetch room.store1.address.siggAddress join fetch room.store2.address.siggAddress where room.store1 = :store or room.store2 = :store")
    List<ChatRoom> findByStore(Store store);

    @Query("select room from ChatRoom room join fetch room.lastMessage join fetch room.store1 join fetch room.store2 join fetch room.store1.address join fetch room.store2.address join fetch room.store1.address.siggAddress join fetch room.store2.address.siggAddress where (room.store1 = :store1 and room.store2 = :store2) or (room.store1 = :store2 and room.store2 = :store1)")
    Optional<ChatRoom> findByStores(Store store1, Store store2);

}
