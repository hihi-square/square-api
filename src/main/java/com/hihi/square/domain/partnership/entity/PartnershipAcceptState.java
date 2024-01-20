package com.hihi.square.domain.partnership.entity;

// 쿠폰 수락 현황
public enum PartnershipAcceptState {
    WAIT("대기"), // 수락 대기
    ACCEPTED("수락"), // 발급 승인
    REFUSAL("거절"), // 발급 거절
    STOP("발급정지"), // 발급 정지
    FINISHED("종료"), // 발급 종료
    CANCELED("취소"); // 일정 시간 내에 수락/거절 하지 않았을 경우 

    private final String status;

    PartnershipAcceptState(String status) { this.status = status; }
}
