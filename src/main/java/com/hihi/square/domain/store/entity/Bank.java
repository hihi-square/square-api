package com.hihi.square.domain.store.entity;

import lombok.Getter;

@Getter
public enum Bank {
    KB("KB국민은행"),
    SHINHAN("신한은행"),
    WOORI("우리은행"),
    NH("NH농협은행");

    private final String displayName;

    Bank(String displayName) {
        this.displayName = displayName;
    }

}
