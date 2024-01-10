package com.hihi.square.global.jwt.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hihi.square.global.error.ErrorCode;
import com.hihi.square.global.error.ErrorRes;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;

import java.io.IOException;

/**
 * 오류 응답을 전송하는 역할을 하는 클래스
 */
public class JwtErrorResponseSender {
    public static void sendErrorResponse(HttpServletResponse response, String data, ErrorCode errorCode) {
        try {
            ErrorRes errorResponse = ErrorRes.of(errorCode);
            response.setStatus(errorCode.getStatus());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);

//            new ObjectMapper().writeValue(response.getOutputStream(), JwtExceptionResponse.error(errorCode, data));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}