package com.hihi.square.domain.store.dto.response;

import com.hihi.square.global.jwt.token.TokenInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRes {
    String name;
    String role;
    TokenInfo tokenInfo;
}