package com.hihi.square.global.error.type;

import com.hihi.square.global.error.ErrorCode;

public class DuplicatedUserException extends BusinessException{
    public DuplicatedUserException(String message) {
        super(message, ErrorCode.DUPLICATED_USER);
    }
}
