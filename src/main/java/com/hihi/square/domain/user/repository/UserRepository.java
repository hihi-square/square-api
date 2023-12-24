package com.hihi.square.domain.user.repository;

import com.hihi.square.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

   @Query("select u from User u where u.uid=:uid")
   Optional<User> findByUID(@Param("uid") String uid);

}