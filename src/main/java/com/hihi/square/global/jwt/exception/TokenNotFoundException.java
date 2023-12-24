package com.hihi.square.global.jwt.exception;

import com.hihi.square.global.error.ErrorCode;
import com.hihi.square.global.error.type.BusinessException;

public class TokenNotFoundException extends BusinessException {
	public TokenNotFoundException(String message) {
		super(message, ErrorCode.TOKEN_NOT_FOUND);
	}
}