package com.example.websocketdemo.controller;

import com.example.websocketdemo.entity.Sharing;
import com.example.websocketdemo.service.SharingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SharingControllerTest {

    @Mock
    private SharingService sharingService;

    @InjectMocks
    private SharingController sharingController;

    @Test
    void testGetAllSharing_Success() {
        // Arrange
        List<Sharing> mockSharingList = new ArrayList<>();
        mockSharingList.add(new Sharing("User01", "Title 1", "http://localhost"));
        mockSharingList.add(new Sharing("user02" ,"Title 2", "http://url02"));

        // Mocking the behavior of getAllSharing() method
        when(sharingService.getAllSharing()).thenReturn(mockSharingList);

        // Act
        ResponseEntity<List<Sharing>> responseEntity = sharingController.getAllSharing();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockSharingList, responseEntity.getBody());
        verify(sharingService, times(1)).getAllSharing();
    }

    @Test
    void testGetAllSharing_NoContent() {
        // Arrange
        List<Sharing> mockEmptyList = new ArrayList<>();

        // Mocking the behavior of getAllSharing() method
        when(sharingService.getAllSharing()).thenReturn(mockEmptyList);

        // Act
        ResponseEntity<List<Sharing>> responseEntity = sharingController.getAllSharing();

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertEquals(mockEmptyList, responseEntity.getBody());
        verify(sharingService, times(1)).getAllSharing();
    }

    @Test
    void testGetAllSharing_InternalServerError() {
        // Mocking the behavior of getAllSharing() method to throw an exception
        when(sharingService.getAllSharing()).thenThrow(new RuntimeException());

        // Act
        ResponseEntity<List<Sharing>> responseEntity = sharingController.getAllSharing();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());
        verify(sharingService, times(1)).getAllSharing();
    }
}

