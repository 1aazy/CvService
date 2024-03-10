package com.max.api.v1.service;

import com.max.api.v1.model.BinaryContent;
import com.max.api.v1.repositories.BinaryContentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class PhotoService {

    private final BinaryContentRepository photoRepository;

    @Transactional
    public void savePhoto(BinaryContent photo) {
        photoRepository.save(photo);
    }

    public BinaryContent getPhoto(int id) {
        return photoRepository.findById(id).orElse(null);
    }
}
