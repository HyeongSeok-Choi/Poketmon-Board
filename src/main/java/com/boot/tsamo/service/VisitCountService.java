package com.boot.tsamo.service;

import com.boot.tsamo.entity.VisitCount;
import com.boot.tsamo.repository.VisitCountRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitCountService {

    @Autowired
    private VisitCountRepository visitCountRepository;

    public List<VisitCount> findAll() {
        return visitCountRepository.findAll();
    }
}
