package com.hihi.square.global.util.radis;

public interface RedisService {

	void setValues(String uid, String refreshToken);

	String getValues(String uid);

	void deleteValues(String uid);

	boolean checkExistsValue(String value);

}