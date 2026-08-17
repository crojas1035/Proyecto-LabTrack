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
import java.sql.Date;
import lombok.Data;

@Data
@Entity
@Table(name = "reporte_falla")
public class ReporteFalla implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReporte;

    @ManyToOne
    @JoinColumn(name = "id_equipo", nullable = false)
    @NotNull
    private Equipo equipo;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @NotNull
    private Usuario usuario;

    @Column(name = "descripcion_falla", nullable = false)
    @NotNull
    @Size(min = 1, max = 1000)
    private String descripcionFalla;

    @Column(name = "fecha_reporte")
    private Date fechaReporte;

    @Column(nullable = false, length = 20)
    @NotNull
    private String estado;
}
