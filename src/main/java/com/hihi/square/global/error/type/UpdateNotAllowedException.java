package com.hihi.square.global.error.type;

import com.hihi.square.global.error.ErrorCode;

public class UpdateNotAllowedException extends BusinessException{
    public UpdateNotAllowedException(String message) {
        super(message, ErrorCode.UPDATE_NOT_ALLOWED);
    }
}
