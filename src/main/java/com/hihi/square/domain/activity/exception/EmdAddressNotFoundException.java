package com.hihi.square.domain.activity.exception;

import com.hihi.square.global.error.ErrorCode;
import com.hihi.square.global.error.type.BusinessException;

public class EmdAddressNotFoundException extends BusinessException {
    public EmdAddressNotFoundException(String message) {
        super(message, ErrorCode.USER_NOT_FOUND);
    }
}