package com.hihi.square.domain.dibs.service;

import com.hihi.square.domain.buyer.entity.Buyer;
import com.hihi.square.domain.buyer.repository.BuyerRepository;
import com.hihi.square.domain.dibs.dto.DibsAllRes;
import com.hihi.square.domain.dibs.dto.DibsInfoRes;
import com.hihi.square.domain.dibs.entity.Dibs;
import com.hihi.square.domain.dibs.repository.DibsRepository;
import com.hihi.square.domain.partnership.entity.Partnership;
import com.hihi.square.domain.partnership.repository.PartnershipRepository;
import com.hihi.square.domain.store.dto.response.StoreInfoRes;
import com.hihi.square.domain.store.entity.Store;
import com.hihi.square.domain.store.repository.StoreRepository;
import com.hihi.square.domain.user.entity.UserStatus;
import com.hihi.square.global.error.type.AddNotAllowedException;
import com.hihi.square.global.error.type.EntityNotFoundException;
import com.hihi.square.global.error.type.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DibsServiceImpl implements DibsService{

    private final DibsRepository dibsRepository;
    private final BuyerRepository buyerRepository;
    private final StoreRepository storeRepository;
    private final PartnershipRepository partnershipRepository;

    @Override
    public DibsAllRes getAllDibsList(Integer buyerId) {
        Buyer buyer = buyerRepository.findById(buyerId).orElseThrow(() -> new UserNotFoundException("구매자를 찾을 수 없습니다."));
        List<Dibs> list = dibsRepository.findByBuyer(buyer);
        List<DibsInfoRes> res = new ArrayList<>();
        for(Dibs d : list) {
            boolean isPn = partnershipRepository.existsByStoreAndProgress(d.getStore());
            res.add(DibsInfoRes.builder()
                    .id(d.getId())
                    .store(StoreInfoRes.toRes(d.getStore(), isPn, isPn))
                    .build());
        }
        return DibsAllRes.builder().list(res).build();
    }

    @Override
    @Transactional
    public void addDibs(Integer buyerId, Integer storeId) {
        Buyer buyer = buyerRepository.findById(buyerId).orElseThrow(() -> new UserNotFoundException("구매자를 찾을 수 없습니다."));
        Store store = storeRepository.findById(storeId, UserStatus.ACTIVE).orElseThrow(() -> new EntityNotFoundException("판매자를 찾을 수 없습니다."));
        if (dibsRepository.existsByBuyerAndStore(buyer, store)) throw new AddNotAllowedException("이미 존재하는 즐겨찾기입니다.");
        dibsRepository.save(Dibs.builder()
                        .buyer(buyer)
                        .store(store)
                .build());
    }

    @Override
    @Transactional
    public void removeDibs(Integer buyerId, Integer id) {
        Buyer buyer = buyerRepository.findById(buyerId).orElseThrow(() -> new UserNotFoundException("구매자를 찾을 수 없습니다."));
        dibsRepository.removeByBuyerAndId(buyer, id);
    }
}
