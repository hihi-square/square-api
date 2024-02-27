package com.hihi.square.domain.menu.service;

import com.hihi.square.common.CommonStatus;
import com.hihi.square.domain.menu.dto.*;
import com.hihi.square.domain.menu.entity.Menu;
import com.hihi.square.domain.menu.entity.MenuOption;
import com.hihi.square.domain.menu.repository.MenuOptionRepository;
import com.hihi.square.domain.menu.repository.MenuRepository;
import com.hihi.square.domain.menucategory.dto.MenuCategoryDto;
import com.hihi.square.domain.menucategory.entity.MenuCategory;
import com.hihi.square.domain.menucategory.repository.MenuCategoryRepository;
import com.hihi.square.domain.partnership.dto.response.PartnershipRes;
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

    public Store checkUserMatching(Integer stoId, Integer reqStoId){
        Store store = storeRepository.findById(stoId).orElseThrow(() -> new UserNotFoundException("User Not Found"));
        if(!stoId.equals(reqStoId)) throw new UserMismachException("User Mismatch");
        return store;
    }

    @Override
    @Transactional
    public void addMenu(Integer stoId, MenuDto menuDto) {
        //1. 사전 체크
        //1-1. 로그인한 store와 등록 store 일치 여부
        Store store = checkUserMatching(stoId, menuDto.getStoId());
        
        //2. 메뉴 등록
        //2-1. 메뉴 카테고리 찾기(있으면)
        Integer mcId = menuDto.getMcId();
        MenuCategory menuCategory = null;
        if(mcId != null && mcId != 0){
            menuCategory = menuCategoryRepository.findById(mcId, CommonStatus.activeAndPrivate).orElseThrow(()
                    -> new EntityNotFoundException("MenuCategory Not Found"));
        }

        //2-2. 마지막 순서 지정
        Integer sequence = menuRepository.findSequence(CommonStatus.activeAndPrivate);
        if(sequence == null) sequence = 1;
        menuDto.setSequence(sequence); //마지막 순서로 등록

        Menu menu = Menu.toEntity(menuDto, menuCategory, store);
        menu = menuRepository.save(menu);

        //3. 옵션 등록
        if(menuDto.getOptions() != null){
            List<MenuOptionDto> optionReqList = menuDto.getOptions();
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
    public void updateMenu(Integer stoId, Integer menuId, MenuDto menuDto) {
        //1. 사전 체크
        //1-1. menu 존재 여부
        Menu menu = menuRepository.findById(menuId, CommonStatus.activeAndPrivate).orElseThrow(()
                -> new EntityNotFoundException("Menu Not Found"));
        //1-2. 로그인한 store와 등록 store 일치 여부
        Store store = checkUserMatching(stoId, menu.getStore().getUsrId());

        //2. 메뉴 수정
        //2-1. 카테고리 존재 여부
        Menu menuToSave = menu;
        if(menuDto.getId() != null){
            Integer mcId = menuDto.getMcId();
            MenuCategory menuCategory = null;
            if(mcId != null && mcId != 0){
                menuCategory = menuCategoryRepository.findById(mcId, CommonStatus.activeAndPrivate).orElseThrow(()
                        -> new EntityNotFoundException("MenuCategory Not Found"));
            }
            //2-2. id 설정
            menuDto.setId(menu.getId());    //id 설정

            menuToSave = menuRepository.save(Menu.toEntity(menuDto, menuCategory, store));
        }

        //3. 옵션 등록
        if(menuDto.getOptions() != null){
            List<MenuOptionDto> optionReqList = menuDto.getOptions();

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
    public MenuDto selectMenu(Integer userId, Integer menuId) {
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

        MenuDto menuDto = MenuDto.toRes(menu, menuCategory, optionDtoList);
        return menuDto;
    }

    @Override
    public StoreMenuAllDto selectAllMenu(Integer userId) {
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

        List<MenuDto> menuDtoList = new ArrayList<>();

        //메뉴 카테고리 리스트 조회
        List<MenuCategory> menuCategoryList = menuCategoryRepository.findAllByStoreOrderBySequence(userId, CommonStatus.activeAndPrivate);
        List<MenuCategoryDto> mcdList = new ArrayList<>();

        for(MenuCategory mc : menuCategoryList){
            MenuCategoryDto mcDto = MenuCategoryDto.toRes(mc);
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
            
            MenuDto menuDto = MenuDto.toRes(menu, menuCategory, optionDtoList);
            menuDtoList.add(menuDto);
        }

        StoreMenuAllDto storeMenuAllDto = StoreMenuAllDto.toRes(mcdList, menuDtoList);

        return storeMenuAllDto;
    }

    @Override
    @Transactional
    public void updateSequence(MenuSequenceReq menuSequenceReq) {
        //1. 카테고리 순서 변경
        List<MenuCategoryDto> menuCategoryDtoList = menuSequenceReq.getCategory();
        List<MenuDto> menuDtoList = menuSequenceReq.getMenu();

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
        if(menuDtoList != null){
            for(MenuDto menuDto : menuDtoList){
                Integer mcId = menuDto.getMcId();
                Integer menuId = menuDto.getId();
                MenuCategory mc = null;
                if(mcId != null && mcId != 0){
                    mc = menuCategoryRepository.findById(mcId, CommonStatus.activeAndPrivate).orElseThrow(
                            () -> new EntityNotFoundException("MenuCategory Not Found")
                    );
                }

                Menu menu = menuRepository.findById(menuId, CommonStatus.activeAndPrivate).orElseThrow(
                        () -> new EntityNotFoundException("Menu Not Found")
                );
                menuRepository.updateSequence(menuId, menuDto.getSequence(), mc, menuDto.getStatus());
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

        MenuDto menu = MenuDto.toRes(timeSaleMenu.getMenu(), timeSaleMenu.getMenu().getMenuCategory(), moDtos);

        TimeSaleDto timeSaleDto = TimeSaleDto.toRes(timeSale, TimeSaleMenuDto.toRes(timeSaleMenu, menu));
        return timeSaleDto;
    }

    @Override
    public PartnershipRes selectPartnershipMenuByBuyer(Integer stoId, Integer typeId) {
        return null;
    }

    @Override
    public MenuAllDto selectAllMenuByBuyer(Integer stoId) {
        //1. 상점 존재 확인
        Store store = storeRepository.findById(stoId, UserStatus.ACTIVE).orElseThrow(
                () -> new UserNotFoundException("Store Not Found"));
        //2. 타임 세일 존재 확인
        List<TimeSale> timeSaleList = timeSaleRepository.findByStoreAndStatus(stoId, TimeSaleStatus.ONGOING);

        //3. 타임 세일 상품 조회
        List<TimeSaleDto> timeSaleDtos = new ArrayList<>();

        if(timeSaleList != null){
            for(TimeSale s : timeSaleList){
                TimeSaleMenu timeSaleMenu = timeSaleMenuRepository.findBySale(s.getId()).orElseThrow(
                        () -> new EntityNotFoundException("Sale Menu Not Found")
                );

                Menu menu = timeSaleMenu.getMenu();
                MenuDto menuDto = MenuDto.toRes(menu, menu.getMenuCategory(), null);
                TimeSaleMenuDto timeSaleMenuDto = TimeSaleMenuDto.toRes(timeSaleMenu, menuDto);
                timeSaleDtos.add(TimeSaleDto.toRes(s, timeSaleMenuDto));
            }
        }

        //메뉴 카테고리 리스트 조회
        List<MenuCategory> menuCategoryList = menuCategoryRepository.findAllByStoreOrderBySequence(stoId, CommonStatus.activeAndPrivate);
        List<MenuCategoryDto> mcdList = new ArrayList<>();

        for(MenuCategory mc : menuCategoryList){
            MenuCategoryDto mcDto = MenuCategoryDto.toRes(mc);
            mcdList.add(mcDto);
        }

        MenuAllDto menuAllDto = MenuAllDto.toRes(mcdList, timeSaleDtos);
        return menuAllDto;
    }
}
