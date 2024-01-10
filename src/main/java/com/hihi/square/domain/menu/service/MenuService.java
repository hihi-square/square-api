package com.hihi.square.domain.menu.service;

import com.hihi.square.domain.menu.dto.MenuReq;
import com.hihi.square.domain.menu.dto.MenuSequenceReq;

import java.util.List;

public interface MenuService {
    void addMenu(Integer stoId, MenuReq menuReq);
    
    void deleteMenu(Integer stoId, Integer menuId);

    void updateMenu(Integer stoId, Integer menuId, MenuReq menuReq);
    
    //상세 메뉴 보기
    MenuReq selectMenu(Integer stoId, Integer menuId);
    
    //전체 메뉴 보기
    List<MenuReq> selectAllMenu(Integer stoId);

    void updateSequence(MenuSequenceReq menuSequenceReq);
}
