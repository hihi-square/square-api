package com.hihi.square.global.util.redis;

public interface RedisService {

	void setValues(String uid, String refreshToken);

	String getValues(String uid);

	void deleteValues(String uid);

	boolean checkExistsValue(String value);

	void setBlackList(String key, Object o, Long milliSeconds);

	Object getBlackList(String key);

	boolean deleteBlackList(String key);

	boolean hasKeyBlackList(String key);

}