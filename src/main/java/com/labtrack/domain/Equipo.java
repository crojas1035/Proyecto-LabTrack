package com.labtrack.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name="equipo")
public class Equipo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEquipo; // Mismo estilo que idProducto
    
    private Integer idLaboratorio; // Clave foránea idéntica al idCategoria de tu ejemplo
    
    @Column(unique = true, nullable = false, length = 50)
    @NotNull
    @Size(max = 50)
    private String nombre; // Equivalente a descripcion
    
    @Column(columnDefinition="TEXT")
    private String detalle; // Para poner estado, marca o componentes del equipo
    
    @Column(unique = true, nullable = false, length = 50)
    @NotNull
    @Size(max = 50)
    private String numeroSerie; 
    
    @Min(value=0, message="Las existencias NO pueden ser negativas")
    private Integer existencias; // Si manejan stock de periféricos (mouses, teclados, etc.)
    
    private boolean activo;
}
