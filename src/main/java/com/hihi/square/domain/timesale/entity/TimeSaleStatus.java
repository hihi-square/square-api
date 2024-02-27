package com.hihi.square.domain.timesale.entity;

import com.hihi.square.common.CommonStatus;

import java.util.Arrays;
import java.util.List;

public enum TimeSaleStatus {
    PREPARATION("준비"),
    ONGOING("진행중"),
    END("종료"),
    PAUSE("중지"),
    CANCEL("취소")
    ;

    private final String status;
    public static final List<TimeSaleStatus> prepareAndOngoing = Arrays.asList(TimeSaleStatus.PREPARATION, TimeSaleStatus.ONGOING);

    TimeSaleStatus(String status) { this.status = status; }
}
