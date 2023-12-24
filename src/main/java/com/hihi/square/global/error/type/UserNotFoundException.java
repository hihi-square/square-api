package com.hihi.square.global.error.type;

import com.hihi.square.global.error.ErrorCode;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException(String message) {
        super(message, ErrorCode.USER_NOT_FOUND);
    }
}
