//package com.hihi.square.domain.buyer.service;
//
//import org.springframework.security.authentication.InternalAuthenticationServiceException;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//
//import javax.naming.AuthenticationException;
//
//public class CustomOAuth2UserService extends DefaultOAuth2UserService {
//
//    // 스프링 시큐리티 OAuth2LoginAuthenticationFilter에서 시작된 OAuth2 인증 과정 중 호출.
//    // 호출 시점은 액세스 토큰을 OAuth2 제공자로부터 받았을 때
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
//        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
//
//        try {
//            return processOAuth2User(oAuth2UserRequest, oAuth2User);
//        } catch (Exception ex) {
//            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
//        }
//    }
//
//
//    private OAuth2User processOAuth2User(OAuth2UserRequest request, OAuth2User oAuth2User) {
//        String registrationId = request.getClientRegistration().getRegistrationId();
//        String accessToken = request.getAccessToken().getTokenValue();
//
//
//    }
//
//}
