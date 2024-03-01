package com.hihi.square.domain.store.service;

import com.hihi.square.domain.store.dto.StoreDto;
import com.hihi.square.domain.store.dto.request.LoginReq;
import com.hihi.square.domain.store.dto.request.SignUpStoreReq;
import com.hihi.square.domain.store.dto.request.StoreFindReq;
import com.hihi.square.domain.store.dto.response.LoginRes;
import com.hihi.square.domain.store.dto.response.StoreInfoRes;
import com.hihi.square.domain.store.dto.response.StoreSearchInfoDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

public interface StoreService {
    void checkDuplicateUID(String uid);

    void join(SignUpStoreReq signUpStoreReq);

    Map<String, String> findId(StoreFindReq storeFindReq);

    void findPassword(StoreFindReq storeFindReq);

    LoginRes login(LoginReq loginReq, HttpServletResponse response);

    void recreateToken(HttpServletRequest request, HttpServletResponse response);

    void logout(HttpServletRequest request, HttpServletResponse response);

    void updatePassword(Integer userId, String password);

    void deleteStore(Integer stoId, Integer PathStoreId, HttpServletRequest request, HttpServletResponse response);

    void updateStore(Integer stoId, StoreDto storeDto);

    StoreDto selectStore(Integer stoId, Integer pathStoreId);

    StoreInfoRes findInfoForBuyer(Integer buyerId, Integer storeId);

    List<StoreInfoRes> findAllStores(Integer stoId, Integer depth);

    List<StoreSearchInfoDto> searchStores(Integer buyerId, Integer category, String orderBy, boolean timesale, boolean partnership, boolean dibs, double longitude, double latitude);
}
