package com.hihi.square.domain.buyer.oauth2.dto;

import com.hihi.square.domain.buyer.entity.Buyer;
import com.hihi.square.domain.buyer.entity.LoginMethod;
import com.hihi.square.domain.user.entity.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String uid;
    private String email;
    private String nickname;
    private String profileImage;
    private LoginMethod method;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String uid, String email, String nickname, String profileImage, LoginMethod method) {
        this.attributes = attributes;
        this.nameAttributeKey=nameAttributeKey;
        this.uid = uid;
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.method = method;
    }

    public static OAuthAttributes of(String registrationId, String nameAttributeKey, Map<String, Object> attributes) {
        System.out.println(registrationId);
        // 카카오 로그인이라면
        if (registrationId.equals("KAKAO"))
            return ofKakao(nameAttributeKey, attributes);
        // 카카오 로그인이라면
        else if (registrationId.equals("naver"))
            return ofNaver(nameAttributeKey, attributes);
        return ofKakao(nameAttributeKey, attributes);
    }

    private static OAuthAttributes ofKakao(String nameAttributeKey, Map<String, Object> attributes) {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        Map<String, Object> kakao_account = (Map<String, Object>) attributes.get("kakao_account");
        return OAuthAttributes.builder()
                .attributes(attributes)
                .nameAttributeKey(nameAttributeKey)
                .uid("KAKAO".concat(String.valueOf(attributes.get("id"))))
                .email((String) kakao_account.get("email"))
                .profileImage((String) properties.get("profile_image"))
                .nickname((String) properties.get("nickname"))
                .method(LoginMethod.KAKAO)
                .build();
    }
    private static OAuthAttributes ofNaver(String nameAttributeKey, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return OAuthAttributes.builder()
                .attributes(attributes)
                .nameAttributeKey(nameAttributeKey)
                .uid("NAVER".concat(String.valueOf(response.get("id"))))
                .email((String) response.get("email"))
                .profileImage((String) response.get("profile_image"))
                .nickname((String) response.get("nickname"))
                .method(LoginMethod.NAVER)
                .build();
    }

    public Buyer toEntity(LoginMethod method) {
        return Buyer.builder()
                .uid(uid)
                .nickname(nickname)
                .email(email)
                .profileImage(profileImage)
                .status(UserStatus.ACTIVE)
                .method(method)
                .build();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("attributes", this.getAttributes());
        map.put("nameAttributeKey", this.getNameAttributeKey());
        map.put("uid", this.getUid());
        map.put("email", this.getEmail());
        map.put("profileImage", this.getProfileImage());
        map.put("nickname", this.getNickname());
        map.put("method", this.getMethod());
        return map;
    }

}
