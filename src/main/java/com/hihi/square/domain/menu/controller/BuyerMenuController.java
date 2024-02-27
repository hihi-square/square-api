package com.hihi.square.domain.menu.controller;

import com.hihi.square.common.CommonRes;
import com.hihi.square.domain.menu.dto.MenuAllDto;
import com.hihi.square.domain.menu.entity.SaleType;
import com.hihi.square.domain.menu.service.MenuService;
import com.hihi.square.domain.timesale.dto.TimeSaleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/buyer/store")
@RequiredArgsConstructor
public class BuyerMenuController {
    private final MenuService menuService;

    //type_id : sale_id or partnership_id
    @GetMapping("/{store_id}/menu/{type_id}")
    public ResponseEntity<?> selectMenu(@PathVariable(name = "store_id") @Validated Integer stoId,
                                        @PathVariable("type_id") @Validated Integer id,
                                        @RequestParam("type") SaleType type) {

        if(type == SaleType.TIME){
            TimeSaleDto timeSaleDto = menuService.selectSaleMenuByBuyer(stoId, id);
            return new ResponseEntity<>(CommonRes.success(timeSaleDto), HttpStatus.ACCEPTED);
        }
        else return new ResponseEntity<>(CommonRes.success(null), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{store_id}/menu")
    public ResponseEntity<?> selectAllMenu(@PathVariable(name = "store_id") @Validated Integer stoId) {
        MenuAllDto saleMenus = menuService.selectAllMenuByBuyer(stoId);
        return new ResponseEntity<>(CommonRes.success(saleMenus), HttpStatus.ACCEPTED);
    }
}
