package com.labtrack.repository;

import com.labtrack.domain.ReporteFalla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReporteFallaRepository extends JpaRepository<ReporteFalla, Integer> {
}
