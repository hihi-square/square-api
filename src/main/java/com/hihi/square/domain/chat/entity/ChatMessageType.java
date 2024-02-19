package com.hihi.square.domain.chat.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import com.hihi.square.common.CommonStatus;

import java.util.Arrays;
import java.util.List;

public enum ChatMessageType {
    ENTER("입장"),
    TALK("메세지"),
    FILE("파일"),
    IMAGE("이미지"),
    LINK("링크");

    private final String type;

    ChatMessageType(String type) { this.type = type; }

    @JsonValue
    public String getType() {
        return this.toString();
    }
}
