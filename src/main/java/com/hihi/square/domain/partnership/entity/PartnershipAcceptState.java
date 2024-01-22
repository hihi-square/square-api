package com.hihi.square.domain.partnership.entity;

// 쿠폰 수락 현황
public enum PartnershipAcceptState {
    WAIT("대기"), // 수락 대기
    NORMAL("정상처리"), // 발급 승인
    REFUSAL("거절"), // 발급 거절
    STOP("발급정지"), // 발급 정지
    CANCELED("취소"); // 상관 없으면

    private final String status;

    PartnershipAcceptState(String status) { this.status = status; }
}
