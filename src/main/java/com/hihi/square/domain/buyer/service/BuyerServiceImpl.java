package com.hihi.square.domain.buyer.service;

import com.hihi.square.domain.buyer.entity.Buyer;
import com.hihi.square.domain.buyer.entity.LoginMethod;
import com.hihi.square.domain.buyer.oauth2.dto.OAuthAttributes;
import com.hihi.square.domain.buyer.repository.BuyerRepository;
import com.hihi.square.domain.buyer.dto.response.LoginRes;
import com.hihi.square.domain.store.dto.request.LoginReq;
import com.hihi.square.domain.user.entity.Role;
import com.hihi.square.domain.user.entity.User;
import com.hihi.square.domain.user.repository.UserRepository;
import com.hihi.square.global.error.type.UserNotFoundException;
import com.hihi.square.global.jwt.token.TokenInfo;
import com.hihi.square.global.jwt.token.TokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class BuyerServiceImpl implements BuyerService {

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    @Override
    public LoginRes login(Authentication authentication, HttpServletResponse response) {
        User findUser = userRepository.findByUserId(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));
        TokenInfo tokenInfo = tokenProvider.createTokens(findUser.getUid(), "defaultPassword", findUser.getDecriminatorValue(), response);
        return LoginRes.builder()
                .name(authentication.getName())
                .tokenInfo(tokenInfo)
                .role("BUYER")
                .build();
    }
}
