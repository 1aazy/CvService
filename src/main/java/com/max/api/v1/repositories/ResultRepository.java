package com.max.api.v1.repositories;

import com.max.api.v1.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<Result, Integer> {
}
