package com.labtrack.controller;

import com.labtrack.domain.Equipo;
import com.labtrack.service.EquipoService;
import com.labtrack.service.LaboratorioService;
import jakarta.validation.Valid;
import java.util.Locale;
import java.util.Optional;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/equipo")
public class EquipoController {
    
    private final EquipoService equipoService;
    private final LaboratorioService laboratorioService;
    private final MessageSource messageSource;

    public EquipoController(EquipoService equipoService, LaboratorioService laboratorioService, MessageSource messageSource) {
        this.equipoService = equipoService;
        this.laboratorioService = laboratorioService;
        this.messageSource = messageSource;
    }

    // HU-04: Consultar listado de equipos
    @GetMapping("/listado")
    public String listado(Model model) {
        var equipos = equipoService.getEquipos(false);
        model.addAttribute("equipos", equipos);
        model.addAttribute("totalEquipos", equipos.size());
        
        // Pasamos también los laboratorios activos para que aparezcan en los modales/selects de registro
        var laboratorios = laboratorioService.getLaboratorios(true);
        model.addAttribute("laboratorios", laboratorios);
        return "/equipo/listado";
    }

    // HU-03: Guardar nuevo equipo o modificaciones
    @PostMapping("/guardar")
    public String guardar(@Valid Equipo equipo, RedirectAttributes redirectAttributes) {
        equipoService.save(equipo);
        redirectAttributes.addFlashAttribute("todoOk", messageSource.getMessage("mensaje.actualizado", null, Locale.getDefault()));
        return "redirect:/equipo/listado";
    }

    // Eliminar un equipo de inventario
    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer idEquipo, RedirectAttributes redirectAttributes) {
        String titulo = "todoOk";
        String detalle = "mensaje.eliminado";
        try {
            equipoService.delete(idEquipo);
        } catch (IllegalArgumentException e) {
            titulo = "error";
            detalle = "equipo.error01"; // El equipo no existe
        } catch (IllegalStateException e) {
            titulo = "error";
            detalle = "equipo.error02"; // Restricción transaccional
        } catch (Exception e) {
            titulo = "error";
            detalle = "equipo.error03";
        }
        redirectAttributes.addFlashAttribute(titulo, messageSource.getMessage(detalle, null, Locale.getDefault()));
        return "redirect:/equipo/listado";
    }

    // Abrir formulario para modificar equipo
    @GetMapping("/modificar/{idEquipo}")
    public String modificar(@PathVariable("idEquipo") Integer idEquipo, Model model, RedirectAttributes redirectAttributes) {
        Optional<Equipo> equipoOpt = equipoService.getEquipo(idEquipo);
        if (equipoOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", messageSource.getMessage("equipo.error01", null, Locale.getDefault()));
            return "redirect:/equipo/listado";
        }
        model.addAttribute("equipo", equipoOpt.get());
        
        // Cargamos los laboratorios disponibles para poder cambiarlo de aula si es necesario
        var laboratorios = laboratorioService.getLaboratorios(true);
        model.addAttribute("laboratorios", laboratorios);
        return "/equipo/modifica";
    }
}
