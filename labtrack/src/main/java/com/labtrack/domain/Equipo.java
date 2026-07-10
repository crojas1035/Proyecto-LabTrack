package com.labtrack.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "equipo")
public class Equipo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEquipo;

    @Column(unique = true, nullable = false, length = 20)
    @NotNull
    @Size(min = 1, max = 20)
    private String codigo;

    @Column(nullable = false, length = 60)
    @NotNull
    @Size(min = 1, max = 60)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false, length = 30)
    @NotNull
    private String estado;

    private boolean activo = true;

    @ManyToOne
    @JoinColumn(name = "id_laboratorio", nullable = false)
    @NotNull
    private Laboratorio laboratorio;
}