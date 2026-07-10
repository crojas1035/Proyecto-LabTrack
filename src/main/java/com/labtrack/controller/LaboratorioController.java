package com.labtrack.controller;

import com.labtrack.domain.Laboratorio;
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
@RequestMapping("/laboratorio")
public class LaboratorioController {
    
    private final LaboratorioService laboratorioService;
    private final MessageSource messageSource;

    public LaboratorioController(LaboratorioService laboratorioService, MessageSource messageSource) {
        this.laboratorioService = laboratorioService;
        this.messageSource = messageSource;
    }

    // HU-01 & HU-02: Listado de laboratorios
    @GetMapping("/listado")
    public String listado(Model model) {
        var laboratorios = laboratorioService.getLaboratorios(false);
        model.addAttribute("laboratorios", laboratorios);
        model.addAttribute("totalLaboratorios", laboratorios.size());
        return "laboratorio/listado";
    }

    // Guardar nuevo o cambios (HU-01 y HU-02)
    @PostMapping("/guardar")
    public String guardar(@Valid Laboratorio laboratorio, RedirectAttributes redirectAttributes) {
        laboratorioService.save(laboratorio);
        redirectAttributes.addFlashAttribute("todoOk", messageSource.getMessage("mensaje.actualizado", null, Locale.getDefault()));
        return "redirect:/laboratorios/listado";
    }

    // Eliminar laboratorio con los mismos catch de tu clase
    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer idLaboratorio, RedirectAttributes redirectAttributes) {
        String titulo = "todoOk";
        String detalle = "mensaje.eliminado";
        try {
            laboratorioService.delete(idLaboratorio);
        } catch (IllegalArgumentException e) {
            titulo = "error";
            detalle = "laboratorio.error01"; // El laboratorio no existe
        } catch (IllegalStateException e) {
            titulo = "error";
            detalle = "laboratorio.error02"; // Tiene equipos asignados
        } catch (Exception e) {
            titulo = "error";
            detalle = "laboratorio.error03"; // Error inesperado
        }
        redirectAttributes.addFlashAttribute(titulo, messageSource.getMessage(detalle, null, Locale.getDefault()));
        return "redirect:/laboratorios/listado";
    }

    // HU-02: Abrir vista de modificación
    @GetMapping("/modificar/{idLaboratorio}")
    public String modificar(@PathVariable("idLaboratorio") Integer idLaboratorio, Model model, RedirectAttributes redirectAttributes) {
        Optional<Laboratorio> laboratorioOpt = laboratorioService.getLaboratorio(idLaboratorio);
        if (laboratorioOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", messageSource.getMessage("laboratorio.error01", null, Locale.getDefault()));
            return "redirect:/laboratorios/listado";
        }
        model.addAttribute("laboratorio", laboratorioOpt.get());
        return "laboratorio/modifica";
    }
}
