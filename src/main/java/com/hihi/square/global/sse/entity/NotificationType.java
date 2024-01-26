package com.hihi.square.global.sse.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum NotificationType {
	//보류
	READY,
	//수락
	ACCEPT,
	//거절
	REJECT,
	//완료
	COMPLETED,
	//쪽지
	MESSAGE;

	@JsonCreator
	public static NotificationType from(String value) {
		for (NotificationType status : NotificationType.values()) {
			if (status.name().equals(value)) {
				return status;
			}
		}
		return null;
	}
}