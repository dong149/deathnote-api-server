package com.rest.api.repository;

import com.rest.api.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchDtoJpaRepo extends JpaRepository<Report, Long> {
}
