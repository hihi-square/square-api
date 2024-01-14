package com.hihi.square.global.util.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
//@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

	private final RedisTemplate<String, String> redisTemplate;
	private final RedisTemplate<String, Object> redisBlackListTemplate;

	public RedisServiceImpl(@Qualifier("customRedisTemplate") RedisTemplate<String, String> redisTemplate,
							@Qualifier("customRedisBlackListTemplate") RedisTemplate<String, Object> redisBlackListTemplate){
		this.redisTemplate = redisTemplate;
		this.redisBlackListTemplate = redisBlackListTemplate;
	}

	@Override
	public void setValues(String uid, String refreshToken) {
		redisTemplate.opsForValue().set(uid, refreshToken, 14, TimeUnit.DAYS);
		// 4. RefreshToken Redis 저장 (expirationTime 설정을 통해 자동 삭제 처리)
	}

	@Override
	public String getValues(String uid) {
		return redisTemplate.opsForValue().get(uid);
	}

	@Override
	public void deleteValues(String uid) {
		redisTemplate.delete(uid);
	}

	@Override
	public boolean checkExistsValue(String value) {
		return !value.equals("false");
	}

	@Override
	public void setBlackList(String key, Object o, Long milliSeconds) {
		redisBlackListTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(o.getClass()));
		redisBlackListTemplate.opsForValue().set(key, o, milliSeconds, TimeUnit.MILLISECONDS);
	}

	@Override
	public Object getBlackList(String key) {
		return redisBlackListTemplate.opsForValue().get(key);
	}

	@Override
	public boolean deleteBlackList(String key) {
		return redisBlackListTemplate.delete(key);
	}

	@Override
	public boolean hasKeyBlackList(String key) {
		return redisBlackListTemplate.hasKey(key);
	}
}