package com.labtrack.service;

import com.labtrack.domain.ReporteFalla;
import com.labtrack.repository.ReporteFallaRepository;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReporteFallaService {

    private final ReporteFallaRepository reporteRepository;

    public ReporteFallaService(ReporteFallaRepository reporteRepository) {
        this.reporteRepository = reporteRepository;
    }

    @Transactional(readOnly = true)
    public List<ReporteFalla> getReportes() {
        return reporteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<ReporteFalla> getReporte(Integer idReporte) {
        return reporteRepository.findById(idReporte);
    }

    @Transactional
    public void save(ReporteFalla reporte) {
        if (reporte.getFechaReporte() == null) {
            reporte.setFechaReporte(new Date(System.currentTimeMillis()));
        }
        reporteRepository.save(reporte);
    }

    @Transactional
    public void delete(Integer idReporte) {
        reporteRepository.deleteById(idReporte);
    }
}
