package com.hihi.square.domain.partnership.service;


import com.hihi.square.domain.menu.entity.Menu;
import com.hihi.square.domain.menu.repository.MenuRepository;
import com.hihi.square.domain.partnership.dto.request.PartnershipDto;
import com.hihi.square.domain.partnership.entity.Partnership;
import com.hihi.square.domain.partnership.entity.PartnershipAcceptState;
import com.hihi.square.domain.partnership.repository.PartnershipRepository;
import com.hihi.square.domain.store.entity.Store;
import com.hihi.square.domain.store.repository.StoreRepository;
import com.hihi.square.global.error.ErrorCode;
import com.hihi.square.global.error.type.BusinessException;
import com.hihi.square.global.error.type.EntityNotFoundException;
import com.hihi.square.global.error.type.UserMismachException;
import com.hihi.square.global.error.type.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PartnershipServiceImpl implements PartnershipService{

    private final PartnershipRepository partnershipRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;

    @Override
    @Transactional
    public void addPartnership(Integer stoId, PartnershipDto req) {
        // 로그인 유저
        Store store = storeRepository.findById(stoId).orElseThrow(()->new UserNotFoundException("Store not found"));
        
        // 쿠폰 발급 가게
        Store issStore = storeRepository.findById(req.getIssStoreId()).orElseThrow(() -> new UserNotFoundException("Coupon Issue Store not found"));
        
        // 쿠폰 사용 가게
        Store useStore = storeRepository.findById(req.getUseStoreId()).orElseThrow(() -> new UserNotFoundException("Coupon Use Store not found"));
        
        // 쿠폰 적용 메뉴
        Menu menu = menuRepository.findById(req.getMenuId()).orElseThrow(()->new EntityNotFoundException("Menu not found"));

        // 요청 가게가 발급/사용 가게 중 하나여야 함
        if (!issStore.equals(store) && !useStore.equals(store)) throw new BusinessException(ErrorCode.ADD_NOT_ALLOWED);

        // 메뉴가 발급가게의 메뉴가 아니라면 에러발생
        if (issStore.getUsrId() != menu.getStore().getUsrId()) throw new UserMismachException("Menu and Coupon issue User Mismatch");
        // 다른 가게에게 연계할인 제안할 때에는 대기상태
        PartnershipAcceptState state = issStore.equals(useStore) ? PartnershipAcceptState.ACCEPTED : PartnershipAcceptState.WAIT;
        Partnership partnership = Partnership.toEntity(req,  issStore,  useStore, store, menu, state);

        partnershipRepository.save(partnership);
    }

    @Override
    @Transactional
    public void updatePartnership(Integer stoId, PartnershipDto req) {
        // 로그인 유저
        Store store = storeRepository.findById(stoId).orElseThrow(()->new UserNotFoundException("Store not found"));
        Partnership partnership = partnershipRepository.findById(req.getId()).orElseThrow(() -> new EntityNotFoundException("Partnership not found"));

        // 제휴의 경우 제안자만 수정 가능
        if (!partnership.getProStore().equals(store)) throw new UserMismachException("Partnership proposed user mismatch");

        // 현재 상태가 대기일 때만 수정 가능
        if (!partnership.getAcceptState().equals(PartnershipAcceptState.WAIT)) throw new BusinessException(ErrorCode.UPDATE_NOT_ALLOWED);

        // 쿠폰 발급 가게
        Store issStore = storeRepository.findById(req.getIssStoreId()).orElseThrow(() -> new UserNotFoundException("Coupon Issue Store not found"));

        // 쿠폰 사용 가게
        Store useStore = storeRepository.findById(req.getUseStoreId()).orElseThrow(() -> new UserNotFoundException("Coupon Use Store not found"));

        // 이전에 함께 진행했던 유저가 아니면 진행 불가
        if (!((partnership.getUseStore().equals(useStore) && partnership.getIssStore().equals(issStore)) ||
                (partnership.getUseStore().equals(issStore) && partnership.getIssStore().equals(useStore)))) throw new BusinessException(ErrorCode.ADD_NOT_ALLOWED);

        // 쿠폰 적용 메뉴
        Menu menu = menuRepository.findById(req.getMenuId()).orElseThrow(()->new EntityNotFoundException("Menu not found"));

        // 메뉴가 발급가게의 메뉴가 아니라면 에러발생
        if (issStore.getUsrId() != menu.getStore().getUsrId()) throw new UserMismachException("Menu and Coupon issue User Mismatch");

        // 이미 기간이 시작된 제휴 || dto의 시작 시간이 현재 이전이면 수정 불가
        if (partnership.getStartTime().isBefore(LocalDateTime.now()) || req.getStartTime().isBefore(LocalDateTime.now())) throw new BusinessException(ErrorCode.UPDATE_NOT_ALLOWED);

        partnership.update(req, issStore, useStore, menu);
        partnershipRepository.save(partnership);

    }
}
