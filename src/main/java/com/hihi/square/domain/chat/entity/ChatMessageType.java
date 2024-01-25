package com.hihi.square.domain.chat.entity;

import com.hihi.square.common.CommonStatus;

import java.util.Arrays;
import java.util.List;

public enum ChatMessageType {
    TALK("메세지"),
    FILE("파일"),
    IMAGE("이미지"),
    LINK("링크");

    private final String status;

    ChatMessageType(String status) { this.status = status; }
}
