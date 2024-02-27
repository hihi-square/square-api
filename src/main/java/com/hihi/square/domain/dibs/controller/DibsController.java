package com.hihi.square.domain.dibs.controller;

import com.hihi.square.common.CommonRes;
import com.hihi.square.domain.dibs.dto.DibsAllRes;
import com.hihi.square.domain.dibs.service.DibsService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dibs")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class DibsController {

    private final DibsService dibsService;

    @PostMapping("/{sto_id}")
    public ResponseEntity addDibs(Authentication authentication, @PathVariable("sto_id") Integer storeId) {
        Integer buyerId = Integer.parseInt(authentication.getName());
        dibsService.addDibs(buyerId, storeId);
        return new ResponseEntity(CommonRes.success(null), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity getAllDibs(Authentication authentication) {
        Integer buyerId = Integer.parseInt(authentication.getName());
        DibsAllRes list = dibsService.getAllDibsList(buyerId);
        return new ResponseEntity(CommonRes.success(list), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDibs(Authentication authentication, @PathVariable("id") Integer id) {
        Integer buyerId = Integer.parseInt(authentication.getName());
        dibsService.removeDibs(buyerId, id);
        return new ResponseEntity(CommonRes.success(null), HttpStatus.OK);
    }
}
