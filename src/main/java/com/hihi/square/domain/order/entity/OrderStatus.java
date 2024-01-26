package com.hihi.square.domain.order.entity;

public enum OrderStatus {
    REGISTER("등록"),
    WAIT("대기"),
    ACCEPT("수락"),
    REJECT("거절");

    private final String status;

    OrderStatus(String status) { this.status = status; }
}
