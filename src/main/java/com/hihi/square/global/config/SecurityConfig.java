package com.hihi.square.global.config;

import com.hihi.square.global.jwt.JwtAccessDeniedHandler;
import com.hihi.square.global.jwt.JwtAuthenticationEntryPoint;
import com.hihi.square.global.jwt.filter.JwtExceptionFilter;
import com.hihi.square.global.jwt.filter.JwtFilter;
import com.hihi.square.global.jwt.service.CustomUserDetailsService;
import com.hihi.square.global.jwt.token.TokenProvider;
import com.hihi.square.global.util.radis.RedisService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableMethodSecurity //@PreAuthorize를 메서드로 추가하기 위해서
@Configuration
public class SecurityConfig {
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final RedisService redisService;
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(
            TokenProvider tokenProvider,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler,
            RedisService redisService,
            CustomUserDetailsService userDetailsService
    ) {
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.redisService = redisService;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())

                //로그인, 회원가입 API
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers("/store/join/**", "/store/login", "/store/reissue", "/buyer/join/**", "/buyer/login").permitAll()
                        .requestMatchers("/store/**").hasAuthority("STORE")
                        .requestMatchers("/buyer/**").hasAuthority("BUYER")
                        .anyRequest().authenticated()
                )

                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                //JwtFilter를 addFilterBefore로 등록했던 JwtSecurityConfig 클래스 적용
                .addFilterBefore(new JwtFilter(tokenProvider, redisService, userDetailsService), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtExceptionFilter(), JwtFilter.class)
                //예외 핸들링 -> 만든 클래스
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                );

        return http.build();
    }
}