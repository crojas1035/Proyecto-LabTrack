package com.labtrack.service;

import com.labtrack.domain.Laboratorio;
import com.labtrack.repository.LaboratorioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LaboratorioService {

    private final LaboratorioRepository laboratorioRepository;

    public LaboratorioService(
            LaboratorioRepository laboratorioRepository) {
        this.laboratorioRepository = laboratorioRepository;
    }

    @Transactional(readOnly = true)
    public List<Laboratorio> getLaboratorios(boolean activos) {
        if (activos) {
            return laboratorioRepository.findByActivoTrue();
        }

        return laboratorioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Laboratorio> getLaboratorio(
            Integer idLaboratorio) {
        return laboratorioRepository.findById(idLaboratorio);
    }

    @Transactional
    public void save(Laboratorio laboratorio) {
        laboratorioRepository.save(laboratorio);
    }

    @Transactional
    public void delete(Integer idLaboratorio) {
        Laboratorio laboratorio = laboratorioRepository
                .findById(idLaboratorio)
                .orElseThrow(() -> new IllegalArgumentException(
                "El laboratorio no existe"));

        laboratorio.setActivo(false);
        laboratorioRepository.save(laboratorio);
    }
}