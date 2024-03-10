package com.max.api.v1.repositories;

import com.max.api.v1.model.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DomainRepository extends JpaRepository<Domain, Long> {
    Optional<Domain> findByTitle(String title);

    void deleteByTitle(String title);
}
