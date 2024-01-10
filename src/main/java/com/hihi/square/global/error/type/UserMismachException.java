package com.hihi.square.global.error.type;

import com.hihi.square.global.error.ErrorCode;

public class UserMismachException extends BusinessException{
    public UserMismachException(String message) {
        super(message, ErrorCode.USER_MISMATCH);
    }
}
