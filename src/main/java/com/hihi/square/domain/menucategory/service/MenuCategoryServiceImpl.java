package com.hihi.square.domain.menucategory.service;

import com.hihi.square.common.CommonStatus;
import com.hihi.square.domain.buyer.entity.Buyer;
import com.hihi.square.domain.menu.repository.MenuRepository;
import com.hihi.square.domain.menucategory.dto.MenuCategoryDto;
import com.hihi.square.domain.menucategory.entity.MenuCategory;
import com.hihi.square.domain.menucategory.repository.MenuCategoryRepository;
import com.hihi.square.domain.store.entity.Store;
import com.hihi.square.domain.store.repository.StoreRepository;
import com.hihi.square.domain.user.entity.User;
import com.hihi.square.domain.user.entity.UserStatus;
import com.hihi.square.domain.user.repository.UserRepository;
import com.hihi.square.global.error.type.DeletionNotAllowedException;
import com.hihi.square.global.error.type.EntityNotFoundException;
import com.hihi.square.global.error.type.UserMismachException;
import com.hihi.square.global.error.type.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.MethodNotAllowedException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuCategoryServiceImpl implements MenuCategoryService {
    private final MenuCategoryRepository menuCategoryRepository;
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;


    public Store checkUserMatching(Integer stoId, Integer reqStoId){
        Store store = storeRepository.findById(stoId).orElseThrow(() -> new UserNotFoundException("User Not Found"));
        if(stoId != reqStoId) throw new UserMismachException("User Mismatch");
        return store;
    }

    @Transactional
    @Override
    public void addCategory(Integer userId, MenuCategoryDto menuCategoryReq) {
        //1. 사전 체크
        User user = userRepository.findByUserId(userId, UserStatus.ACTIVE).orElseThrow(
                () -> new UserNotFoundException("User Not Found")
        );

        if(user instanceof Buyer) throw new UserMismachException("Only Store add");

        //1-1. 로그인한 store와 등록 store 일치 여부
        Store store = checkUserMatching(userId, menuCategoryReq.getStoId());

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
    public void deleteCategory(Integer userId, Integer categoryId) {
        //1. 사전 체크
        User user = userRepository.findByUserId(userId, UserStatus.ACTIVE).orElseThrow(
                () -> new UserNotFoundException("User Not Found")
        );

        if(user instanceof Buyer) throw new UserMismachException("Only Store add");

        //1-1. 카테고리 존재 여부
        //1-2. 로그인한 store와 등록 store 일치 여부
        MenuCategory menuCategory = menuCategoryRepository.findById(categoryId, CommonStatus.activeAndPrivate).orElseThrow(() -> new EntityNotFoundException("MenuCategory Not Found"));
        Store store = checkUserMatching(userId, menuCategory.getStore().getUsrId());

        //1-2. 해당 카테고리에 상품이 없는지 확인 ---> 없으면 empty(null x)
        if(!menuRepository.findByCategory(categoryId).isEmpty()) throw new DeletionNotAllowedException("Category Doesn't Empty");

        //2. 카테고리 삭제
        menuCategoryRepository.deleteCategory(categoryId);
    }

    @Transactional
    @Override
    public void updateCategory(Integer userId, Integer categoryId, MenuCategoryDto menuCategoryReq) {
        //1. 사전 체크
        User user = userRepository.findByUserId(userId, UserStatus.ACTIVE).orElseThrow(
                () -> new UserNotFoundException("User Not Found")
        );

        if(user instanceof Buyer) throw new UserMismachException("Only Store add");
        //1-1. 카테고리 가져옴
        //1-2. 로그인한 store와 등록 store 일치 여부
        MenuCategory menuCategory = menuCategoryRepository.findById(categoryId, CommonStatus.activeAndPrivate).orElseThrow(() -> new EntityNotFoundException("MenuCategory Not Found"));
        Store store = checkUserMatching(userId, menuCategory.getStore().getUsrId());
        int originSeq = menuCategory.getSequence();

        //2. 카테고리 변경
        //2-1. 아이디, 상태 지정
        menuCategoryReq.setId(menuCategory.getId());
        menuCategoryReq.setStatus(menuCategory.getStatus());
        MenuCategory menuCategoryToSave = MenuCategory.toEntity(menuCategoryReq, store);
        int newSeq = menuCategoryToSave.getSequence();
        menuCategoryRepository.save(menuCategoryToSave);

        if(originSeq == newSeq) return;

        //3. 순서 바뀐 경우 : 나머지 애들 순서 변경
        //3-1. 현재 store에 해당하는 category List 가져옴(ACTIVE, PRIVATE)
        List<MenuCategory> categoryList = menuCategoryRepository.findAllByStoreOrderBySequence(
                userId,
                CommonStatus.activeAndPrivate
        );

        //3-2. 변경한 카테고리 기준으로 나머지 애들 순서 변경
        changeSequence(categoryList, originSeq, newSeq);

    }

    @Transactional
    public void changeSequence(List<MenuCategory> categoryList, Integer originSeq, Integer newSeq) {
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
    public List<MenuCategoryDto> selectAllCategory(Integer userId) {
        List<MenuCategory> menuCategoryList = menuCategoryRepository.findAllByStoreOrderBySequence(userId, CommonStatus.activeAndPrivate);
        List<MenuCategoryDto> menuCategoryDtoList = new ArrayList<>();

        for(MenuCategory mc : menuCategoryList){
            MenuCategoryDto mcDto = MenuCategoryDto.toRes(mc);
            menuCategoryDtoList.add(mcDto);
        }

        return menuCategoryDtoList;
    }
}
