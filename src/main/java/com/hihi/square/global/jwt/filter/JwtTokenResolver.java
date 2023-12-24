package com.hihi.square.global.jwt.filter;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

/**
 * 토큰을 추출하는 역할을 하는 클래스
 */
public class JwtTokenResolver {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}