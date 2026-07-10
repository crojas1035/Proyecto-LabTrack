package com.labtrack.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name="laboratorio")
public class Laboratorio implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLaboratorio; // Mismo estilo que idCategoria
    
    @Column(unique = true, nullable = false, length = 50)
    @NotNull
    @Size(max = 50)
    private String nombre; // Equivalente a descripcion
    
    @Column(nullable = false, length = 100)
    @NotNull
    @Size(max = 100)
    private String ubicacion;
    
    private Integer capacidad;
    
    private boolean activo; // Equivalente a activo en Categoria
}
