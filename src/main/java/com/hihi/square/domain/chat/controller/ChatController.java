package com.hihi.square.domain.chat.controller;

import com.hihi.square.common.CommonRes;
import com.hihi.square.domain.chat.dto.response.ChatRoomRes;
import com.hihi.square.domain.chat.service.ChatRoomService;
import com.hihi.square.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class ChatController {

    private final ChatRoomService chatRoomService;
    private final ChatService chatService;

    @GetMapping("/rooms")
    public ResponseEntity getRooms(Authentication authentication) {
        Integer stoId = Integer.parseInt(authentication.getName());
        List<ChatRoomRes> res = chatRoomService.getAllRoom(stoId);
        return new ResponseEntity(CommonRes.success(res), HttpStatus.OK);
    }



}

