package com.max.api.v1.repositories;

import com.max.api.v1.model.BinaryContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface BinaryContentRepository extends JpaRepository<BinaryContent, Integer> {
}
