package com.hihi.square.domain.menu.controller;

import com.hihi.square.common.CommonRes;
import com.hihi.square.domain.menu.dto.MenuAllDto;
import com.hihi.square.domain.menu.dto.MenuDto;
import com.hihi.square.domain.menu.service.MenuService;
import com.hihi.square.global.error.type.UserMismachException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/buyer/store")
@RequiredArgsConstructor
public class BuyerMenuController {
    private final MenuService menuService;

    @GetMapping("/{store_id}/menu/{menu_id}")
    public ResponseEntity<?> selectMenu(Authentication authentication, @PathVariable(name = "store_id") @Validated Integer pathStoId,
                                        @PathVariable("menu_id") @Validated Integer menuId) {
        Integer stoId = Integer.parseInt(authentication.getName());
        if(stoId != pathStoId) throw new UserMismachException("Store And Path MisMatch");
        MenuDto menuDto = menuService.selectMenu(stoId, menuId);
        return new ResponseEntity<>(CommonRes.success(menuDto), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{store_id}/menu")
    public ResponseEntity<?> selectAllMenu(Authentication authentication, @PathVariable(name = "store_id") @Validated Integer pathStoId) {
        Integer stoId = Integer.parseInt(authentication.getName());
        if(stoId != pathStoId) throw new UserMismachException("Store And Path MisMatch");
        MenuAllDto menuList = menuService.selectAllMenu(stoId);
        return new ResponseEntity<>(CommonRes.success(menuList), HttpStatus.ACCEPTED);
    }
}
