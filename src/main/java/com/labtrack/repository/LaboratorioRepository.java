package com.labtrack.repository;

import com.labtrack.domain.Laboratorio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaboratorioRepository extends JpaRepository<Laboratorio, Integer> {
    
    // Consulta derivada idéntica al ejemplo de tu tienda para traer solo laboratorios activos
    public List<Laboratorio> findByActivoTrue();
}
