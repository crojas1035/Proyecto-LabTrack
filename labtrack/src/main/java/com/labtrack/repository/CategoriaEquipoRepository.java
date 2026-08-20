package com.labtrack.repository;

import com.labtrack.domain.CategoriaEquipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaEquipoRepository extends JpaRepository<CategoriaEquipo, Integer> {
}
