package com.hihi.square.global.sse.dto;

import com.hihi.square.global.sse.entity.Notification;

import lombok.Builder;
import lombok.Data;

@Data
public class NotificationDto {
	private Long id;
	private String content;
	private String storeName;
	private Boolean isRead;

	@Builder
	public NotificationDto(Notification notification) {
		this.id = notification.getId();
		this.content = notification.getContent();
		this.storeName = notification.getData();
		this.isRead = notification.getIsRead();
	}
}