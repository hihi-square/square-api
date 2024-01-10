package com.hihi.square.domain.buyer.oauth2.dto;

import com.hihi.square.domain.buyer.entity.Buyer;
import com.hihi.square.domain.buyer.entity.LoginMethod;
import com.hihi.square.domain.user.entity.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

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
        return ofKakao(nameAttributeKey, attributes);
    }

    private static OAuthAttributes ofKakao(String nameAttributeKey, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .attributes(attributes)
                .nameAttributeKey(nameAttributeKey)
                .uid((String) attributes.get("id"))
                .email((String) attributes.get("account_email"))
                .profileImage((String) attributes.get("profile_image"))
                .nickname((String) attributes.get("profile_nickname"))
                .method(LoginMethod.KAKAO)
                .build();
    }



    public Buyer toEntity() {
        return Buyer.builder()
                .uid(uid)
                .nickname(nickname)
                .email(email)
                .profileImage(profileImage)
                .status(UserStatus.ACTIVE)
                .mainAddress("대전 유성구 덕명동")
                .method(LoginMethod.KAKAO)
                .build();
    }

}
