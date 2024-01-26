package com.hihi.square.global.sse.entity;

import com.hihi.square.common.BaseEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.hihi.square.domain.user.entity.User;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "notification")
@EqualsAndHashCode(of = "id", callSuper = false)
public class Notification extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ala_id")
	private Long id;

	private String content;

	@Column(name = "related_url")
	private String data;

	@Column(name = "is_read", nullable = false)
	private Boolean isRead;

	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false)
	private NotificationType notificationType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usr_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User receiver;

	@Builder
	public Notification(User receiver, NotificationType notificationType, String content, String data,
		Boolean isRead) {
		this.receiver = receiver;
		this.notificationType = notificationType;
		this.content = content;
		this.data = data;
		this.isRead = isRead;
	}

	public static Notification createNotification(User receiver, NotificationType notificationType, String content,
											String data) {
		return Notification.builder()
				.receiver(receiver)
				.notificationType(notificationType)
				.content(content)
				.data(data)
				.isRead(false)
				.build();
	}
}