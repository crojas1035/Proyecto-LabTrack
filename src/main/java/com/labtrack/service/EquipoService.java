package com.labtrack.service;

import com.labtrack.domain.Equipo;
import com.labtrack.repository.EquipoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EquipoService {

    private final EquipoRepository equipoRepository;

    public EquipoService(EquipoRepository equipoRepository) {
        this.equipoRepository = equipoRepository;
    }

    // HU-04: Recuperar todos los equipos (activos o todos)
    @Transactional(readOnly = true)
    public List<Equipo> getEquipos(boolean activo) {
        if (activo) {
            return equipoRepository.findByActivoTrue();
        }
        return equipoRepository.findAll();
    }
    
    // Recuperar un equipo por ID
    @Transactional(readOnly = true)
    public Optional<Equipo> getEquipo(Integer idEquipo) {
        return equipoRepository.findById(idEquipo);
    }
    
    // HU-03: Guardar o actualizar equipo
    @Transactional
    public void save(Equipo equipo) {
        equipoRepository.save(equipo);
    }
    
    // Eliminar un equipo
    @Transactional
    public void delete(Integer idEquipo) {
        if (!equipoRepository.existsById(idEquipo)) {
            throw new IllegalArgumentException("El equipo con ID " + idEquipo + " no existe");
        }
        try {
            equipoRepository.deleteById(idEquipo);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("No se puede eliminar el equipo debido a restricciones de integridad");
        }
    }
}