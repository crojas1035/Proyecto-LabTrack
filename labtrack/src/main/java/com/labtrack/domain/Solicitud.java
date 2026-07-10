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
import java.time.LocalDate;
import lombok.Data;

@Data
@Entity
@Table(name = "solicitud")
public class Solicitud implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSolicitud;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @NotNull
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_laboratorio", nullable = false)
    @NotNull
    private Laboratorio laboratorio;

    @Column(name = "fecha_uso", nullable = false)
    @NotNull
    private LocalDate fechaUso;

    @Column(nullable = false, length = 255)
    @NotNull
    @Size(min = 1, max = 255)
    private String motivo;

    @Column(nullable = false, length = 20)
    @NotNull
    private String estado;
}