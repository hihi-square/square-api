package com.hihi.square.global.jwt.exception;

import com.hihi.square.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidTokenException extends CustomJwtException {
	public InvalidTokenException() {
		super(ErrorCode.INVALID_TOKEN);
	}
}