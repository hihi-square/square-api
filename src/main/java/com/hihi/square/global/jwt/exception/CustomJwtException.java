package com.hihi.square.global.jwt.exception;

import com.hihi.square.global.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomJwtException extends RuntimeException {
	ErrorCode errorCode;
}