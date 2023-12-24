package com.hihi.square.global.util.radis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisServiceImpl implements RedisService {

	private final RedisTemplate<String, String> redisTemplate;

	public RedisServiceImpl(@Qualifier("customRedisTemplate") RedisTemplate<String, String> redisTemplate){
		this.redisTemplate = redisTemplate;
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
}