package com.example.websocketdemo.controller;

import com.example.websocketdemo.dto.SharingMessageDto;
import com.example.websocketdemo.service.SharingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class WebSocketController {

    @Autowired
    SharingService sharingService;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public SharingMessageDto sendMessage(@Payload SharingMessageDto sharingMessageDto) {
        sharingService.saveSharing(sharingMessageDto);
        return sharingMessageDto;
    }

    /*@MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public SharingMessageDto addUser(@Payload SharingMessageDto sharingMessageDto, SimpMessageHeaderAccessor headerAccessor) {

        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", sharingMessageDto.getUsername());
        return sharingMessageDto;
    }*/

}
