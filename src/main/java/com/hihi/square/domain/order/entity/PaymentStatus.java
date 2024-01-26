package com.hihi.square.domain.order.entity;

public enum PaymentStatus {
    COMPLETED("완료"),
    FAILED("실패"),
    WAIT("대기");

    private final String status;

    PaymentStatus(String status) { this.status = status; }
}
