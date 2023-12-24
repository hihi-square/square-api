package com.hihi.square.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
//    private final UserRepository userRepository;
//    private final StoreRepository storeRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final TokenProvider tokenProvider;
//    private final AuthenticationManagerBuilder authenticationManagerBuilder;
//    private final RedisService redisService;
//
//    @Override
//    public void checkDuplicateUID(String uid){
//        //이미 존재하는 아이디의 경우
//        if (userRepository.findByUID(uid).orElse(null) != null) {
//            throw new DuplicatedUserException("User is Duplicated");
//        }
//    }
//
//    @Override
//    @Transactional
//    public void join(SignUpReq signUpReq) {
//        //이미 존재하는 아이디의 경우
//        if (userRepository.findByUID(signUpReq.getUid()).orElse(null) != null) {
//            throw new DuplicatedUserException("User is Duplicated");
//        }
//
//        signUpReq.setPassword(passwordEncoder.encode(signUpReq.getPassword()));
//        Store store = Store.toEntity(signUpReq);
//        storeRepository.save(store);
//    }
//
//    @Override
//    public LoginRes login(LoginReq loginReq, HttpServletResponse response) {
//        //사용자 존재 여부 체크
//        User findUser = userRepository.findByUID(loginReq.getUid())
//                .orElseThrow(() -> new UserNotFoundException("User Not Found"));
//
//        // 비밀번호 일치 여부 체크
//        if (!passwordEncoder.matches(loginReq.getPassword(), findUser.getPassword())) {
//            throw new PasswordNotMatchException("Password Not Match");
//        }
//
////        //단일 권한
////        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(findUser.getDecriminatorValue().toString());
////
////        // Authenticate the user
////        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginReq.getUid(), loginReq.getPassword(), Collections.singletonList(grantedAuthority));
////        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
////        SecurityContextHolder.getContext().setAuthentication(authentication);
////        log.debug("authentication : {}", authentication);
////
////        // Create a token for the authenticated user
////        TokenInfo tokenInfo = tokenProvider.createAccessToken(authentication);
////        String refreshToken = tokenProvider.createRefreshToken(authentication);
////        tokenInfo.setRefreshToken(refreshToken);
////        log.debug("tokenInfo : {}", tokenInfo);
////
////        // AccessToken은 Response Body에 담아 클라이언트에게 전달
////        // RefreshToken은 Secure 쿠키에 담아 클라이언트에게 전달
////        tokenProvider.addRefreshTokenCookie(response, tokenInfo.getRefreshToken());
////
////        //redis에 refreshToken 저장
////        redisService.setValues(findUser.getUid(), tokenInfo.getRefreshToken());
//
//        return new LoginRes(findUser.getUid(), findUser.getDecriminatorValue(), tokenInfo);
//    }
}