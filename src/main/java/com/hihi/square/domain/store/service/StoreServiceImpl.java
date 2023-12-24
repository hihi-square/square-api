package com.hihi.square.domain.store.service;

import com.hihi.square.domain.store.entity.Store;
import com.hihi.square.domain.store.repository.StoreRepository;
import com.hihi.square.domain.user.dto.request.LoginReq;
import com.hihi.square.domain.user.dto.request.SignUpReq;
import com.hihi.square.domain.user.dto.response.LoginRes;
import com.hihi.square.domain.user.entity.User;
import com.hihi.square.domain.user.repository.UserRepository;
import com.hihi.square.global.error.type.DuplicatedUserException;
import com.hihi.square.global.error.type.PasswordNotMatchException;
import com.hihi.square.global.error.type.UserNotFoundException;
import com.hihi.square.global.jwt.token.TokenInfo;
import com.hihi.square.global.jwt.token.TokenProvider;
import com.hihi.square.global.util.radis.RedisService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService{
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Override
    public void save(Store store){
        storeRepository.save(store);
    }

    @Override
    public void checkDuplicateUID(String uid){
        //이미 존재하는 아이디의 경우
        if (userRepository.findByUID(uid).orElse(null) != null) {
            throw new DuplicatedUserException("User is Duplicated");
        }
    }

    @Override
    @Transactional
    public void join(SignUpReq signUpReq) {
        //이미 존재하는 아이디의 경우
        if (userRepository.findByUID(signUpReq.getUid()).orElse(null) != null) {
            throw new DuplicatedUserException("User is Duplicated");
        }

        signUpReq.setPassword(passwordEncoder.encode(signUpReq.getPassword()));
        Store store = Store.toEntity(signUpReq);
        storeRepository.save(store);
    }

    @Transactional
    @Override
    public LoginRes login(LoginReq loginReq, HttpServletResponse response) {
        //사용자 존재 여부 체크
        User findUser = userRepository.findByUID(loginReq.getUid())
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        // 비밀번호 일치 여부 체크
        if (!passwordEncoder.matches(loginReq.getPassword(), findUser.getPassword())) {
            throw new PasswordNotMatchException("Password Not Match");
        }

        TokenInfo tokenInfo = tokenProvider.createTokens(loginReq.getUid(), loginReq.getPassword(), findUser.getDecriminatorValue(), response);

        return new LoginRes(findUser.getUid(), findUser.getDecriminatorValue(), tokenInfo);
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
//        String accessToken = tokenProvider.resolveToken(request);
//
//        // Delete Cookie
//        tokenProvider.removeRefreshTokenCookie(response);
//
//        // Extract uid from AccessToken and delete RefreshToken from Redis
//        String uid = tokenProvider.parseClaims(accessToken).getSubject();
//        tokenProvider.removeRefreshTokenByRedis(uid);
//
//        // 해당 Access Token 유효시간을 가지고 와서 BlackList에 저장하기
//        Long expiration = tokenProvider.getExpiration(accessToken);
//        redisTemplate.opsForValue().set(tokenRequestDto.getAccessToken(),"logout",expiration,TimeUnit.MILLISECONDS);
    }
}
