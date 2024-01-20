package com.hihi.square.global.config;

import com.hihi.square.domain.buyer.oauth2.handler.OAuth2AuthenticationFailureHandler;
import com.hihi.square.domain.buyer.oauth2.handler.OAuth2AuthenticationSuccessHandler;
import com.hihi.square.domain.buyer.oauth2.service.CustomOAuth2UserService;
import com.hihi.square.global.jwt.JwtAccessDeniedHandler;
import com.hihi.square.global.jwt.JwtAuthenticationEntryPoint;
import com.hihi.square.global.jwt.filter.JwtExceptionFilter;
import com.hihi.square.global.jwt.filter.JwtFilter;
import com.hihi.square.global.jwt.service.CustomUserDetailsService;
import com.hihi.square.global.jwt.token.TokenProvider;
import com.hihi.square.global.util.redis.RedisService;
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
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    public SecurityConfig(
            TokenProvider tokenProvider,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler,
            RedisService redisService,
            CustomUserDetailsService userDetailsService,
            CustomOAuth2UserService customOAuth2UserService, OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler, OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler) {
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.redisService = redisService;
        this.userDetailsService = userDetailsService;
        this.customOAuth2UserService = customOAuth2UserService;
        this.oAuth2AuthenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
        this.oAuth2AuthenticationFailureHandler = oAuth2AuthenticationFailureHandler;
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
                        .requestMatchers("/store/join/**", "/store/login", "/store/reissue",  "/**/oauth2/**").permitAll()
                        .requestMatchers("/store/**").hasAuthority("STORE")
                        .requestMatchers("/partnerships/**").hasAuthority("STORE")
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
                )

                // 로그아웃. 추후 주석 제거
//                .logout((logout) -> logout.logoutSuccessUrl("/"))

                 // 스프링 oauth2 로그인.
                .oauth2Login((oauth2) -> {
                    oauth2
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint  // oauth2 로그인 성공 이후 사용자 정보를 가져올 때 설정 담당
                                .userService(customOAuth2UserService));
                        oauth2.successHandler(oAuth2AuthenticationSuccessHandler);
                        oauth2.failureHandler(oAuth2AuthenticationFailureHandler);
                }) // oauth2 로그인 성공 시 작업을 진행할 서비스
//                        .defaultSuccessUrl("/buyer/login/oauth", true)); // OAuth2 성공시 들어갈 controller

        ;

        return http.build();
    }
}