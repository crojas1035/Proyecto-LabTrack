package com.labtrack.repository;

import com.labtrack.domain.DetallePrestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallePrestamoRepository extends JpaRepository<DetallePrestamo, Integer> {
}
