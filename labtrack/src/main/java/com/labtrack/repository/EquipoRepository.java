package com.labtrack.repository;

import com.labtrack.domain.Equipo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface EquipoRepository
        extends JpaRepository<Equipo, Integer> {

    public List<Equipo> findByActivoTrue();
    @Query("SELECT e FROM Equipo e "
        + "WHERE LOWER(e.laboratorio.nombre) "
        + "LIKE LOWER(CONCAT('%', :nombreLaboratorio, '%'))")
    public List<Equipo> buscarPorLaboratorio(
        @Param("nombreLaboratorio") String nombreLaboratorio);
}