package com.hihi.square.domain.buyer.oauth2.service;

import com.hihi.square.domain.buyer.entity.Buyer;
import com.hihi.square.domain.buyer.oauth2.dto.OAuthAttributes;
import com.hihi.square.domain.buyer.repository.BuyerRepository;
import com.hihi.square.global.jwt.token.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService  implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final BuyerRepository buyerRepository;
    private final TokenProvider tokenProvider;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 기본 OAuth2UserService 객체 생성한다
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        // OAuth2UserService를 사용하여 OAuth2User 정보를 가져온다
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 클라이언트 등록 ID(kakao, naver, google)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        log.info(registrationId+" 로그인 시도");

        // OAuth2 로그인 진행 시 키가 되는 필드 값(PK)
        // 예를 들어 구글의 경우에는 sub이다
        // 근데 우리 어플리케이션의 경우 uid가 있기 때문에 이걸로 판별해도 괜찮을 듯
        String userNameAttributeName = "uid";
        // OAuth2UserService를 사용하여 가져온  OAuth2User정보를 OAuth2Attribute 객체를 만든다
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        // 저장
        Buyer buyer = save(attributes);

        // 여기서 리턴해주는 값을 successHandler에서 authentication 객체에서 확인할 수 있음.
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(buyer.getDecriminatorValue()))
                , attributes.toMap()
                , attributes.getNameAttributeKey());
    }

    private Buyer save(OAuthAttributes attributes) {
        Buyer buyer = buyerRepository.findByUidAndMethod(attributes.getUid(), attributes.getMethod())
                // 우리 프로젝트에서는 유저의 닉네임/사진에 대한 실시간 정보가 필요 없기 때문에 update는 하지 않는다.
                .orElse(attributes.toEntity(attributes.getMethod()));
        return buyerRepository.save(buyer);
    }
}
