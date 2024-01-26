package com.hihi.square.global.sse.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.NoArgsConstructor;

@Repository
@NoArgsConstructor
public class EmitterRepository {
	private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
	private final Map<String, Object> eventCache = new ConcurrentHashMap<>();

	public SseEmitter save(String emitterId, SseEmitter sseEmitter) {
		emitters.put(emitterId, sseEmitter);
		return sseEmitter;
	}

	public void saveEventCache(String eventCacheId, Object event) {
		eventCache.put(eventCacheId, event);
	}

//	public Map<String, SseEmitter> findAllEmitterStartWithByUserId(String memberId) {
//		return emitters.entrySet().stream()
//			.filter(entry -> entry.getKey().startsWith(memberId))
//			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//	}

	public Map<String, SseEmitter> findAllStartWithByUserId(String userId) {
		return emitters.entrySet().stream()
			.filter(entry -> entry.getKey().startsWith(userId))
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

//	public void deleteById(String id) {
//		emitters.remove(id);
//	}

	public void deleteAllStartWithId(String userId) {
		eventCache.forEach((key, emitter) -> {
				if (key.startsWith(userId)) {
					eventCache.remove(key);
				}
			}
		);
	}
}