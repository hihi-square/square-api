package com.hihi.square.domain.user.repository;

import com.hihi.square.domain.user.entity.User;
import com.hihi.square.domain.user.entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

   @Query("select u from User u where u.uid=:uid and u.status='ACTIVE'")
   Optional<User> findByUID(@Param("uid") String uid);

   @Query("select u from User u where u.usrId=:usrId and u.status=:status")
   Optional<User> findByUserId(@Param("usrId") Integer usrId, @Param("status") UserStatus status);

   @Query("select u from User u where u.email=:email and u.status=:status")
   Optional<User> findByEmail(@Param("email") String email, @Param("status") UserStatus status);

   @Transactional
   @Modifying
   @Query("update User u set u.password=:password where u.usrId=:userId")
   void updatePassword(@Param("userId") Integer userId, @Param("password") String password);

}