package com.hihi.square.domain.category.controller;

import com.hihi.square.common.CommonRes;
import com.hihi.square.domain.category.dto.CategoryDto;
import com.hihi.square.domain.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> selectAllCategory(){
        List<CategoryDto> categoryList = categoryService.selectAllCategory();
        return new ResponseEntity<>(CommonRes.success(categoryList), HttpStatus.ACCEPTED);
    }
}
