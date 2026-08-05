package com.labtrack.repository;

import com.labtrack.domain.Laboratorio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaboratorioRepository
        extends JpaRepository<Laboratorio, Integer> {

    public List<Laboratorio> findByActivoTrue();
    public List<Laboratorio> findByEstadoIgnoreCase(String estado);
}