package com.example.websocketdemo.controller;

import com.example.websocketdemo.dto.SharingMessageDto;
import com.example.websocketdemo.service.SharingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WebSocketControllerTest {

    @Mock
    private SharingService sharingService;

    @InjectMocks
    private WebSocketController webSocketController;

    @Test
    void testSendMessage() {
        // Arrange
        SharingMessageDto sharingMessageDto = new SharingMessageDto("http://local", "Hello, world!", "User01");

        // Act
        SharingMessageDto result = webSocketController.sendMessage(sharingMessageDto);

        // Assert
        assertEquals(sharingMessageDto, result);
        verify(sharingService, times(1)).saveSharing(sharingMessageDto);
    }
}

