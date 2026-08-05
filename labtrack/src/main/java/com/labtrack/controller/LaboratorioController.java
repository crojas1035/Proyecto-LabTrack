package com.labtrack.controller;

import com.labtrack.domain.Laboratorio;
import com.labtrack.service.LaboratorioService;
import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    public LaboratorioController(
            LaboratorioService laboratorioService) {
        this.laboratorioService = laboratorioService;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        var laboratorios = laboratorioService
                .getLaboratorios(false);

        model.addAttribute("laboratorios", laboratorios);
        model.addAttribute("totalLaboratorios",
                laboratorios.size());

        return "/laboratorio/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setEstado("Disponible");
        laboratorio.setActivo(true);

        model.addAttribute("laboratorio", laboratorio);

        return "/laboratorio/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(
            @Valid Laboratorio laboratorio,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "/laboratorio/formulario";
        }

        laboratorioService.save(laboratorio);

        redirectAttributes.addFlashAttribute(
                "todoOk",
                "Laboratorio guardado correctamente");

        return "redirect:/laboratorio/listado";
    }

    @GetMapping("/modificar/{idLaboratorio}")
    public String modificar(
            @PathVariable Integer idLaboratorio,
            Model model,
            RedirectAttributes redirectAttributes) {

        Optional<Laboratorio> laboratorioOpt
                = laboratorioService
                        .getLaboratorio(idLaboratorio);

        if (laboratorioOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "El laboratorio solicitado no existe");

            return "redirect:/laboratorio/listado";
        }

        model.addAttribute(
                "laboratorio",
                laboratorioOpt.get());

        return "/laboratorio/formulario";
    }

    @PostMapping("/eliminar")
    public String eliminar(
            @RequestParam Integer idLaboratorio,
            RedirectAttributes redirectAttributes) {

        try {
            laboratorioService.delete(idLaboratorio);

            redirectAttributes.addFlashAttribute(
                    "todoOk",
                    "Laboratorio desactivado correctamente");

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    e.getMessage());
        }

        return "redirect:/laboratorio/listado";
        
        
    }
    
    @GetMapping("/buscarPorEstado")
public String buscarPorEstado(
        @RequestParam String estado,
        Model model) {

    var laboratorios = laboratorioService
            .buscarPorEstado(estado);

    model.addAttribute("laboratorios", laboratorios);
    model.addAttribute("totalLaboratorios",
            laboratorios.size());

    return "/laboratorio/listado";
    }
}