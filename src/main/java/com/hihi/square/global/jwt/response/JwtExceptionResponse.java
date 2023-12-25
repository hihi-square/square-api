//package com.hihi.square.global.jwt.response;
//
//import com.hihi.square.global.error.ErrorCode;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//
///**
// * 오류 응답을 전송하는 역할을 하는 클래스
// */
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class JwtExceptionResponse {
//    private String message;
//    private int status;
//    private String code;
//    private String data;
//
//    public static JwtExceptionResponse error(ErrorCode errorCode, String data) {
//        return JwtExceptionResponse.builder()
//                .message(errorCode.getMessage())
//                .status(errorCode.getStatus())
//                .code(errorCode.getCode())
//                .data(data)
//                .build();
//    }
//}
