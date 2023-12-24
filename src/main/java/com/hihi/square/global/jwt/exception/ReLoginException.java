package com.hihi.square.global.jwt.exception;

import com.hihi.square.global.error.ErrorCode;
import com.hihi.square.global.error.type.BusinessException;

public class ReLoginException extends BusinessException {
    public ReLoginException(String message) {
        super(message, ErrorCode.TOKEN_NOT_FOUND);
    }
}
