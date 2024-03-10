package com.max.api.v1.repositories;

import com.max.api.v1.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Integer> {
    Optional<Test> findByTitle(String title);

    void deleteByTitle(String title);
}
