package com.hihi.square.domain.buyer.oauth2.handler;

import com.hihi.square.domain.buyer.service.BuyerService;
import com.hihi.square.global.jwt.token.TokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


import java.io.IOException;

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
