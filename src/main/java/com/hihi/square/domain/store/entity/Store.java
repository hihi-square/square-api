package com.hihi.square.domain.store.entity;

import com.hihi.square.domain.activity.entity.EmdAddress;
import com.hihi.square.domain.category.entity.Category;
import com.hihi.square.domain.store.dto.StoreDto;
import com.hihi.square.domain.store.dto.request.SignUpStoreReq;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emd_id")
    EmdAddress address;

    @Column(name = "detail_address")
    String detailAddress;
    @Column(name = "min_pickup_time")
    Integer minPickUpTime;
    @Column(name = "max_pickup_time")
    Integer maxPickUpTime;
    String image;
    @OneToOne
    @JoinColumn(name = "category")
    Category category;
    Double latitude;
    Double longitude;

    public static Store toEntity(SignUpStoreReq signUpStoreReq, Category category, EmdAddress emdAddress){
        return Store.builder()
                .uid(signUpStoreReq.getUid())
                .password(signUpStoreReq.getPassword())
                .name(signUpStoreReq.getName())
                .email(signUpStoreReq.getEmail())
                .phone(signUpStoreReq.getPhone())
                .nickname(signUpStoreReq.getStoreName())
                .status(UserStatus.ACTIVE)
                .storeName(signUpStoreReq.getStoreName())
                .content(signUpStoreReq.getContent())
                .storeContact(signUpStoreReq.getStoreContact())
                .storeContact2(signUpStoreReq.getStoreContact2())
                .address(emdAddress)
                .detailAddress(signUpStoreReq.getDetailAddress())
                .dibs_count(0)
                .review_count(0)
                .rating(0.0)
                .minPickUpTime(0)
                .maxPickUpTime(0)
                .category(category)
                .latitude(signUpStoreReq.getLatitude())
                .longitude(signUpStoreReq.getLongitude())
                .build();
    }

    public static Store toEntityByDto(StoreDto storeDto){
        return Store.builder()
                .usrId(storeDto.getId())
                .name(storeDto.getName())
                .email(storeDto.getEmail())
                .phone(storeDto.getPhone())
                .storeName(storeDto.getStoreName())
                .content(storeDto.getContent())
                .storeContact(storeDto.getStoreContact())
                .storeContact2(storeDto.getStoreContact2())
                .detailAddress(storeDto.getDetailAddress())
                .minPickUpTime(0)
                .maxPickUpTime(0)
                .image(storeDto.getImage())
                .build();
    }
}
