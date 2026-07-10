package com.labtrack.service;

import com.labtrack.domain.Solicitud;
import com.labtrack.repository.SolicitudRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SolicitudService {

    private final SolicitudRepository solicitudRepository;

    public SolicitudService(
            SolicitudRepository solicitudRepository) {
        this.solicitudRepository = solicitudRepository;
    }

    @Transactional(readOnly = true)
    public List<Solicitud> getSolicitudes() {
        return solicitudRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Solicitud> getSolicitud(Integer idSolicitud) {
        return solicitudRepository.findById(idSolicitud);
    }

    @Transactional
    public void save(Solicitud solicitud) {
        solicitudRepository.save(solicitud);
    }

    @Transactional
    public void cambiarEstado(
            Integer idSolicitud,
            String nuevoEstado) {

        if (!nuevoEstado.equals("Aprobada")
                && !nuevoEstado.equals("Rechazada")) {

            throw new IllegalArgumentException(
                    "El estado solicitado no es válido");
        }

        Solicitud solicitud = solicitudRepository
                .findById(idSolicitud)
                .orElseThrow(() -> new IllegalArgumentException(
                "La solicitud no existe"));

        solicitud.setEstado(nuevoEstado);
        solicitudRepository.save(solicitud);
    }
}