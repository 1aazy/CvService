package com.max.api.v1.service;

import com.max.api.v1.model.BinaryContent;
import com.max.api.v1.repositories.BinaryContentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class CvService {

    private final BinaryContentRepository binaryContentRepository;

    @Transactional
    public void saveCv(BinaryContent cv) {
        binaryContentRepository.save(cv);
    }

    public BinaryContent getCv(int id) {
        return binaryContentRepository.findById(id).orElse(null);
    }
}
