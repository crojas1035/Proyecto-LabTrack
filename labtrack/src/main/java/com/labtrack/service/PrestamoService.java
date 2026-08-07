package com.labtrack.service;

import com.labtrack.domain.Prestamo;
import com.labtrack.repository.PrestamoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;

    public PrestamoService(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    @Transactional(readOnly = true)
    public List<Prestamo> getPrestamos() {
        return prestamoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Prestamo> getPrestamo(Integer idPrestamo) {
        return prestamoRepository.findById(idPrestamo);
    }

    @Transactional
    public void save(Prestamo prestamo) {
        if (prestamo.getDetalles() != null) {
            prestamo.getDetalles().forEach(detalle -> detalle.setPrestamo(prestamo));
        }
        prestamoRepository.save(prestamo);
    }

    @Transactional
    public void delete(Integer idPrestamo) {
        Prestamo prestamo = prestamoRepository.findById(idPrestamo)
                .orElseThrow(() -> new IllegalArgumentException("El préstamo no existe"));
        prestamo.setActivo(false);
        prestamoRepository.save(prestamo);
    }
}
