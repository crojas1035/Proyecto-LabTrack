package com.labtrack.repository;

import com.labtrack.domain.Solicitud;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudRepository
        extends JpaRepository<Solicitud, Integer> {

    public List<Solicitud> findByEstado(String estado);
}