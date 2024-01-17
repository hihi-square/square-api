package com.hihi.square.domain.buyer.service;

import com.hihi.square.domain.buyer.dto.LoginRes;
import com.hihi.square.domain.user.entity.User;
import com.hihi.square.domain.user.repository.UserRepository;
import com.hihi.square.global.error.type.UserNotFoundException;
import com.hihi.square.global.jwt.token.TokenInfo;
import com.hihi.square.global.jwt.token.TokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

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
