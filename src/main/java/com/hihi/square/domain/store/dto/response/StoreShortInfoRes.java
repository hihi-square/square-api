package com.hihi.square.domain.store.dto.response;

import com.hihi.square.domain.store.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class StoreShortInfoRes {
    private Integer id;
    private String name;
    private String storeName;
    private String storeContact;
    private String profileImage;

    public static StoreShortInfoRes toRes(Store store) {
        return StoreShortInfoRes.builder()
                .id(store.getUsrId())
                .name(store.getName())
                .storeName(store.getStoreName())
                .storeContact(store.getStoreContact())
                .profileImage(store.getProfileImage())
                .build();
    }
}
