package com.hihi.square.domain.menucategory.service;

import com.hihi.square.domain.menucategory.dto.request.MenuCategoryDto;
import com.hihi.square.domain.menu.entity.CommonStatus;
import com.hihi.square.domain.menucategory.entity.MenuCategory;
import com.hihi.square.domain.menucategory.repository.MenuCategoryRepository;
import com.hihi.square.domain.menu.repository.MenuRepository;
import com.hihi.square.domain.store.entity.Store;
import com.hihi.square.domain.store.repository.StoreRepository;
import com.hihi.square.global.error.type.DeletionNotAllowedException;
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
public class MenuCategoryServiceImpl implements MenuCategoryService {
    private final MenuCategoryRepository menuCategoryRepository;
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;


    public Store checkUserMatching(Integer stoId, Integer reqStoId){
        Store store = storeRepository.findById(stoId).orElseThrow(() -> new UserNotFoundException("User Not Found"));
        if(stoId != reqStoId) throw new UserMismachException("User Mismatch");
        return store;
    }

    @Transactional
    @Override
    public void addCategory(Integer stoId, MenuCategoryDto menuCategoryReq) {
        //1. 로그인한 store와 등록 store 일치 여부
        Store store = checkUserMatching(stoId, menuCategoryReq.getStoId());

        //2. 카테고리 등록
        //2-1. 마지막 순서 지정
        Integer sequence = menuCategoryRepository.findSequence(CommonStatus.activeAndPrivate);
        if(sequence == null) sequence = 1;
        menuCategoryReq.setSequence(sequence); //마지막 순서로 등록

        //2-2. 상태 등록
        if(menuCategoryReq.getStatus() == null) menuCategoryReq.setStatus(CommonStatus.ACTIVE);

        MenuCategory menuCategory = MenuCategory.toEntity(menuCategoryReq, store);
        menuCategoryRepository.save(menuCategory);
    }

    @Transactional
    @Override
    public void deleteCategory(Integer stoId, Integer categoryId) {
        //1. 사전 체크
        //1-1. 카테고리 존재 여부
        //1-2. 로그인한 store와 등록 store 일치 여부
        MenuCategory menuCategory = menuCategoryRepository.findById(categoryId, CommonStatus.activeAndPrivate).orElseThrow(() -> new EntityNotFoundException("MenuCategory Not Found"));
        Store store = checkUserMatching(stoId, menuCategory.getStore().getUsrId());

        //1-2. 해당 카테고리에 상품이 없는지 확인
        if(menuRepository.findByCategory(categoryId) != null) throw new DeletionNotAllowedException("Category Doesn't Empty");

        //2. 카테고리 삭제
        menuCategoryRepository.deleteCategory(categoryId);
    }

    @Transactional
    @Override
    public void updateCategory(Integer stoId, Integer categoryId, MenuCategoryDto menuCategoryReq) {
        //1. 사전 체크
        //1-1. 카테고리 가져옴
        //1-2. 로그인한 store와 등록 store 일치 여부
        MenuCategory menuCategory = menuCategoryRepository.findById(categoryId, CommonStatus.activeAndPrivate).orElseThrow(() -> new EntityNotFoundException("MenuCategory Not Found"));
        Store store = checkUserMatching(stoId, menuCategory.getStore().getUsrId());
        int originSeq = menuCategory.getSequence();

        //2. 카테고리 변경
        //2-1. 상태 지정
        menuCategoryReq.setId(menuCategory.getId());
        menuCategoryReq.setStatus(menuCategory.getStatus());
        log.info("req : {}", menuCategoryReq);
        MenuCategory menuCategoryToSave = MenuCategory.toEntity(menuCategoryReq, store);
        menuCategoryRepository.save(menuCategoryToSave);

        //3. 나머지 애들 순서 변경
        //3-1. 현재 store에 해당하는 category List 가져옴(ACTIVE, PRIVATE)
        List<MenuCategory> categoryList = menuCategoryRepository.findAllByStoreOrderBySequence(
                stoId,
                CommonStatus.activeAndPrivate
        );

        log.info("list : {}", categoryList);

        //3-2. 변경한 카테고리 기준으로 나머지 애들 순서 변경
        //3-2-1. 변경한 sequence < 원래 sequence
        int newSeq = menuCategoryToSave.getSequence();
        log.info("origin : {}, new : {}", originSeq, newSeq);
        changeSequence(categoryList, originSeq, newSeq);

    }

    @Transactional
    private void changeSequence(List<MenuCategory> categoryList, Integer originSeq, Integer newSeq) {
        int start = 0;
        int finish = categoryList.size();
        if(originSeq < newSeq){
            //이전애들 sequence -1
            start = originSeq == 0 ? 0 : originSeq-1;
            finish = newSeq - 2;
            for(int i=start; i<finish; i++){
                log.info("i : {}", i);
                MenuCategory mc = categoryList.get(i);
                menuCategoryRepository.updateSequence(mc.getId(), mc.getSequence()-1);
            }
            //newSeq 자리에 있는 애 -1
            MenuCategory mc = categoryList.get(newSeq-1);   //순서는 1부터 인덱스는 0부터
            menuCategoryRepository.updateSequence(mc.getId(), mc.getSequence()-1);
        }
        else if(originSeq > newSeq){
            //다음애들 sequence + 1
            start = newSeq;
            finish = originSeq;
            for(int i=start; i<finish; i++){
                MenuCategory mc = categoryList.get(i);
                menuCategoryRepository.updateSequence(mc.getId(), mc.getSequence()+1);
            }
        }

    }

    @Override
    public List<MenuCategoryDto> selectCategory(Integer stoId) {
        List<MenuCategory> menuCategoryList = menuCategoryRepository.findAllByStoreOrderBySequence(stoId, CommonStatus.activeAndPrivate);
        List<MenuCategoryDto> menuCategoryDtoList = new ArrayList<>();

        for(MenuCategory mc : menuCategoryList){
            MenuCategoryDto mcDto = MenuCategoryDto.toRes(mc);
            menuCategoryDtoList.add(mcDto);
        }

        return menuCategoryDtoList;
    }
}