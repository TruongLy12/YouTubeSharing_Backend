package com.example.websocketdemo.service;

import com.example.websocketdemo.dto.SharingMessageDto;
import com.example.websocketdemo.entity.Sharing;
import com.example.websocketdemo.repository.ISharingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SharingService {
    @Autowired
    private ISharingRepository sharingRepository;

    public boolean saveSharing(SharingMessageDto dto) {
        try {
            sharingRepository.save(new Sharing(dto.getUsername(), dto.getTitle(), dto.getUrl()));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Sharing> getAllSharing() {
        return sharingRepository.findAll();
    }
}
