package com.hihi.square.domain.store.service;

import com.hihi.square.domain.category.dto.CategoryDto;
import com.hihi.square.domain.category.entity.Category;
import com.hihi.square.domain.category.repository.CategoryRepository;
import com.hihi.square.domain.store.dto.StoreDto;
import com.hihi.square.domain.store.dto.request.LoginReq;
import com.hihi.square.domain.store.dto.request.SignUpStoreReq;
import com.hihi.square.domain.store.dto.request.StoreFindReq;
import com.hihi.square.domain.store.dto.response.LoginRes;
import com.hihi.square.domain.store.entity.Store;
import com.hihi.square.domain.store.repository.StoreRepository;
import com.hihi.square.domain.user.entity.User;
import com.hihi.square.domain.user.entity.UserStatus;
import com.hihi.square.domain.user.repository.UserRepository;
import com.hihi.square.global.error.type.*;
import com.hihi.square.global.jwt.exception.ExpiredTokenException;
import com.hihi.square.global.jwt.exception.ReLoginException;
import com.hihi.square.global.jwt.token.TokenInfo;
import com.hihi.square.global.jwt.token.TokenProvider;
import com.hihi.square.global.util.redis.RedisService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService{
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RedisService redisService;

    @Override
    public void checkDuplicateUID(String uid){
        //이미 존재하는 아이디의 경우
        if (storeRepository.findByUID(uid, UserStatus.ACTIVE).orElse(null) != null) {
            throw new DuplicatedUserException("User is Duplicated");
        }

    }

    @Override
    @Transactional
    public void join(SignUpStoreReq signUpStoreReq) {
        //이미 존재하는 아이디의 경우
        if (storeRepository.findByUID(signUpStoreReq.getUid(), UserStatus.ACTIVE).orElse(null) != null) {
            throw new DuplicatedUserException("User is Duplicated");
        }

        signUpStoreReq.setPassword(passwordEncoder.encode(signUpStoreReq.getPassword()));

        //1. default category 설정
        Category category = categoryRepository.findRandomCategory().orElseThrow(
                () -> new EntityNotFoundException("Category Not Found")
        );

        Store store = Store.toEntity(signUpStoreReq, category);
        storeRepository.save(store);
    }

    @Override
    public Map<String, String> findId(StoreFindReq storeFindReq) {
        //1. 해당 이메일을 가진 유저 확인(ACTIVE)
        User user = userRepository.findByEmail(storeFindReq.getEmail(), UserStatus.ACTIVE).orElseThrow(
                () -> new UserNotFoundException("User Not Found")
        );
        //2. 존재한다면 인증번호 이메일 전송
        Map<String, String> response = new HashMap<>();
        response.put("id", user.getUid());
        return response;
    }

    @Override
    public void findPassword(StoreFindReq storeFindReq) {

    }

    @Transactional
    @Override
    public LoginRes login(LoginReq loginReq, HttpServletResponse response) {
        //사용자 존재 여부 체크
        Store findUser = storeRepository.findByUID(loginReq.getUid(), UserStatus.ACTIVE)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        // 비밀번호 일치 여부 체크
        log.info("login password : {}", loginReq.getPassword());
        log.info("login password : {}", passwordEncoder.encode(loginReq.getPassword()));
        log.info("find User password : {}", findUser.getPassword());
        log.info("matches : {}", passwordEncoder.matches(loginReq.getPassword(), findUser.getPassword()));
        if (!passwordEncoder.matches(loginReq.getPassword(), findUser.getPassword())) {
            throw new PasswordNotMatchException("Password Not Match");
        }

        TokenInfo tokenInfo = tokenProvider.createTokens(loginReq.getUid(), loginReq.getPassword(), findUser.getDecriminatorValue(), response);

        return new LoginRes(findUser.getUid(), findUser.getDecriminatorValue(), tokenInfo);
    }

    @Override
    public void recreateToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie refreshTokenCookie = WebUtils.getCookie(request, "RefreshToken");

        if (refreshTokenCookie != null) {
            String refreshToken = refreshTokenCookie.getValue();
            log.info("refreshToken : {}", refreshToken);
            if (StringUtils.hasText(refreshToken)) {
                tokenProvider.handleExpiredToken(response, refreshToken);
            } else {
                // Secure Refresh Token 쿠키가 존재하지 않는 경우
                throw new ReLoginException("Does not Exist Cookie");
            }
        }
        else {
            throw new ReLoginException("Does not Exist Cookie");
        }
    }

    public void deleteToken(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = tokenProvider.resolveToken(request);

        // 1. Access Token 검증
        if(!tokenProvider.validateToken(accessToken)){
            throw new ExpiredTokenException();
        }

        //2. 쿠키에 있는 refresh Token 삭제
        tokenProvider.removeRefreshTokenCookie(response);

        //3. redis에 저장된 refresh token 제거
        String uid = tokenProvider.parseClaims(accessToken).getSubject();
        tokenProvider.removeRefreshTokenByRedis(uid);

        // 4. Access Token blacklist에 등록하여 만료시키기
        // 해당 엑세스 토큰의 남은 유효시간을 얻음
        Long expiration = tokenProvider.getExpiration(accessToken);
        redisService.setBlackList(accessToken, "access_token", expiration);
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response){
        deleteToken(request, response);
    }

    @Override
    @Transactional
    public void updatePassword(Integer userId, String password) {
        //1. 사용자 존재 여부
        User user = userRepository.findByUserId(userId, UserStatus.ACTIVE).orElseThrow(
                () -> new UserNotFoundException("User Not Found")
        );

        //2. 패스워드 변경
        String enPassword = passwordEncoder.encode(password);
        userRepository.updatePassword(user.getUsrId(), enPassword);
    }

    @Override
    public void deleteStore(Integer stoId, Integer pathStoreId, HttpServletRequest request, HttpServletResponse response) {
        storeRepository.deleteById(stoId, UserStatus.WITHDRAWAL);
        deleteToken(request, response);
    }

    @Override
    public void updateStore(Integer stoId, StoreDto storeDto) {
        //1. 사전 확인
        //1-1. 로그인 유저와 조회하려는 유저의 정보 일치 여부
        Store store = storeRepository.findById(storeDto.getId(), UserStatus.ACTIVE).orElseThrow(
                () -> new EntityNotFoundException("User Not Found"));
        if(stoId != storeDto.getId()) throw new UserMismachException("Store MisMatch");
        
        //2. 정보 수정
        Store storeToSave = Store.toEntityByDto(storeDto);
        storeRepository.save(storeToSave);
    }

    @Override
    public StoreDto selectStore(Integer stoId, Integer pathStoreId) {
        //1. 사전 확인
        //1-1. 로그인 유저와 조회하려는 유저의 정보 일치 여부
        Store store = storeRepository.findById(pathStoreId, UserStatus.ACTIVE).orElseThrow(
                () -> new EntityNotFoundException("User Not Found"));
        if(pathStoreId != stoId) throw new UserMismachException("Store MisMatch");

        //2. 정보 반환
        CategoryDto categoryDto = CategoryDto.toRes(store.getCategory());
        StoreDto storeDto = StoreDto.toRes(store, categoryDto);
        return storeDto;
    }
}
