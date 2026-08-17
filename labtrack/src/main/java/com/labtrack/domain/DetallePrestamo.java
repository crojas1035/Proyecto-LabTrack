package com.labtrack.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "detalle_prestamo")
public class DetallePrestamo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDetalle;

    @ManyToOne
    @JoinColumn(name = "id_prestamo", nullable = false)
    @NotNull
    @ToString.Exclude
    private Prestamo prestamo;

    @ManyToOne
    @JoinColumn(name = "id_equipo", nullable = false)
    @NotNull
    private Equipo equipo;

    private boolean devuelto = false;
}
