package com.hihi.square.domain.menu.entity;

public enum SaleType {
    TIME("타임세일"),
    PARTNERSHIP("제휴세일")
    ;

    private final String status;

    SaleType(String status) { this.status = status; }
}
