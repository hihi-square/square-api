package com.hihi.square.domain.menu.service;

import com.hihi.square.domain.menu.dto.MenuDto;
import com.hihi.square.domain.menu.dto.MenuSequenceReq;

import java.util.List;

public interface MenuService {
    void addMenu(Integer stoId, MenuDto menuDto);
    
    void deleteMenu(Integer stoId, Integer menuId);

    void updateMenu(Integer stoId, Integer menuId, MenuDto menuDto);
    
    //상세 메뉴 보기
    MenuDto selectMenu(Integer stoId, Integer menuId);
    
    //전체 메뉴 보기
    List<MenuDto> selectAllMenu(Integer stoId);

    void updateSequence(MenuSequenceReq menuSequenceReq);
}
