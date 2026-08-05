package com.labtrack.controller;

import com.labtrack.domain.Equipo;
import com.labtrack.domain.Laboratorio;
import com.labtrack.service.EquipoService;
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
@RequestMapping("/equipo")
public class EquipoController {

    private final EquipoService equipoService;
    private final LaboratorioService laboratorioService;

    public EquipoController(
            EquipoService equipoService,
            LaboratorioService laboratorioService) {

        this.equipoService = equipoService;
        this.laboratorioService = laboratorioService;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        var equipos = equipoService.getEquipos(false);

        model.addAttribute("equipos", equipos);
        model.addAttribute("totalEquipos", equipos.size());

        return "/equipo/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        Equipo equipo = new Equipo();
        equipo.setEstado("Disponible");
        equipo.setActivo(true);
        equipo.setLaboratorio(new Laboratorio());

        model.addAttribute("equipo", equipo);
        model.addAttribute(
                "laboratorios",
                laboratorioService.getLaboratorios(true));

        return "/equipo/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(
            @Valid Equipo equipo,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (equipo.getLaboratorio() == null
                || equipo.getLaboratorio()
                        .getIdLaboratorio() == null) {

            model.addAttribute(
                    "error",
                    "Debe seleccionar un laboratorio");

            model.addAttribute(
                    "laboratorios",
                    laboratorioService.getLaboratorios(true));

            return "/equipo/formulario";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute(
                    "laboratorios",
                    laboratorioService.getLaboratorios(true));

            return "/equipo/formulario";
        }

        Integer idLaboratorio = equipo
                .getLaboratorio()
                .getIdLaboratorio();

        Optional<Laboratorio> laboratorioOpt
                = laboratorioService
                        .getLaboratorio(idLaboratorio);

        if (laboratorioOpt.isEmpty()) {
            model.addAttribute(
                    "error",
                    "El laboratorio seleccionado no existe");

            model.addAttribute(
                    "laboratorios",
                    laboratorioService.getLaboratorios(true));

            return "/equipo/formulario";
        }

        equipo.setLaboratorio(laboratorioOpt.get());
        equipoService.save(equipo);

        redirectAttributes.addFlashAttribute(
                "todoOk",
                "Equipo guardado correctamente");

        return "redirect:/equipo/listado";
    }

    @GetMapping("/modificar/{idEquipo}")
    public String modificar(
            @PathVariable Integer idEquipo,
            Model model,
            RedirectAttributes redirectAttributes) {

        Optional<Equipo> equipoOpt
                = equipoService.getEquipo(idEquipo);

        if (equipoOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "El equipo solicitado no existe");

            return "redirect:/equipo/listado";
        }

        model.addAttribute("equipo", equipoOpt.get());
        model.addAttribute(
                "laboratorios",
                laboratorioService.getLaboratorios(true));

        return "/equipo/formulario";
    }

    @PostMapping("/eliminar")
    public String eliminar(
            @RequestParam Integer idEquipo,
            RedirectAttributes redirectAttributes) {

        try {
            equipoService.delete(idEquipo);

            redirectAttributes.addFlashAttribute(
                    "todoOk",
                    "Equipo desactivado correctamente");

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    e.getMessage());
        }

        return "redirect:/equipo/listado";
    }
    @GetMapping("/buscarPorLaboratorio")
public String buscarPorLaboratorio(
        @RequestParam String nombreLaboratorio,
        Model model) {

    var equipos = equipoService
            .buscarPorLaboratorio(nombreLaboratorio);

    model.addAttribute("equipos", equipos);
    model.addAttribute("totalEquipos", equipos.size());

    return "/equipo/listado";
    }
}