package com.hihi.square.domain.store.dto.response;

import com.hihi.square.domain.store.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StoreInfoRes {
    private Integer usrId;
    private String uid;
    private String nickname;
    private String profileImage;
    private String storeName;
    private String storeContact;

    public static StoreInfoRes toRes(Store store) {
        return StoreInfoRes.builder()
                .usrId(store.getUsrId())
                .uid(store.getUid())
                .nickname(store.getNickname())
                .profileImage(store.getProfileImage())
                .storeName(store.getStoreName())
                .storeContact(store.getStoreContact())
                .build();
    }
}
