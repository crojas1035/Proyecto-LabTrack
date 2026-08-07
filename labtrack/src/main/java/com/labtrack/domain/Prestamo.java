package com.labtrack.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name = "prestamo")
public class Prestamo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPrestamo;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @NotNull
    private Usuario usuario;

    @Column(insertable = false, updatable = false)
    private Timestamp fechaPrestamo;

    @Column(nullable = false)
    @NotNull
    private Date fechaDevolucionEsperada;

    @Column(nullable = false, length = 30)
    @NotNull
    private String estado;

    private boolean activo = true;

    @OneToMany(mappedBy = "prestamo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePrestamo> detalles;
}
