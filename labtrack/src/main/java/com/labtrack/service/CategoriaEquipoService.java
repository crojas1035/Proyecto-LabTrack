package com.labtrack.service;

import com.labtrack.domain.CategoriaEquipo;
import com.labtrack.repository.CategoriaEquipoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaEquipoService {

    private final CategoriaEquipoRepository categoriaEquipoRepository;

    public CategoriaEquipoService(CategoriaEquipoRepository categoriaEquipoRepository) {
        this.categoriaEquipoRepository = categoriaEquipoRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoriaEquipo> getCategorias() {
        return categoriaEquipoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<CategoriaEquipo> getCategoria(Integer idCategoria) {
        return categoriaEquipoRepository.findById(idCategoria);
    }

    @Transactional
    public void save(CategoriaEquipo categoria) {
        categoriaEquipoRepository.save(categoria);
    }

    @Transactional
    public void delete(Integer idCategoria) {
        CategoriaEquipo categoria = categoriaEquipoRepository.findById(idCategoria)
                .orElseThrow(() -> new IllegalArgumentException("La categoría no existe"));
        categoria.setActivo(false);
        categoriaEquipoRepository.save(categoria);
    }
}
