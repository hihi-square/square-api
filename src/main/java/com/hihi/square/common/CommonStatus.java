package com.hihi.square.common;

import java.util.Arrays;
import java.util.List;

public enum CommonStatus {
    ACTIVE("정상"),
    DELETE("삭제"),
    PRIVATE("비공개"),
    REPORT("신고처리");

    private final String status;
    public static final List<CommonStatus> activeAndPrivate = Arrays.asList(CommonStatus.ACTIVE, CommonStatus.PRIVATE);

    CommonStatus(String status) { this.status = status; }
}
