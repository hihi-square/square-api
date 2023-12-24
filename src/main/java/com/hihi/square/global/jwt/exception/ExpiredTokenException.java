package com.hihi.square.global.jwt.exception;

import com.hihi.square.global.error.ErrorCode;
import com.hihi.square.global.error.type.BusinessException;
import lombok.Getter;

@Getter
public class ExpiredTokenException extends BusinessException {
	public ExpiredTokenException() {
		super(ErrorCode.EXPIRED_TOKEN);
	}
}