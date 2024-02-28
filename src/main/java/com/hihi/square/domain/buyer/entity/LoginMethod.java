package com.hihi.square.domain.buyer.entity;

public enum LoginMethod {
    KAKAO("카카오"),
    NAVER("네이버"),
    GOOGLE("구글");

    private final String method;

    LoginMethod(String method) { this.method = method; }
}
