package com.hihi.square.domain.timesale.entity;

public enum TimeSaleStatus {
    PREPARATION("준비"),
    ONGOING("진행중"),
    END("종료"),
    PAUSE("중지"),
    CANCEL("취소")
    ;

    private final String status;

    TimeSaleStatus(String status) { this.status = status; }
}
