package com.labtrack.repository;

import com.labtrack.domain.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository
        extends JpaRepository<Usuario, Integer> {

    public List<Usuario> findByActivoTrue();

    public Optional<Usuario> findByCorreoAndActivoTrue(String correo);
}