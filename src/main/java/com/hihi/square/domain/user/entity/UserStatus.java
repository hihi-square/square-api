package com.hihi.square.domain.user.entity;

public enum UserStatus {
    ACTIVE("정상"),
    STOP("정지"),
    WITHDRAWAL("탈퇴"),
    WAIT("가입대기"),
    REPORT("신고처리");

    private final String status;

    UserStatus(String status) { this.status = status; }
}
