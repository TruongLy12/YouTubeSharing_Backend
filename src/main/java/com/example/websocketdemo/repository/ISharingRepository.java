package com.example.websocketdemo.repository;

import com.example.websocketdemo.entity.Sharing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISharingRepository extends JpaRepository<Sharing, Long> {
}
