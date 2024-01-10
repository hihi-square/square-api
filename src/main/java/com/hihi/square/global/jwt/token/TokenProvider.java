package com.hihi.square.global.jwt.token;

import com.hihi.square.domain.user.repository.UserRepository;
import com.hihi.square.global.error.ErrorCode;
import com.hihi.square.global.error.type.UserNotFoundException;
import com.hihi.square.global.jwt.exception.CustomJwtException;
import com.hihi.square.global.jwt.exception.ReLoginException;
import com.hihi.square.global.jwt.service.CustomUserDetailsService;
import com.hihi.square.global.util.radis.RedisService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider {
    private static final String AUTHORITIES_KEY = "Auth";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TYPE = "Bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 24 * 60 * 60 * 1000L;
//     private static final long ACCESS_TOKEN_EXPIRE_TIME = 60 * 1000L; // 1분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 14 * 24 * 60 * 60 * 1000L; // 2주
//    private static final long REFRESH_TOKEN_EXPIRE_TIME = 10 * 60 * 1000L; // 10분
    private final String secret;
    private Key key;
    private final UserRepository userRepository;
    private final CustomUserDetailsService userDetailsService;
    private final RedisService redisService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public TokenProvider(@Value("${jwt.secret}") String secret,
                            UserRepository userRepository,
                         CustomUserDetailsService userDetailsService,
                         RedisService redisService,
                         AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.secret = secret;
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.redisService = redisService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    public TokenInfo createTokens(String uid, String password, String authority, HttpServletResponse response) {
        //단일 권한
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.toString());

        // Authenticate the user
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(uid, password, Collections.singletonList(grantedAuthority));
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.debug("authentication : {}", authentication);

        // Create a token for the authenticated user
        String accessToken = createAccessToken(authentication);
        String refreshToken = createRefreshToken(authentication);
        TokenInfo tokenInfo = TokenInfo.builder()
                        .accessToken(accessToken)
                                        .grantType(BEARER_TYPE)
                                                .build();
        log.debug("tokenInfo : {}", tokenInfo);

        // AccessToken은 Response Body에 담아 클라이언트에게 전달
        // RefreshToken은 Secure 쿠키에 담아 클라이언트에게 전달
        addRefreshTokenCookie(response, refreshToken);
        return tokenInfo;
    }


    //Authentication 권한 정보 담음 토큰 생성
    public String createAccessToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        com.hihi.square.domain.user.entity.User user = userRepository.findByUID(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException(
                        "User Not Found"));

        // AccessToken 생성
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .claim("uid", user.getUid())
                .claim("userId", user.getUsrId())
                .setExpiration(new Date(now + ACCESS_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // 생성된 토큰을 토큰 dto에 담아 반환
        return accessToken;
    }

    public String createRefreshToken(Authentication authentication){
        String uid = authentication.getName();
        long now = (new Date()).getTime();
        String refreshToken = Jwts.builder()
                .setSubject(authentication.getName())
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        //redis에 refreshToken 저장
        redisService.setValues(uid, refreshToken);

        return refreshToken;
    }

    // Request Header에서 토큰 정보 추출
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_TYPE)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // Request의 Header에서 RefreshToken 값을 가져옵니다. "authorization" : "token'
    public String resolveRefreshToken(HttpServletRequest request) {
        if(request.getHeader("refreshToken") != null )
            return request.getHeader("refreshToken").substring(7);
        return null;
    }

    // JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼냄
    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);
        log.debug("claims={}", claims);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // Claim 에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(
                        claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication 리턴
        Integer userId = claims.get("userId", Integer.class);
        UserDetails principal = new org.springframework.security.core.userdetails.User(userId.toString(), "",
                authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // 토큰을 파싱하여 클레임 형태로 추출
    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    //Refresh Token Cookie에 담아서 전송
    public void addRefreshTokenCookie(HttpServletResponse response, String refreshToken){
        Cookie refreshTokenCookie = new Cookie("RefreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setMaxAge((int) (ACCESS_TOKEN_EXPIRE_TIME)); // 쿠키 만료 시간 설정 (초)
        refreshTokenCookie.setPath("/"); // 쿠키의 유효 경로 설정
        response.addCookie(refreshTokenCookie);
    }

    //쿠키 삭제
    public void removeRefreshTokenCookie(HttpServletResponse response) {
        Cookie refreshTokenCookie = new Cookie("RefreshToken", null);
        refreshTokenCookie.setMaxAge(0);
        refreshTokenCookie.setPath("/"); // Set the cookie path to match the one used for the original set
        response.addCookie(refreshTokenCookie);
    }

    //redis에서 삭제
    public void removeRefreshTokenByRedis(String uid){
        if (redisService.checkExistsValue(uid)){
            // Refresh Token을 삭제
            redisService.deleteValues(uid);
        }
    }

    public Long getExpiration(String accessToken) {
        // accessToken 남은 유효시간
        Date expiration = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody().getExpiration();
        // 현재 시간
        Long now = new Date().getTime();
        return (expiration.getTime() - now);
    }

    //headle Expired Token
    public void handleExpiredToken(HttpServletResponse response, String refreshToken){
        try {
            validateToken(refreshToken);
            String uid = parseClaims(refreshToken).getSubject();
            String redisRTK = redisService.getValues(uid);
            validateToken(redisRTK);

            if (redisRTK.equals(refreshToken)) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(uid);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                String accessToken = createAccessToken(authentication);
                addRefreshTokenCookie(response, refreshToken);
                // 새로운 AccessToken을 헤더에 추가
                response.setHeader("Authorization", "Bearer " + accessToken);
            } else {
                throw new ReLoginException("Does not match refreshToken");
            }
        } catch (ExpiredJwtException ex) {
            throw new ReLoginException("RefreshToken is Expired");
        }
    }

    //토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            //파싱
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            log.info("MalformedJwtException");
            throw new CustomJwtException(ErrorCode.INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            log.info("ExpiredJwtException");
            throw new CustomJwtException(ErrorCode.EXPIRED_TOKEN);
//            throw new CustomJwtException(ErrorCode.RE_LOGIN);
        }
        catch (UnsupportedJwtException e) {
            log.info("UnsupportedJwtException");
            throw new CustomJwtException(ErrorCode.INVALID_TOKEN);
        } catch (IllegalArgumentException e) {
            log.info("IllegalArgumentException");
            throw new CustomJwtException(ErrorCode.INVALID_TOKEN);
        } catch (SignatureException e) {
            log.info("SignatureException");
            throw new CustomJwtException(ErrorCode.INVALID_TOKEN);
        }
    }
}