package com.hihi.square.global.jwt.filter;

import com.hihi.square.global.error.ErrorCode;
import com.hihi.square.global.jwt.response.JwtErrorResponseSender;
import com.hihi.square.global.jwt.service.CustomUserDetailsService;
import com.hihi.square.global.jwt.token.TokenProvider;
import com.hihi.square.global.util.redis.RedisService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

/**
 * 토큰의 유효성을 검사하는 역할을 하는 클래스
 */
@Slf4j
public class JwtTokenValidator {
    public static CustomUserDetailsService userDetailsService;

    public JwtTokenValidator(CustomUserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }

    public void validateAccessToken(HttpServletResponse response, TokenProvider tokenProvider, RedisService redisService, String accessToken, String requestURI) throws ExpiredJwtException {
        if (StringUtils.hasText(accessToken) && tokenProvider.validateToken(accessToken)) {
            if(redisService.getBlackList(accessToken) == null){ //로그아웃한 유저가 아닌 경우
                Authentication authentication = tokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
            }
            else{
                log.info("유효한 JWT가 없습니다.");
                JwtErrorResponseSender.sendErrorResponse(response, "ACCESS DENIED", ErrorCode.ACCESS_DENIED);
            }
        }
    }
//
//    public void handleExpiredToken(HttpServletResponse response, String refreshToken, TokenProvider tokenProvider, RedisService redisService) throws IOException {
//        try {
//            tokenProvider.validateToken(refreshToken);
//            String uid = tokenProvider.parseClaims(refreshToken).getSubject();
//            String redisRTK = redisService.getValues(uid);
//            tokenProvider.validateToken(redisRTK);
//
//            if (redisRTK.equals(refreshToken)) {
//                UserDetails userDetails = userDetailsService.loadUserByUsername(uid);
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
////                TokenInfo newTokenInfo = tokenProvider.createAccessToken(authentication);
//                String accessToken = tokenProvider.createAccessToken(authentication);
//                tokenProvider.addRefreshTokenCookie(response, refreshToken);
//                // 새로운 AccessToken을 헤더에 추가
//                response.setHeader("Authorization", "Bearer " + accessToken);
////                JwtErrorResponseSender.sendErrorResponse(response, accessToken, ErrorCode.EXPIRED_TOKEN);
//            } else {
//                JwtErrorResponseSender.sendErrorResponse(response, "Does not match refreshToken", ErrorCode.RE_LOGIN);
//            }
//        } catch (ExpiredJwtException ex) {
//            JwtErrorResponseSender.sendErrorResponse(response, "RefreshToken is Expired", ErrorCode.RE_LOGIN);
//        }
//    }
}