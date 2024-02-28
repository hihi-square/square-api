package com.hihi.square.domain.timesale.service;

import com.hihi.square.domain.menu.dto.StoreMenuDto;
import com.hihi.square.domain.menu.dto.MenuOptionDto;
import com.hihi.square.domain.menu.entity.Menu;
import com.hihi.square.domain.menu.entity.MenuOption;
import com.hihi.square.domain.menu.repository.MenuOptionRepository;
import com.hihi.square.domain.menu.repository.MenuRepository;
import com.hihi.square.domain.store.entity.Store;
import com.hihi.square.domain.store.repository.StoreRepository;
import com.hihi.square.domain.timesale.dto.TimeSaleDto;
import com.hihi.square.domain.timesale.dto.TimeSaleMenuDto;
import com.hihi.square.domain.timesale.entity.TimeSale;
import com.hihi.square.domain.timesale.entity.TimeSaleMenu;
import com.hihi.square.domain.timesale.entity.TimeSaleStatus;
import com.hihi.square.domain.timesale.repository.TimeSaleMenuRepository;
import com.hihi.square.domain.timesale.repository.TimeSaleRepository;
import com.hihi.square.domain.user.entity.UserStatus;
import com.hihi.square.global.error.type.EntityNotFoundException;
import com.hihi.square.global.error.type.InvalidValueException;
import com.hihi.square.global.error.type.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TimeSaleServiceImpl implements TimeSaleService {
    private final TimeSaleRepository timeSaleRepository;
    private final TimeSaleMenuRepository timeSaleMenuRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final MenuOptionRepository menuOptionRepository;

    @Transactional
    @Override
    public void addSale(Integer stoId, TimeSaleDto timeSaleDto) {
        //1. 상점 존재 확인
        Store store = storeRepository.findById(stoId, UserStatus.ACTIVE).orElseThrow(
                () -> new UserNotFoundException("Store Not Found"));
        //2. 메뉴 존재 확인
        if(timeSaleDto.getSaleMenu() == null) throw new InvalidValueException("Sale Menu Not Input");
        TimeSaleMenuDto timeSaleMenuDto = timeSaleDto.getSaleMenu();
        if(timeSaleMenuDto.getMenu() == null) throw new InvalidValueException("Menu Not Input");
        StoreMenuDto storeMenuDto = timeSaleMenuDto.getMenu();
        if(storeMenuDto.getId() == null) throw new InvalidValueException("Menu Not Found");

        Menu menu = menuRepository.findById(storeMenuDto.getId()).orElseThrow(
                () -> new EntityNotFoundException("Menu Not Found")
        );
        //3. 타임 세일 등록(타임 세일 등록 + 타임 세일 메뉴 등록)
        TimeSale timeSale = TimeSale.toEntity(timeSaleDto, store);
        timeSaleRepository.save(timeSale);

        TimeSaleMenu timeSaleMenu = TimeSaleMenu.toEntity(timeSaleMenuDto, timeSale, menu);
        timeSaleMenuRepository.save(timeSaleMenu);
    }

    @Transactional
    @Override
    public void deleteSale(Integer stoId, Integer saleId) {
        //1. 상점 존재 확인
        Store store = storeRepository.findById(stoId, UserStatus.ACTIVE).orElseThrow(
                () -> new UserNotFoundException("Store Not Found"));
        //2. 타임세일 존재 확인
        TimeSale timeSale = timeSaleRepository.findById(saleId).orElseThrow(
                () -> new EntityNotFoundException("Sale Not Found")
        );
        //3. 삭제
        timeSaleRepository.updateSaleStatus(saleId, TimeSaleStatus.CANCEL);
    }

    @Transactional
    @Override
    public void updateSale(Integer stoId, Integer saleId, TimeSaleDto timeSaleDto) {
        //1. 상점 존재 확인
        Store store = storeRepository.findById(stoId, UserStatus.ACTIVE).orElseThrow(
                () -> new UserNotFoundException("Store Not Found"));
        //2. 타임세일 존재 확인
        TimeSale timeSale = timeSaleRepository.findById(saleId).orElseThrow(
                () -> new EntityNotFoundException("Sale Not Found")
        );

        timeSaleDto.setId(timeSale.getId());
        TimeSale newTimeSale = TimeSale.toEntity(timeSaleDto, store);
        timeSaleRepository.save(newTimeSale);

        //3. 타임세일 상품 존재 확인
        TimeSaleMenuDto timeSaleMenuDto = timeSaleDto.getSaleMenu();
        TimeSaleMenu timeSaleMenu = timeSaleMenuRepository.findBySale(saleId).orElseThrow(
                () -> new EntityNotFoundException("Sale Menu Not Found")
        );
        timeSaleMenuDto.setId(timeSaleMenu.getId());
        Menu menu = menuRepository.findById(timeSaleMenuDto.getMenu().getId()).orElseThrow(
                () -> new EntityNotFoundException("Menu Not Found")
        );
        timeSaleMenuRepository.save(TimeSaleMenu.toEntity(timeSaleMenuDto, newTimeSale, menu));
    }

    @Override
    public TimeSaleDto selectSale(Integer stoId, Integer saleId) {
        //1. 상점 존재 확인
        Store store = storeRepository.findById(stoId, UserStatus.ACTIVE).orElseThrow(
                () -> new UserNotFoundException("Store Not Found"));
        //2. 타임 세일 존재 확인
        TimeSale timeSale = timeSaleRepository.findById(saleId).orElseThrow(
                () -> new EntityNotFoundException("Sale Not Found")
        );

        //3. 타임 세일 상품 조회
        TimeSaleMenu timeSaleMenu = timeSaleMenuRepository.findBySale(saleId).orElseThrow(
                () -> new EntityNotFoundException("Sale Menu Not Found")
        );
        List<MenuOption> menuOptions = menuOptionRepository.findAllByMenu(timeSaleMenu.getMenu().getId());
        List<MenuOptionDto> moDtos = new ArrayList<>();

        if(menuOptions != null){
            for(MenuOption mo : menuOptions){
                moDtos.add(MenuOptionDto.toRes(mo));
            }
        }

        StoreMenuDto menu = StoreMenuDto.toRes(timeSaleMenu.getMenu(), timeSaleMenu.getMenu().getMenuCategory(), moDtos);

        TimeSaleDto timeSaleDto = TimeSaleDto.toRes(timeSale, TimeSaleMenuDto.toRes(timeSaleMenu, menu));
        return timeSaleDto;
    }

    @Override
    public List<TimeSaleDto> selectAllSale(Integer stoId) {
        //1. 상점 존재 확인
        Store store = storeRepository.findById(stoId, UserStatus.ACTIVE).orElseThrow(
                () -> new UserNotFoundException("Store Not Found"));
        //2. 타임 세일 존재 확인
        List<TimeSale> timeSaleList = timeSaleRepository.findByStore(stoId);

        //3. 타임 세일 상품 조회
        List<TimeSaleDto> timeSaleDtos = new ArrayList<>();

        if(timeSaleList != null){
            for(TimeSale s : timeSaleList){
                TimeSaleMenu timeSaleMenu = timeSaleMenuRepository.findBySale(s.getId()).orElseThrow(
                        () -> new EntityNotFoundException("Sale Menu Not Found")
                );

                StoreMenuDto storeMenuDto = StoreMenuDto.toRes(timeSaleMenu.getMenu(), null, null);
                TimeSaleMenuDto timeSaleMenuDto = TimeSaleMenuDto.toRes(timeSaleMenu, storeMenuDto);
                timeSaleDtos.add(TimeSaleDto.toRes(s, timeSaleMenuDto));
            }
        }

        return timeSaleDtos;
    }

    @Override
    public List<TimeSaleDto> selectAllSaleByStatus(Integer stoId, TimeSaleStatus status) {
        //1. 상점 존재 확인
        Store store = storeRepository.findById(stoId, UserStatus.ACTIVE).orElseThrow(
                () -> new UserNotFoundException("Store Not Found"));
        //2. 타임 세일 존재 확인
        List<TimeSale> timeSaleList = timeSaleRepository.findByStoreAndStatus(stoId, status);

        //3. 타임 세일 상품 조회
        List<TimeSaleDto> timeSaleDtos = new ArrayList<>();

        if(timeSaleList != null){
            for(TimeSale s : timeSaleList){
                TimeSaleMenu timeSaleMenu = timeSaleMenuRepository.findBySale(s.getId()).orElseThrow(
                        () -> new EntityNotFoundException("Sale Menu Not Found")
                );

                Menu menu = timeSaleMenu.getMenu();
                StoreMenuDto storeMenuDto = StoreMenuDto.toRes(menu, menu.getMenuCategory(), null);
                TimeSaleMenuDto timeSaleMenuDto = TimeSaleMenuDto.toRes(timeSaleMenu, storeMenuDto);
                timeSaleDtos.add(TimeSaleDto.toRes(s, timeSaleMenuDto));
            }
        }

        return timeSaleDtos;
    }
}
