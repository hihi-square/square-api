package com.hihi.square.domain.buyer.oauth2.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hihi.square.common.CommonRes;
import com.hihi.square.domain.buyer.dto.response.LoginRes;
import com.hihi.square.domain.buyer.service.BuyerService;
import com.hihi.square.global.jwt.token.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.internal.DefaultUpdateEventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final BuyerService buyerService;
    private final TokenProvider tokenProvider;
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("social login success");
        String accessToken = tokenProvider.createOAuthAccessToken(authentication);
        String uri = "http://localhost:3000/success?token="+accessToken;
        redirectStrategy.sendRedirect(request, response, uri);
    }

}
