package com.hihi.square.domain.partnership.service;


import com.hihi.square.domain.menu.dto.MenuInfoRes;
import com.hihi.square.domain.menu.entity.Menu;
import com.hihi.square.domain.menu.repository.MenuRepository;
import com.hihi.square.domain.partnership.dto.request.PartnershipReq;
import com.hihi.square.domain.partnership.dto.request.UpdatePartnershipAcceptStateReq;
import com.hihi.square.domain.partnership.dto.response.PartnershipRes;
import com.hihi.square.domain.partnership.entity.Partnership;
import com.hihi.square.domain.partnership.entity.PartnershipAcceptState;
import com.hihi.square.domain.partnership.entity.PartnershipStop;
import com.hihi.square.domain.partnership.repository.PartnershipRepository;
import com.hihi.square.domain.partnership.repository.PartnershipStopRepository;
import com.hihi.square.domain.store.dto.response.StoreInfoRes;
import com.hihi.square.domain.store.dto.response.StoreRes;
import com.hihi.square.domain.store.entity.Store;
import com.hihi.square.domain.store.repository.StoreRepository;
import com.hihi.square.domain.user.dto.UserRes;
import com.hihi.square.global.error.ErrorCode;
import com.hihi.square.global.error.type.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PartnershipServiceImpl implements PartnershipService{

    private final PartnershipRepository partnershipRepository;
    private final PartnershipStopRepository partnershipStopRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;

    @Override
    @Transactional
    public void addPartnership(Integer stoId, PartnershipReq req) {
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
        PartnershipAcceptState state = issStore.equals(useStore) ? PartnershipAcceptState.NORMAL : PartnershipAcceptState.WAIT;
        Partnership partnership = Partnership.toEntity(req,  issStore,  useStore, store, menu, state);

        partnershipRepository.save(partnership);
    }

    @Override
    @Transactional
    public void updatePartnership(Integer stoId, PartnershipReq req) {
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

    @Override
    @Transactional
    public void updatePartnershipAcceptState(Integer stoId, UpdatePartnershipAcceptStateReq req) {
        // 로그인한 유저
        Store store = storeRepository.findById(stoId).orElseThrow(() -> new UserNotFoundException("Store not found"));

        // 제휴
        Partnership partnership = partnershipRepository.findById(req.getId()).orElseThrow(() -> new EntityNotFoundException("Partnership Entity not found"));

        // 만약 해당 제휴가 로그인 유저와 관계 없는 제휴라면 변경 불가
        if (!partnership.getUseStore().equals(store) && !partnership.getIssStore().equals(store)) throw new UpdateNotAllowedException("Store has nothing to do with partnership");

        // 거절 -> 아무것도 못함
        // 취소 -> 아무것도 못함
        if (partnership.getAcceptState().equals(PartnershipAcceptState.REFUSAL) ||
            partnership.getAcceptState().equals(PartnershipAcceptState.CANCELED)
        ) throw new UpdateNotAllowedException("Can't update REFUSAL OR CANCELED State");

        else if (partnership.getAcceptState().equals(PartnershipAcceptState.WAIT)) {
            // 대기 -> 수락/거절 (제안안한 가게만 가능)
            if (req.getState().equals(PartnershipAcceptState.NORMAL) || req.getState().equals(PartnershipAcceptState.REFUSAL)) {
                if (partnership.getProStore().equals(store)) throw new UpdateNotAllowedException("Only offer received store can update state.");
            }
            // 대기 -> 취소 (아직 수락/거절 안했으면) -> 제안한 가게만 가능
            else if (req.getState().equals(PartnershipAcceptState.CANCELED)) {
                if (!partnership.getProStore().equals(store)) throw new UpdateNotAllowedException("Only offer proposed store can update state.");
            }
            // 대기 -> 발급정지 불가능
            else if (req.getState().equals(PartnershipAcceptState.STOP)) {
                throw new UpdateNotAllowedException("Can't update WAIT State to STOP");
            }
            partnership.updatePartnershipAcceptState(req.getState());
//            partnershipRepository.save(partnership);
        }

        // 수락 -> 정지는 둘다 가능
        else if (partnership.getAcceptState().equals(PartnershipAcceptState.NORMAL)) {
            // 수락 -> 거절/대기/취소(이미 수락한 건에 대하여 취소 불가) 못함
            if (req.getState().equals(PartnershipAcceptState.WAIT) || req.getState().equals(PartnershipAcceptState.CANCELED) || req.getState().equals(PartnershipAcceptState.REFUSAL)) throw new UpdateNotAllowedException("Can't update NORMAL state to "+req.getState());
            // 발급 정지 처리
            else if (req.getState().equals(PartnershipAcceptState.STOP)) {
                partnership.updatePartnershipAcceptState(req.getState());
                PartnershipStop partnershipStop = PartnershipStop.builder()
                        .partnership(partnership)
                        .store(store)
                        .isFinished(false)
                        .build();
                partnershipStopRepository.save(partnershipStop);
//                partnershipRepository.save(partnership);
            }
        }

        // 발급정지 -> 정상처리 => 발급 정지한 유저만 가능
        else {
            if (req.getState().equals(PartnershipAcceptState.NORMAL)) {
                // 1. 정지 -> 정상처리 하고 싶으면
                // 1-1. 먼저 본인이 정지한 기록을 가져와서
                PartnershipStop stop = partnershipStopRepository.findByPartnershipAndStoreAndIsFinished(partnership, store, false).orElseThrow(() -> new EntityNotFoundException("Didn't stop Partnership"));

                // 1-2. 정지기록을 처리한 후,
                stop.updateToFinish();
                partnershipStopRepository.save(stop);

                // 1-3. 다른 가게도 정지한 상태가 아니면 정상처리 한다.
                if (partnershipStopRepository.findAllByPartnershipAndIsFinished(partnership, false).isEmpty()) {
                    partnership.updatePartnershipAcceptState(req.getState());
//                    partnershipRepository.save(partnership);
                }
            } else if (req.getState().equals(PartnershipAcceptState.STOP)) {
                // 2. 정지 -> 정지하고 싶으면
                // 2-1. 현재 내가 정지하고 있지 않아야 한다.
                if (partnershipStopRepository.findByPartnershipAndStoreAndIsFinished(partnership, store, false).isPresent()) throw new UpdateNotAllowedException("Only partnership not stopped store can update the state.");

                // 2-2. 새로운 정지 기록 생성
                PartnershipStop partnershipStop = PartnershipStop.builder()
                        .partnership(partnership)
                        .store(store)
                        .isFinished(false)
                        .build();
                partnershipStopRepository.save(partnershipStop);
//                partnershipRepository.save(partnership);
            } else throw new UpdateNotAllowedException("Can't update STOP state to "+req.getState()); // 나머지 처리는 불가
        }
    }

    @Override
    public List<PartnershipRes> getPartnerships(Integer stoId) {
        Store store = storeRepository.findById(stoId).orElseThrow(()-> new UserNotFoundException("User not found"));
        List<Partnership> partnershipList = partnershipRepository.findAllByStore(store);
        return partnershipListToResList(partnershipList);
    }

    @Override
    public List<PartnershipRes> getAvailablePartnerships(Integer stoId) {
        return null;
    }

    @Override
    public List<PartnershipRes> getPartnerships(Integer stoId1, Integer stoId2) {
        Store store1 = storeRepository.findById(stoId1).orElseThrow(()-> new UserNotFoundException("User not found"));
        Store store2 = storeRepository.findById(stoId2).orElseThrow(()-> new UserNotFoundException("User not found"));
        List<Partnership> partnershipList = partnershipRepository.findAllByStores(store1, store2);

        return partnershipListToResList(partnershipList);
    }

    @Override
    public List<PartnershipRes> getProgressPartnerships(Integer stoId) {
        Store store = storeRepository.findById(stoId).orElseThrow(()-> new UserNotFoundException("User not found"));
        List<Partnership> partnershipList = partnershipRepository.findAllByStoreAndProgress(store, LocalDateTime.now());
        return partnershipListToResList(partnershipList);
    }

    public List<PartnershipRes> partnershipListToResList(List<Partnership> partnershipList) {
        List<PartnershipRes> resList = new ArrayList<>();
        for(Partnership partnership : partnershipList) {
            StoreInfoRes issStore = StoreInfoRes.toRes(partnership.getIssStore());
            StoreInfoRes useStore = StoreInfoRes.toRes(partnership.getUseStore());
            MenuInfoRes menu = MenuInfoRes.toRes(partnership.getMenu());
            resList.add(PartnershipRes.toRes(partnership, issStore, useStore, menu));
        }
        return resList;
    }
}
