package com.hihi.square.domain.menu.service;

import com.hihi.square.common.CommonStatus;
import com.hihi.square.domain.menu.dto.MenuReq;
import com.hihi.square.domain.menu.dto.MenuSequenceReq;
import com.hihi.square.domain.menu.entity.Menu;
import com.hihi.square.domain.menu.repository.MenuRepository;
import com.hihi.square.domain.menucategory.dto.MenuCategoryDto;
import com.hihi.square.domain.menucategory.entity.MenuCategory;
import com.hihi.square.domain.menucategory.repository.MenuCategoryRepository;
import com.hihi.square.domain.menu.dto.MenuOptionDto;
import com.hihi.square.domain.menu.entity.MenuOption;
import com.hihi.square.domain.menu.repository.MenuOptionRepository;
import com.hihi.square.domain.store.entity.Store;
import com.hihi.square.domain.store.repository.StoreRepository;
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
    private final StoreRepository storeRepository;

    public Store checkUserMatching(Integer stoId, Integer reqStoId){
        Store store = storeRepository.findById(stoId).orElseThrow(() -> new UserNotFoundException("User Not Found"));
        if(!stoId.equals(reqStoId)) throw new UserMismachException("User Mismatch");
        return store;
    }

    @Override
    @Transactional
    public void addMenu(Integer stoId, MenuReq menuReq) {
        //1. 사전 체크
        //1-1. 로그인한 store와 등록 store 일치 여부
        Store store = checkUserMatching(stoId, menuReq.getStoId());
        
        //2. 메뉴 등록
        //2-1. 메뉴 카테고리 찾기(있으면)
        Integer mcId = menuReq.getMcId();
        MenuCategory menuCategory = null;
        if(mcId != null && mcId != 0){
            menuCategory = menuCategoryRepository.findById(mcId, CommonStatus.activeAndPrivate).orElseThrow(()
                    -> new EntityNotFoundException("MenuCategory Not Found"));
        }

        //2-2. 마지막 순서 지정
        Integer sequence = menuRepository.findSequence(CommonStatus.activeAndPrivate);
        if(sequence == null) sequence = 1;
        menuReq.setSequence(sequence); //마지막 순서로 등록

        Menu menu = Menu.toEntity(menuReq, menuCategory, store);
        menu = menuRepository.save(menu);

        //3. 옵션 등록
        if(menuReq.getOptions() != null){
            List<MenuOptionDto> optionReqList = menuReq.getOptions();
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
    public void updateMenu(Integer stoId, Integer menuId, MenuReq menuReq) {
        //1. 사전 체크
        //1-1. menu 존재 여부
        Menu menu = menuRepository.findById(menuId, CommonStatus.activeAndPrivate).orElseThrow(()
                -> new EntityNotFoundException("Menu Not Found"));
        //1-2. 로그인한 store와 등록 store 일치 여부
        Store store = checkUserMatching(stoId, menu.getStore().getUsrId());

        //2. 메뉴 수정
        //2-1. 카테고리 존재 여부
        Menu menuToSave = menu;
        if(menuReq.getId() != null){
            Integer mcId = menuReq.getMcId();
            MenuCategory menuCategory = null;
            if(mcId != null && mcId != 0){
                menuCategory = menuCategoryRepository.findById(mcId, CommonStatus.activeAndPrivate).orElseThrow(()
                        -> new EntityNotFoundException("MenuCategory Not Found"));
            }
            //2-2. id 설정
            menuReq.setId(menu.getId());    //id 설정

            menuToSave = menuRepository.save(Menu.toEntity(menuReq, menuCategory, store));
        }

        //3. 옵션 등록
        if(menuReq.getOptions() != null){
            List<MenuOptionDto> optionReqList = menuReq.getOptions();

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
    public MenuReq selectMenu(Integer stoId, Integer menuId) {
        //1. 사전 체크
        //1-1. menu 존재 여부
        Menu menu = menuRepository.findById(menuId, CommonStatus.activeAndPrivate).orElseThrow(()
                -> new EntityNotFoundException("Menu Not Found"));
        //1-2. 로그인한 store와 등록 store 일치 여부
        Store store = checkUserMatching(stoId, menu.getStore().getUsrId());

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

        MenuReq menuReq = MenuReq.toRes(menu, mcId, optionDtoList);
        return menuReq;
    }

    @Override
    public List<MenuReq> selectAllMenu(Integer stoId) {
        List<Menu> menuList = menuRepository.findAllByStoreOrderBySequence(stoId, CommonStatus.activeAndPrivate);
        List<MenuReq> menuReqList = new ArrayList<>();

        for(Menu menu : menuList){
            //메뉴 카테고리 조회
            MenuCategory menuCategory = menu.getMenuCategory();
            Integer mcId = menuCategory == null ? 0 : menuCategory.getId(); //null인 경우 0 반환
            MenuReq menuReq = MenuReq.toRes(menu, mcId, null);
            menuReqList.add(menuReq);
        }

        return menuReqList;
    }

    @Override
    @Transactional
    public void updateSequence(MenuSequenceReq menuSequenceReq) {
        //1. 카테고리 순서 변경
        List<MenuCategoryDto> menuCategoryDtoList = menuSequenceReq.getCategory();
        List<MenuReq> menuReqList = menuSequenceReq.getMenu();

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
        if(menuReqList != null){
            for(MenuReq menuReq : menuReqList){
                Integer mcId = menuReq.getMcId();
                Integer menuId = menuReq.getId();
                MenuCategory mc = null;
                if(mcId != null && mcId != 0){
                    mc = menuCategoryRepository.findById(mcId, CommonStatus.activeAndPrivate).orElseThrow(
                            () -> new EntityNotFoundException("MenuCategory Not Found")
                    );
                }

                Menu menu = menuRepository.findById(menuId, CommonStatus.activeAndPrivate).orElseThrow(
                        () -> new EntityNotFoundException("Menu Not Found")
                );
                menuRepository.updateSequence(menuId, menuReq.getSequence(), mc, menuReq.getStatus());
            }
        }
    }
}
