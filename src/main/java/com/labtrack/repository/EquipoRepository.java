package com.labtrack.repository;

import com.labtrack.domain.Equipo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Integer> {
    
    public List<Equipo> findByActivoTrue();
}
