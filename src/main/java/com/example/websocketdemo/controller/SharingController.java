package com.example.websocketdemo.controller;

import com.example.websocketdemo.entity.Sharing;
import com.example.websocketdemo.service.SharingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SharingController {

    @Autowired
    SharingService sharingService;
    
    @GetMapping("/sharings")
    public ResponseEntity<List<Sharing>> getAllSharing() {
        try {
            List<Sharing> listSharing = new ArrayList<Sharing>(sharingService.getAllSharing());

            if (listSharing.isEmpty()) {
                return new ResponseEntity<>(listSharing, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(listSharing, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
