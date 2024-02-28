package com.hihi.square.global.error.type;

import com.hihi.square.global.error.ErrorCode;

public class AddNotAllowedException extends BusinessException{
    public AddNotAllowedException(String message) {
        super(message, ErrorCode.ADD_NOT_ALLOWED);
    }
}
