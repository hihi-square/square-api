package com.hihi.square.domain.buyer.dto;

import com.hihi.square.global.jwt.token.TokenInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRes {
    String name;
    String role;
    TokenInfo tokenInfo;
}