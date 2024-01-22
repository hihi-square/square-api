package com.hihi.square.domain.user.dto;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserRes {
    private Integer usrId;
    private String uid;
    private String nickname;
    private String email;
    private String mainAddress;
    private String profileImage;
}
