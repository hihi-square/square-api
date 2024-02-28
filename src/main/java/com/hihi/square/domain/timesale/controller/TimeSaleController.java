package com.hihi.square.domain.timesale.controller;

import com.hihi.square.common.CommonRes;
import com.hihi.square.domain.timesale.dto.TimeSaleDto;
import com.hihi.square.domain.timesale.service.TimeSaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/store/time-sales")
@RequiredArgsConstructor
@Slf4j
public class TimeSaleController {
    private final TimeSaleService timeSaleService;

    @PostMapping
    public ResponseEntity<?> addSale(Authentication authentication, @RequestBody @Validated TimeSaleDto timeSaleDto) {
        Integer stoId = Integer.parseInt(authentication.getName());
        timeSaleService.addSale(stoId, timeSaleDto);
        return new ResponseEntity<>(CommonRes.success(null), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{time_sale_id}")
    public ResponseEntity<?> deleteSale(Authentication authentication, @PathVariable(name = "time_sale_id") @Validated Integer saleId) {
        Integer stoId = Integer.parseInt(authentication.getName());
        timeSaleService.deleteSale(stoId, saleId);
        return new ResponseEntity<>(CommonRes.success(null), HttpStatus.ACCEPTED);
    }

    @PatchMapping("/{time_sale_id}")
    public ResponseEntity<?> updateSale(Authentication authentication, @PathVariable(name = "time_sale_id") @Validated Integer saleId,
                                        @RequestBody @Validated TimeSaleDto timeSaleDto) {
        Integer stoId = Integer.parseInt(authentication.getName());
        timeSaleService.updateSale(stoId, saleId, timeSaleDto);
        return new ResponseEntity<>(CommonRes.success(null), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{time_sale_id}")
    public ResponseEntity<?> selectSale(Authentication authentication, @PathVariable(name = "time_sale_id") @Validated Integer saleId) {
        Integer stoId = Integer.parseInt(authentication.getName());
        TimeSaleDto timeSaleDto = timeSaleService.selectSale(stoId, saleId);
        return new ResponseEntity<>(CommonRes.success(timeSaleDto), HttpStatus.ACCEPTED);
    }

    @GetMapping("/all")
    public ResponseEntity<?> selectAllSale(Authentication authentication) {
        Integer stoId = Integer.parseInt(authentication.getName());
        List<TimeSaleDto> saleMenus = timeSaleService.selectAllSale(stoId);
        return new ResponseEntity<>(CommonRes.success(saleMenus), HttpStatus.ACCEPTED);
    }
}
