package com.labtrack.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    @Column(nullable = false, length = 60)
    private String nombre;

    @Column(unique = true, nullable = false, length = 100)
    private String correo;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(name = "tipo_usuario", nullable = false, length = 20)
    private String tipoUsuario;

    private boolean activo;
}