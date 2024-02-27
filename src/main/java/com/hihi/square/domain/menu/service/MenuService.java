package com.hihi.square.domain.menu.service;

import com.hihi.square.domain.menu.dto.*;
import com.hihi.square.domain.partnership.dto.response.PartnershipRes;
import com.hihi.square.domain.timesale.dto.TimeSaleDto;

public interface MenuService {
    void addMenu(Integer stoId, MenuDto menuDto);
    
    void deleteMenu(Integer stoId, Integer menuId);

    void updateMenu(Integer stoId, Integer menuId, MenuDto menuDto);
    
    //상세 메뉴 보기
    MenuDto selectMenu(Integer stoId, Integer menuId);
    
    //전체 메뉴 보기
    StoreMenuAllDto selectAllMenu(Integer stoId);

    void updateSequence(MenuSequenceReq menuSequenceReq);

    //타임 세일 메뉴 보기
    TimeSaleDto selectSaleMenuByBuyer(Integer stoId, Integer typeId);

    PartnershipRes selectPartnershipMenuByBuyer(Integer stoId, Integer typeId);

    MenuAllDto selectAllMenuByBuyer(Integer stoId);
}
