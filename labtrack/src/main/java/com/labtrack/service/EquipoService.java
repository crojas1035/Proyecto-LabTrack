package com.labtrack.service;

import com.labtrack.domain.Equipo;
import com.labtrack.repository.EquipoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EquipoService {

    private final EquipoRepository equipoRepository;

    public EquipoService(EquipoRepository equipoRepository) {
        this.equipoRepository = equipoRepository;
    }

    @Transactional(readOnly = true)
    public List<Equipo> getEquipos(boolean activos) {
        if (activos) {
            return equipoRepository.findByActivoTrue();
        }

        return equipoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Equipo> getEquipo(Integer idEquipo) {
        return equipoRepository.findById(idEquipo);
    }

    @Transactional
    public void save(Equipo equipo) {
        equipoRepository.save(equipo);
    }

    @Transactional
    public void delete(Integer idEquipo) {
        Equipo equipo = equipoRepository
                .findById(idEquipo)
                .orElseThrow(() -> new IllegalArgumentException(
                "El equipo no existe"));

        equipo.setActivo(false);
        equipoRepository.save(equipo);
    }
}