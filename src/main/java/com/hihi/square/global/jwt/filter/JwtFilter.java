package com.hihi.square.global.jwt.filter;

import com.hihi.square.global.jwt.service.CustomUserDetailsService;
import com.hihi.square.global.jwt.token.TokenProvider;
import com.hihi.square.global.util.radis.RedisService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final TokenProvider tokenProvider;
    private final RedisService redisService;
    private final CustomUserDetailsService userDetailsService;

    public JwtFilter(TokenProvider tokenProvider, RedisService redisService, CustomUserDetailsService userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.redisService = redisService;
        this.userDetailsService = userDetailsService;
    }

    //필터링 로직
    //JWT Token의 인증정보를 현재 실행중인 Security Context에 저장하는 역할
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String accessToken = resolveToken(request);
        String requestURI = request.getRequestURI();
        JwtTokenValidator jwtTokenValidator = new JwtTokenValidator(userDetailsService);
//        try {
        if(!requestURI.equals("/store/reissue")){
            jwtTokenValidator.validateAccessToken(tokenProvider, accessToken, requestURI);
        }
//        }
//        catch (ExpiredJwtException e) {
//            Cookie refreshTokenCookie = WebUtils.getCookie(request, "RefreshToken");
//
//            if (refreshTokenCookie != null) {
//                String refreshToken = refreshTokenCookie.getValue();
//                if (StringUtils.hasText(refreshToken)) {
//                    jwtTokenValidator.handleExpiredToken(response, refreshToken, tokenProvider, redisService);
//                } else {
//                    // Secure Refresh Token 쿠키가 존재하지 않는 경우
//                    JwtErrorResponseSender.sendErrorResponse(response, "Does not Exist Cookie", ErrorCode.RE_LOGIN);
//                    return;
//                }
//            }
//        }

        filterChain.doFilter(request, response);
    }

    //필터링을 위해 토큰 필요
    //request header에서 token 정보 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
