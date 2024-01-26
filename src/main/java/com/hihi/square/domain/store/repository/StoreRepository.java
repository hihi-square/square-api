package com.hihi.square.domain.store.repository;

import com.hihi.square.domain.store.entity.Store;
import com.hihi.square.domain.user.entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Integer> {

    @Query("select s from Store s where s.usrId=:userId and s.status=:status")
    Optional<Store> findById(@Param("userId") Integer userId, @Param("status") UserStatus status);

    @Query("select s from Store s where s.uid=:uid and s.status=:status")
    Optional<Store> findByUID(@Param("uid") String uid, @Param("status") UserStatus status);

    @Transactional
    @Modifying
    @Query("update Store s set s.status=:status where s.uid=:uid")
    void deleteByUid(@Param("uid") String uid, @Param("status") UserStatus status);

    @Modifying
    @Query("update Store s set s.status=:status where s.usrId=:id")
    void deleteById(@Param("id") Integer id, @Param("status") UserStatus status);
}
