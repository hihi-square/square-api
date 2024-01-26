package com.hihi.square.global.error.type;

import com.hihi.square.global.error.ErrorCode;

public class SseNotConnectException extends BusinessException{
    public SseNotConnectException(String message) {
        super(message, ErrorCode.SSE_NOT_CONNECT);
    }
}
