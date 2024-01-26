package com.hihi.square.global.sse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.hihi.square.domain.user.entity.UserStatus;
import com.hihi.square.domain.user.repository.UserRepository;
import com.hihi.square.global.error.type.SseNotConnectException;
import com.hihi.square.global.error.type.UserNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.hihi.square.domain.user.entity.User;
import com.hihi.square.global.sse.dto.NotificationDto;
import com.hihi.square.global.sse.entity.Notification;
import com.hihi.square.global.sse.entity.NotificationType;
import com.hihi.square.global.sse.repository.EmitterRepository;
import com.hihi.square.global.sse.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@Slf4j
@RequiredArgsConstructor
public class SseService {
	private final EmitterRepository emitterRepository = new EmitterRepository();
	private final UserRepository userRepository;
	private final NotificationRepository notificationRepository;

	private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;	//SSE 유효시간 : 1초(1000밀리초) * 60 * 60 = 1시간

	public List<Notification> findAll() {
		List<Notification> notificationList = notificationRepository.findAll();
		return notificationList;
	}

	//데이터 유실시점 파악을 위함
	private String makeTimeIncludeId(Integer memberId) {
		return memberId + "_" + System.currentTimeMillis();
	}

	//구독
	public SseEmitter subscribe(Integer userId, String lastEventId, HttpServletResponse response) {
		String emitterId = makeTimeIncludeId(userId);
		//sse 연결 요청에 대한 응답을 위한 SseEmitter 객체
		SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));
		response.setHeader("X-Accel-Buffering", "no"); // NGINX PROXY 에서의 필요설정 : 불필요한 버퍼링방지

		//SseEmitter 완료/시간초과/에러로 인한 전송 불가 시 sseEmitter 삭제
		emitter.onCompletion(() -> emitterRepository.deleteAllStartWithId(emitterId));
		emitter.onTimeout(() -> emitterRepository.deleteAllStartWithId(emitterId));
//		emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
//		emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));
//		emitter.onError((e) -> emitterRepository.deleteById(id));

		//연결 직후, 데이터 없을 시 503 에러를 방지하기 위한 더미 이벤트 전송
		Notification notification = Notification.createNotification(null, NotificationType.READY,
			"EventStream Created. [userId=" + userId + "]", "");

		sendNotification(emitter, emitterId, "message", new NotificationDto(notification));

		// 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
		if (!lastEventId.isEmpty()) {
			sendLostData(lastEventId, userId, emitterId, emitter);
		}

		return emitter;
	}

	private void sendNotification(SseEmitter emitter, String emitterId, String type, Object data) {
		try {
			emitter.send(SseEmitter.event()
				.id(emitterId)
				.name(type)    //front에서 onmessage로 받기 때문에 event 명 설정
				.data(data));
		} catch (IOException exception) {
//			emitterRepository.deleteById(emitterId);
			emitterRepository.deleteAllStartWithId(emitterId);
			throw new SseNotConnectException("SSE Not Connect");
		}
	}

	private void sendLostData(String lastEventId, Integer userId, String emitterId, SseEmitter emitter) {
		Map<String, SseEmitter> eventCaches = emitterRepository.findAllStartWithByUserId(String.valueOf(userId));
		eventCaches.entrySet().stream()
			.filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
				.forEach(entry -> sendNotification(emitter, emitterId, "message", entry.getValue()));
	}

//	@TransactionalEventListener
	@Transactional
	public void send(Integer receiverId, NotificationType notificationType, String type, String content, String data) {
		User receiver = userRepository.findByUserId(receiverId, UserStatus.ACTIVE).orElseThrow(
				() -> new UserNotFoundException("User Not Found"));

		Notification notification = notificationRepository.save(
			Notification.createNotification(receiver, notificationType, content, data));

		//해당 유저의 모든 SseEmitter 조회
		Map<String, SseEmitter> emitters = emitterRepository.findAllStartWithByUserId(String.valueOf(receiverId));
		emitters.forEach(
			(key, emitter) -> {
				//데이터 캐시 저장 -> 유실 데이터 처리
				emitterRepository.saveEventCache(key, notification);
				//데이터 전송
				sendNotification(emitter, key, type, new NotificationDto(notification));
			}
		);
	}
}