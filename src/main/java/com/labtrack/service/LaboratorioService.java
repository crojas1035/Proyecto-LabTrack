package com.labtrack.service;

import com.labtrack.domain.Laboratorio;
import com.labtrack.repository.LaboratorioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LaboratorioService {

    // Enlace al repositorio por constructor (Estilo de tu clase)
    private final LaboratorioRepository laboratorioRepository;

    public LaboratorioService(LaboratorioRepository laboratorioRepository) {
        this.laboratorioRepository = laboratorioRepository;
    }

    // HU-01 & HU-02: Recuperar laboratorios (activos o todos)
    @Transactional(readOnly = true)
    public List<Laboratorio> getLaboratorios(boolean activo) {
        if (activo) {
            return laboratorioRepository.findByActivoTrue();
        }
        return laboratorioRepository.findAll();
    }
    
    // Recuperar un laboratorio por ID usando Optional
    @Transactional(readOnly = true)
    public Optional<Laboratorio> getLaboratorio(Integer idLaboratorio) {
        return laboratorioRepository.findById(idLaboratorio);
    }
    
    // HU-01 & HU-02: Guardar o actualizar laboratorio sin Firebase
    @Transactional
    public void save(Laboratorio laboratorio) {
        laboratorioRepository.save(laboratorio);
    }
    
    // Eliminar laboratorio con control de integridad (por si tiene equipos asignados)
    @Transactional
    public void delete(Integer idLaboratorio) {
        if (!laboratorioRepository.existsById(idLaboratorio)) {
            throw new IllegalArgumentException("El laboratorio con ID " + idLaboratorio + " no existe");
        }
        try {
            laboratorioRepository.deleteById(idLaboratorio);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("No se puede eliminar el laboratorio, tiene equipos o solicitudes asociadas");
        }
    }
}