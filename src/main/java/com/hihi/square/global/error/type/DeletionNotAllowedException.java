package com.hihi.square.global.error.type;

import com.hihi.square.global.error.ErrorCode;

public class DeletionNotAllowedException extends BusinessException{
    public DeletionNotAllowedException(String message) {
        super(message, ErrorCode.DELETE_NOT_ALLOWED);
    }
}
