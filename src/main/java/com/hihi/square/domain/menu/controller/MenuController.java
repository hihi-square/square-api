package com.hihi.square.domain.menu.controller;

import com.hihi.square.common.CommonRes;
import com.hihi.square.domain.menu.dto.MenuReq;
import com.hihi.square.domain.menu.dto.MenuSequenceReq;
import com.hihi.square.domain.menu.service.MenuServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/store/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuServiceImpl menuService;

    @PostMapping
    public ResponseEntity<?> addMenu(Authentication authentication, @RequestBody @Validated MenuReq menuReq) {
        Integer stoId = Integer.parseInt(authentication.getName());
        menuService.addMenu(stoId, menuReq);
        return new ResponseEntity<>(CommonRes.success(null), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{menu_id}")
    public ResponseEntity<?> deleteMenu(Authentication authentication, @PathVariable(name = "menu_id") @Validated Integer menuId) {
        Integer stoId = Integer.parseInt(authentication.getName());
        menuService.deleteMenu(stoId, menuId);
        return new ResponseEntity<>(CommonRes.success(null), HttpStatus.ACCEPTED);
    }

    @PatchMapping("/{menu_id}")
    public ResponseEntity<?> updateMenu(Authentication authentication, @PathVariable(name = "menu_id") @Validated Integer menuId,
                                        @RequestBody @Validated MenuReq menuReq) {
        Integer stoId = Integer.parseInt(authentication.getName());
        menuService.updateMenu(stoId, menuId, menuReq);
        return new ResponseEntity<>(CommonRes.success(null), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{menu_id}")
    public ResponseEntity<?> selectMenu(Authentication authentication, @PathVariable(name = "menu_id") @Validated Integer menuId) {
        Integer stoId = Integer.parseInt(authentication.getName());
        MenuReq menuReq = menuService.selectMenu(stoId, menuId);
        return new ResponseEntity<>(CommonRes.success(menuReq), HttpStatus.ACCEPTED);
    }

    @GetMapping("/all")
    public ResponseEntity<?> selectAllMenu(Authentication authentication) {
        Integer stoId = Integer.parseInt(authentication.getName());
        List<MenuReq> menuList = menuService.selectAllMenu(stoId);
        return new ResponseEntity<>(CommonRes.success(menuList), HttpStatus.ACCEPTED);
    }

    @PatchMapping("/sequence")
    public ResponseEntity<?> updateAllSequence(@RequestBody @Validated MenuSequenceReq menuSequenceReq){
        menuService.updateSequence(menuSequenceReq);
        return new ResponseEntity<>(CommonRes.success(null), HttpStatus.ACCEPTED);
    }
}