package com.hihi.square.domain.store.repository;

import com.hihi.square.domain.activity.entity.EmdAddress;
import com.hihi.square.domain.buyer.entity.Buyer;
import com.hihi.square.domain.store.dto.response.StoreSearchInfoDto;
import com.hihi.square.domain.store.entity.Store;

import java.util.List;

public interface StoreCustomRepository {
    List<Store> searchStoreList(List<EmdAddress> emdAddressList, Buyer buyer, Integer category, String orderBy, Boolean timesale, Boolean partnership, Boolean dibs, Double longitude, Double latitude);
    List<StoreSearchInfoDto> searchStoreDtoList(List<EmdAddress> emdAddressList, Buyer buyer, Integer category, String orderBy, Boolean timesale, Boolean partnership, Boolean dibs, Double longitude, Double latitude);
}
