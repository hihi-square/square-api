package com.hihi.square.domain.menucategory.controller;

import com.hihi.square.common.CommonRes;
import com.hihi.square.domain.menucategory.dto.MenuCategoryDto;
import com.hihi.square.domain.menucategory.service.MenuCategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu/category")
@RequiredArgsConstructor
@CrossOrigin("*")
public class MenuCategoryController {
    private final MenuCategoryServiceImpl menuCategoryService;

    @PostMapping
    public ResponseEntity<?> addMenuCategory(Authentication authentication,
                                             @RequestBody @Validated MenuCategoryDto menuCategoryReq){
        Integer stoId = Integer.parseInt(authentication.getName());
        menuCategoryService.addCategory(stoId, menuCategoryReq);
        return new ResponseEntity<>(CommonRes.success(null), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{category_id}")
    public ResponseEntity<?> deleteMenuCategory(Authentication authentication, @PathVariable(name = "category_id") @Validated Integer categoryId){
        Integer stoId = Integer.parseInt(authentication.getName());
        menuCategoryService.deleteCategory(stoId, categoryId);
        return new ResponseEntity<>(CommonRes.success(null), HttpStatus.ACCEPTED);
    }

    @PatchMapping("/{category_id}")
    public ResponseEntity<?> updateMenuCategory(Authentication authentication, @PathVariable(name = "category_id") @Validated Integer categoryId,
                                                @RequestBody @Validated MenuCategoryDto menuCategoryReq){
        Integer stoId = Integer.parseInt(authentication.getName());
        menuCategoryService.updateCategory(stoId, categoryId, menuCategoryReq);
        return new ResponseEntity<>(CommonRes.success(null), HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<?> selectMenuCategory(Authentication authentication){
        Integer stoId = Integer.parseInt(authentication.getName());
        List<MenuCategoryDto> menuCategoryList = menuCategoryService.selectAllCategory(stoId);
        return new ResponseEntity<>(CommonRes.success(menuCategoryList), HttpStatus.ACCEPTED);
    }
}
