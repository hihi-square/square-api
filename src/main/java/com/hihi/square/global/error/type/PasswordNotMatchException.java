package com.hihi.square.global.error.type;

import com.hihi.square.global.error.ErrorCode;
public class PasswordNotMatchException extends BusinessException {
    public PasswordNotMatchException(String message) {
        super(message, ErrorCode.PASSWORD_NOT_MATCH);
    }
}
