package com.hihi.square.global.sse;

import java.util.ArrayList;
import java.util.List;

import com.hihi.square.common.CommonRes;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.hihi.square.domain.user.service.UserService;
import com.hihi.square.global.sse.dto.NotificationDto;
import com.hihi.square.global.sse.entity.Notification;
import com.hihi.square.global.sse.entity.NotificationType;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class SseController {
	private final SseService sseService;
	private final UserService userService;

	//구독 요청
    //last-event-id 미수신 event 유실 방지
	@GetMapping(value = "/subscribe", produces = "text/event-stream")
//	@ResponseStatus(HttpStatus.OK)
	public SseEmitter subscribe(Authentication authentication,
								@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId,
								HttpServletResponse response) {
		Integer userId = Integer.parseInt(authentication.getName());
		return sseService.subscribe(userId, lastEventId, response);
	}

	//알림 전체 조회
	@GetMapping("/all")
	public ResponseEntity<?> getAllNotification() {
		List<Notification> notificationList = sseService.findAll();
		List<NotificationDto> responseList = new ArrayList<>();
		for (Notification notification : notificationList) {
			responseList.add(new NotificationDto(notification));
		}

		return ResponseEntity.ok(CommonRes.success(responseList));
	}

	//알림 전송
	@PostMapping("/send-data/{userId}")
	public void sendData(@PathVariable Integer userId) {
		sseService.send(userId, NotificationType.READY, "test", "test", "data");
	}
}