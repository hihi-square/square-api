package com.hihi.square.domain.timesale.entity;

public enum TimeSaleType {
    PERCENT("정률제"),
    FIXED("정액제")
    ;

    private final String status;

    TimeSaleType(String status) { this.status = status; }
}
