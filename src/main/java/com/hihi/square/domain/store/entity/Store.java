package com.hihi.square.domain.store.entity;

import com.hihi.square.domain.store.dto.request.SignUpReq;
import com.hihi.square.domain.user.entity.User;
import com.hihi.square.domain.user.entity.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "store")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder   //부모클래스의 필드를 빌더에 포함
@DiscriminatorValue("STORE")
@ToString
@Getter
public class Store extends User {
    @Column(name = "name")

    String name;
    String phone;
    String storeName;
    String storeContact;
    String storeContact2;
    String content;
    @Enumerated(EnumType.STRING)
    Bank bank;
    String account;
    Integer dibs_count;
    Integer review_count;
    Double rating;
    String address;
    @Column(name = "detail_address")
    String detailAddress;

    public static Store toEntity(SignUpReq signUpReq){
        return Store.builder()
                .uid(signUpReq.getUid())
                .password(signUpReq.getPassword())
                .name(signUpReq.getName())
                .email(signUpReq.getEmail())
                .phone(signUpReq.getPhone())
                .nickname(signUpReq.getStoreName())
                .status(UserStatus.ACTIVE)
                .storeName(signUpReq.getStoreName())
                .content(signUpReq.getContent())
                .storeContact(signUpReq.getStoreContact())
                .storeContact2(signUpReq.getStoreContact2())
                .address(signUpReq.getAddress())
                .detailAddress(signUpReq.getDetailAddress())
                .dibs_count(0)
                .review_count(0)
                .rating(0.0)
                .build();
    }
}
