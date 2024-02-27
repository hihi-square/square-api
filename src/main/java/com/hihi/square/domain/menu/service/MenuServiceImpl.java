package com.hihi.square.domain.menu.service;

import com.hihi.square.common.CommonStatus;
import com.hihi.square.domain.menu.dto.*;
import com.hihi.square.domain.menu.entity.Menu;
import com.hihi.square.domain.menu.entity.MenuOption;
import com.hihi.square.domain.menu.entity.SaleType;
import com.hihi.square.domain.menu.repository.MenuOptionRepository;
import com.hihi.square.domain.menu.repository.MenuRepository;
import com.hihi.square.domain.menucategory.dto.MenuCategoryDto;
import com.hihi.square.domain.menucategory.entity.MenuCategory;
import com.hihi.square.domain.menucategory.repository.MenuCategoryRepository;
import com.hihi.square.domain.partnership.dto.response.PartnershipCouponDto;
import com.hihi.square.domain.partnership.dto.response.PartnershipRes;
import com.hihi.square.domain.partnership.entity.Partnership;
import com.hihi.square.domain.partnership.repository.PartnershipRepository;
import com.hihi.square.domain.store.entity.Store;
import com.hihi.square.domain.store.repository.StoreRepository;
import com.hihi.square.domain.timesale.dto.TimeSaleDto;
import com.hihi.square.domain.timesale.dto.TimeSaleMenuDto;
import com.hihi.square.domain.timesale.entity.TimeSale;
import com.hihi.square.domain.timesale.entity.TimeSaleMenu;
import com.hihi.square.domain.timesale.entity.TimeSaleStatus;
import com.hihi.square.domain.timesale.repository.TimeSaleMenuRepository;
import com.hihi.square.domain.timesale.repository.TimeSaleRepository;
import com.hihi.square.domain.user.entity.User;
import com.hihi.square.domain.user.entity.UserStatus;
import com.hihi.square.domain.user.repository.UserRepository;
import com.hihi.square.global.error.type.EntityNotFoundException;
import com.hihi.square.global.error.type.UserMismachException;
import com.hihi.square.global.error.type.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final MenuCategoryRepository menuCategoryRepository;
    private final MenuOptionRepository menuOptionRepository;
    private final TimeSaleRepository timeSaleRepository;
    private final TimeSaleMenuRepository timeSaleMenuRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final PartnershipRepository partnershipRepository;

    public Store checkUserMatching(Integer stoId, Integer reqStoId){
        Store store = storeRepository.findById(stoId).orElseThrow(() -> new UserNotFoundException("User Not Found"));
        if(!stoId.equals(reqStoId)) throw new UserMismachException("User Mismatch");
        return store;
    }

    @Override
    @Transactional
    public void addMenu(Integer stoId, StoreMenuDto storeMenuDto) {
        //1. 사전 체크
        //1-1. 로그인한 store와 등록 store 일치 여부
        Store store = checkUserMatching(stoId, storeMenuDto.getStoId());
        
        //2. 메뉴 등록
        //2-1. 메뉴 카테고리 찾기(있으면)
        Integer mcId = storeMenuDto.getMcId();
        MenuCategory menuCategory = null;
        if(mcId != null && mcId != 0){
            menuCategory = menuCategoryRepository.findById(mcId, CommonStatus.activeAndPrivate).orElseThrow(()
                    -> new EntityNotFoundException("MenuCategory Not Found"));
        }

        //2-2. 마지막 순서 지정
        Integer sequence = menuRepository.findSequence(CommonStatus.activeAndPrivate);
        if(sequence == null) sequence = 1;
        storeMenuDto.setSequence(sequence); //마지막 순서로 등록

        Menu menu = Menu.toEntity(storeMenuDto, menuCategory, store);
        menu = menuRepository.save(menu);

        //3. 옵션 등록
        if(storeMenuDto.getOptions() != null){
            List<MenuOptionDto> optionReqList = storeMenuDto.getOptions();
            for(MenuOptionDto moDto : optionReqList){
                MenuOption menuOption = MenuOption.toEntity(moDto, menu);
                menuOptionRepository.save(menuOption);
            }
        }
    }

    @Override
    @Transactional
    public void deleteMenu(Integer stoId, Integer menuId) {
        //1. 사전 체크
        //1-1. menu 존재 여부
        Menu menu = menuRepository.findById(menuId, CommonStatus.activeAndPrivate).orElseThrow(()
                -> new EntityNotFoundException("Menu Not Found"));
        //1-2. 로그인한 store와 등록 store 일치 여부
        Store store = checkUserMatching(stoId, menu.getStore().getUsrId());
        
        //2. 메뉴 삭제
        menuRepository.deleteMenu(menuId, CommonStatus.DELETE);
    }

    @Override
    @Transactional
    public void updateMenu(Integer stoId, Integer menuId, StoreMenuDto storeMenuDto) {
        //1. 사전 체크
        //1-1. menu 존재 여부
        Menu menu = menuRepository.findById(menuId, CommonStatus.activeAndPrivate).orElseThrow(()
                -> new EntityNotFoundException("Menu Not Found"));
        //1-2. 로그인한 store와 등록 store 일치 여부
        Store store = checkUserMatching(stoId, menu.getStore().getUsrId());

        //2. 메뉴 수정
        //2-1. 카테고리 존재 여부
        Menu menuToSave = menu;
        if(storeMenuDto.getId() != null){
            Integer mcId = storeMenuDto.getMcId();
            MenuCategory menuCategory = null;
            if(mcId != null && mcId != 0){
                menuCategory = menuCategoryRepository.findById(mcId, CommonStatus.activeAndPrivate).orElseThrow(()
                        -> new EntityNotFoundException("MenuCategory Not Found"));
            }
            //2-2. id 설정
            storeMenuDto.setId(menu.getId());    //id 설정

            menuToSave = menuRepository.save(Menu.toEntity(storeMenuDto, menuCategory, store));
        }

        //3. 옵션 등록
        if(storeMenuDto.getOptions() != null){
            List<MenuOptionDto> optionReqList = storeMenuDto.getOptions();

            for(MenuOptionDto moDto : optionReqList){
                //option id가 있는 경우 존재 여부
                Integer moId = moDto.getId();
                if(moId != null){
                    menuOptionRepository.findById(moId, CommonStatus.activeAndPrivate).orElseThrow(
                            () -> new EntityNotFoundException("MenuOption Not Found")
                    );
                }
                MenuOption menuOption = MenuOption.toEntity(moDto, menuToSave);
                menuOptionRepository.save(menuOption);
            }
        }
    }

    @Override
    public StoreMenuDto selectMenu(Integer userId, Integer menuId) {
        //1. 사전 체크
        //1-2. 유저가 buyer인지, store인지 확인
        User user = userRepository.findByUserId(userId, UserStatus.ACTIVE).orElseThrow(
                () -> new UserNotFoundException("User Not Found")
        );

        Menu menu = null;
        if(user instanceof Store){
            //1-2. 로그인한 store와 등록 store 일치 여부
            menu = menuRepository.findById(menuId, CommonStatus.activeAndPrivate).orElseThrow(()
                    -> new EntityNotFoundException("Menu Not Found"));
            Store store = checkUserMatching(userId, menu.getStore().getUsrId());
        }
        else{   //구매자인 경우
            menu = menuRepository.findById(menuId, CommonStatus.ACTIVE).orElseThrow(()
                    -> new EntityNotFoundException("Menu Not Found"));
        }

        //2. 메뉴 카테고리 조회
        MenuCategory menuCategory = menu.getMenuCategory();
        Integer mcId = menuCategory == null ? 0 : menuCategory.getId(); //null인 경우 0 반환

        //3. 메뉴 옵션 조회
        List<MenuOption> optionList = menuOptionRepository.findAllByMenu(menuId);
        List<MenuOptionDto> optionDtoList = new ArrayList<>();
        if(optionList != null){
            for(MenuOption mo : optionList){
                MenuOptionDto menuOptionDto = MenuOptionDto.toRes(mo);
                optionDtoList.add(menuOptionDto);
            }
        }

        StoreMenuDto storeMenuDto = StoreMenuDto.toRes(menu, menuCategory, optionDtoList);
        return storeMenuDto;
    }

    @Override
    public MenuAllDto selectAllMenu(Integer userId) {
        //1. 사전 체크
        //1-2. 유저가 buyer인지, store인지 확인
        User user = userRepository.findByUserId(userId, UserStatus.ACTIVE).orElseThrow(
                () -> new UserNotFoundException("User Not Found")
        );

        List<Menu> menuList = null;
        if(user instanceof Store){
            menuList = menuRepository.findAllByStoreOrderBySequence(userId, CommonStatus.activeAndPrivate);
        }
        else{
            menuList = menuRepository.findAllByStoreOrderBySequence(userId, CommonStatus.ACTIVE);
        }

        List<StoreMenuDto> storeMenuDtoList = new ArrayList<>();

        //메뉴 카테고리 리스트 조회
        List<MenuCategory> menuCategoryList = menuCategoryRepository.findAllByStoreOrderBySequence(userId, CommonStatus.activeAndPrivate);
        List<MenuCategoryDto> mcdList = new ArrayList<>();

        for(MenuCategory mc : menuCategoryList){
            MenuCategoryDto mcDto = MenuCategoryDto.toRes(mc, null);
            mcdList.add(mcDto);
        }
        
        //메뉴 조회
        for(Menu menu : menuList){
            //2. 메뉴 카테고리 조회
            MenuCategory menuCategory = menu.getMenuCategory();
            Integer mcId = menuCategory == null ? 0 : menuCategory.getId(); //null인 경우 0 반환

            //3. 메뉴 옵션 조회
            List<MenuOption> optionList = menuOptionRepository.findAllByMenu(menu.getId());
            List<MenuOptionDto> optionDtoList = new ArrayList<>();
            if(optionList != null){
                for(MenuOption mo : optionList){
                    MenuOptionDto menuOptionDto = MenuOptionDto.toRes(mo);
                    optionDtoList.add(menuOptionDto);
                }
            }
            
            StoreMenuDto storeMenuDto = StoreMenuDto.toRes(menu, menuCategory, optionDtoList);
            storeMenuDtoList.add(storeMenuDto);
        }

        MenuAllDto menuAllDto = MenuAllDto.toRes(mcdList, storeMenuDtoList);

        return menuAllDto;
    }

    @Override
    @Transactional
    public void updateSequence(MenuSequenceReq menuSequenceReq) {
        //1. 카테고리 순서 변경
        List<MenuCategoryDto> menuCategoryDtoList = menuSequenceReq.getCategory();
        List<StoreMenuDto> storeMenuDtoList = menuSequenceReq.getMenu();

        if(menuCategoryDtoList != null){
            for(MenuCategoryDto mcDto : menuCategoryDtoList){
                Integer mcId = mcDto.getId();
                MenuCategory mc = menuCategoryRepository.findById(mcId, CommonStatus.activeAndPrivate).orElseThrow(
                        () -> new EntityNotFoundException("MenuCategory Not Found")
                );
                menuCategoryRepository.updateSequence(mcId, mcDto.getSequence());
            }
        }

        //2. 메뉴 순서 + 카테고리 + 상태 변경 --> 효율화 고민
        if(storeMenuDtoList != null){
            for(StoreMenuDto storeMenuDto : storeMenuDtoList){
                Integer mcId = storeMenuDto.getMcId();
                Integer menuId = storeMenuDto.getId();
                MenuCategory mc = null;
                if(mcId != null && mcId != 0){
                    mc = menuCategoryRepository.findById(mcId, CommonStatus.activeAndPrivate).orElseThrow(
                            () -> new EntityNotFoundException("MenuCategory Not Found")
                    );
                }

                Menu menu = menuRepository.findById(menuId, CommonStatus.activeAndPrivate).orElseThrow(
                        () -> new EntityNotFoundException("Menu Not Found")
                );
                menuRepository.updateSequence(menuId, storeMenuDto.getSequence(), mc, storeMenuDto.getStatus());
            }
        }
    }

    @Override
    public TimeSaleDto selectSaleMenuByBuyer(Integer stoId, Integer typeId) {
        //1. 상점 존재 확인
        Store store = storeRepository.findById(stoId, UserStatus.ACTIVE).orElseThrow(
                () -> new UserNotFoundException("Store Not Found"));
        //2. 타임 세일 존재 확인
        TimeSale timeSale = timeSaleRepository.findById(typeId).orElseThrow(
                () -> new EntityNotFoundException("Sale Not Found")
        );

        //3. 타임 세일 상품 조회
        TimeSaleMenu timeSaleMenu = timeSaleMenuRepository.findBySale(typeId).orElseThrow(
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

    @Transactional
    @Override
    public PartnershipRes selectPartnershipMenuByBuyer(Integer stoId, Integer typeId) {
        //1. 상점 존재 확인
        Store store = storeRepository.findById(stoId, UserStatus.ACTIVE).orElseThrow(
                () -> new UserNotFoundException("Store Not Found"));
        //2. 제휴 존재 확인
        Partnership partnership = partnershipRepository.findById(typeId).orElseThrow(
                () -> new EntityNotFoundException("Partnership Not Found")
        );

        log.info("partner: {}", partnership);

        //3. 제휴 상품 조회
        Menu menu = partnership.getMenu();
        List<MenuOption> menuOptions = menuOptionRepository.findAllByMenu(menu.getId());
        List<MenuOptionDto> moDtos = new ArrayList<>();

        if(menuOptions != null){
            for(MenuOption mo : menuOptions){
                moDtos.add(MenuOptionDto.toRes(mo));
            }
        }

        StoreMenuDto menuDto = StoreMenuDto.toRes(menu, menu.getMenuCategory(), moDtos);
        PartnershipRes partnershipRes = PartnershipRes.toRes(partnership, partnership.getIssStore(), partnership.getUseStore(),
                MenuInfoRes.toRes(menu));

        return partnershipRes;
    }

    @Override
    public BuyerMenuAllDto selectAllMenuByBuyer(Integer stoId) {
        //1. 상점 존재 확인
        Store store = storeRepository.findById(stoId, UserStatus.ACTIVE).orElseThrow(
                () -> new UserNotFoundException("Store Not Found"));

        //2. 메뉴 카테고리 리스트 조회
        List<MenuCategory> menuCategoryList = menuCategoryRepository.findAllByStoreOrderBySequence(stoId, CommonStatus.activeAndPrivate);
        List<MenuCategoryDto> mcdList = new ArrayList<>();

        //2-1. 타임 세일 존재 확인
        List<TimeSale> timeSaleList = timeSaleRepository.findAllByStoreAndProgress(stoId, LocalDateTime.now(), TimeSaleStatus.prepareAndOngoing);
        List<Partnership> partnershipList = partnershipRepository.findAllByIssStoreAndProgress(store, LocalDateTime.now());

        //카테고리별 타임 세일 + 제휴 세일 상품 조회
        for(MenuCategory mc : menuCategoryList){

            //2-2. 타임 세일 상품 조회
            List<BuyerMenuDto> buyerMenuDtos = new ArrayList<>();

            if(timeSaleList != null){
                for(TimeSale s : timeSaleList){
                    TimeSaleMenu timeSaleMenu = timeSaleMenuRepository.findBySaleAndCategory(s.getId(), mc.getId()).orElseThrow(
                            () -> new EntityNotFoundException("Sale Menu Not Found")
                    );

                    Menu menu = timeSaleMenu.getMenu();
                    StoreMenuDto storeMenuDto = StoreMenuDto.toRes(menu, menu.getMenuCategory(), null);
                    TimeSaleMenuDto timeSaleMenuDto = TimeSaleMenuDto.toRes(timeSaleMenu, storeMenuDto);
                    buyerMenuDtos.add(BuyerMenuDto.toRes(menu, SaleType.TIME, timeSaleMenuDto,false, null));
                }
            }

            //2-3. 제휴 상품 조회
            if(partnershipList != null){
                for(Partnership p : partnershipList){
                    Menu menu = p.getMenu();
                    PartnershipCouponDto partnershipCouponDto = PartnershipCouponDto.toRes(p);
                    buyerMenuDtos.add(BuyerMenuDto.toRes(menu, SaleType.PARTNERSHIP, null,true, partnershipCouponDto));
                }
            }

            //2-4. dto 추가
            mcdList.add(MenuCategoryDto.toRes(mc, buyerMenuDtos));
        }

        BuyerMenuAllDto buyerMenuAllDto = BuyerMenuAllDto.toRes(stoId, mcdList);
        return buyerMenuAllDto;
    }
}
