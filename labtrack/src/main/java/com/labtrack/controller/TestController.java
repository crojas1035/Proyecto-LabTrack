package com.labtrack.controller;

import com.labtrack.domain.CategoriaEquipo;
import com.labtrack.domain.Equipo;
import com.labtrack.domain.Laboratorio;
import com.labtrack.domain.Prestamo;
import com.labtrack.domain.Usuario;
import com.labtrack.service.CategoriaEquipoService;
import com.labtrack.service.EquipoService;
import com.labtrack.service.LaboratorioService;
import com.labtrack.service.PrestamoService;
import com.labtrack.service.UsuarioService;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {

    private final CategoriaEquipoService categoriaService;
    private final PrestamoService prestamoService;
    private final EquipoService equipoService;
    private final LaboratorioService laboratorioService;
    private final UsuarioService usuarioService;

    public TestController(CategoriaEquipoService categoriaService, PrestamoService prestamoService, EquipoService equipoService, LaboratorioService laboratorioService, UsuarioService usuarioService) {
        this.categoriaService = categoriaService;
        this.prestamoService = prestamoService;
        this.equipoService = equipoService;
        this.laboratorioService = laboratorioService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/generar")
    @ResponseBody
    public String generarDatosPrueba() {
        try {
            // 1. Generar Categorias
            CategoriaEquipo cat1 = new CategoriaEquipo();
            cat1.setNombre("Computadoras");
            cat1.setActivo(true);
            categoriaService.save(cat1);

            CategoriaEquipo cat2 = new CategoriaEquipo();
            cat2.setNombre("Microscopios");
            cat2.setActivo(true);
            categoriaService.save(cat2);

            // 2. Obtener un Usuario y Laboratorio existente
            Usuario usuario = null;
            if (!usuarioService.getUsuarios(true).isEmpty()) {
                usuario = usuarioService.getUsuarios(true).get(0);
            } else {
                usuario = new Usuario();
                usuario.setNombre("Test User");
                usuario.setCorreo("test@test.com");
                usuario.setPassword("12345");
                usuario.setTipoUsuario("Investigador");
                usuario.setActivo(true);
                usuarioService.save(usuario);
            }

            Laboratorio lab = null;
            if (laboratorioService.getLaboratorios(true).isEmpty()) {
                lab = new Laboratorio();
                lab.setCodigo("LAB-01");
                lab.setNombre("Lab Prueba");
                lab.setUbicacion("Edificio A");
                lab.setEstado("Disponible");
                lab.setActivo(true);
                laboratorioService.save(lab);
            } else {
                lab = laboratorioService.getLaboratorios(true).get(0);
            }

            // 3. Generar Equipos
            Equipo eq1 = new Equipo();
            eq1.setCodigo("PC-001");
            eq1.setNombre("Dell Optiplex");
            eq1.setLaboratorio(lab);
            eq1.setCategoria(cat1);
            eq1.setEstado("Disponible");
            eq1.setActivo(true);
            equipoService.save(eq1);

            // 4. Generar Prestamo
            if (usuario != null) {
                Prestamo p = new Prestamo();
                p.setUsuario(usuario);
                p.setFechaPrestamo(new java.sql.Date(System.currentTimeMillis()));
                p.setFechaDevolucionEsperada(Date.valueOf(LocalDate.now().plusDays(5)));
                p.setEstado("Vigente");
                p.setActivo(true);
                prestamoService.save(p);
            }

            return "Data generated successfully. Go to /prestamo/listado or /categoria/listado to view.";
        } catch (Exception e) {
            return "Error generating data: " + e.getMessage();
        }
    }
}
