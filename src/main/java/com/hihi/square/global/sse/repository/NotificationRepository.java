package com.hihi.square.global.sse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hihi.square.domain.user.entity.User;
import com.hihi.square.global.sse.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
	List<Notification> findAllByReceiver(User user);
}