package com.labtrack.controller;

import com.labtrack.domain.Prestamo;
import com.labtrack.service.EquipoService;
import com.labtrack.service.PrestamoService;
import com.labtrack.service.UsuarioService;
import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/prestamo")
public class PrestamoController {

    private final PrestamoService prestamoService;
    private final UsuarioService usuarioService;
    private final EquipoService equipoService;

    public PrestamoController(PrestamoService prestamoService, UsuarioService usuarioService, EquipoService equipoService) {
        this.prestamoService = prestamoService;
        this.usuarioService = usuarioService;
        this.equipoService = equipoService;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        var prestamos = prestamoService.getPrestamos();
        model.addAttribute("prestamos", prestamos);
        model.addAttribute("totalPrestamos", prestamos.size());
        return "/prestamo/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        Prestamo prestamo = new Prestamo();
        prestamo.setEstado("Vigente");
        prestamo.setActivo(true);
        model.addAttribute("prestamo", prestamo);
        model.addAttribute("usuarios", usuarioService.getUsuarios(true));
        model.addAttribute("equipos", equipoService.getEquipos(true));
        return "/prestamo/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Prestamo prestamo, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("usuarios", usuarioService.getUsuarios(true));
            model.addAttribute("equipos", equipoService.getEquipos(true));
            return "/prestamo/formulario";
        }
        prestamoService.save(prestamo);
        redirectAttributes.addFlashAttribute("todoOk", "Préstamo guardado correctamente");
        return "redirect:/prestamo/listado";
    }

    @GetMapping("/modificar/{idPrestamo}")
    public String modificar(@PathVariable Integer idPrestamo, Model model, RedirectAttributes redirectAttributes) {
        Optional<Prestamo> prestamoOpt = prestamoService.getPrestamo(idPrestamo);
        if (prestamoOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "El préstamo solicitado no existe");
            return "redirect:/prestamo/listado";
        }
        model.addAttribute("prestamo", prestamoOpt.get());
        model.addAttribute("usuarios", usuarioService.getUsuarios(true));
        model.addAttribute("equipos", equipoService.getEquipos(true));
        return "/prestamo/formulario";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer idPrestamo, RedirectAttributes redirectAttributes) {
        try {
            prestamoService.delete(idPrestamo);
            redirectAttributes.addFlashAttribute("todoOk", "Préstamo desactivado correctamente");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/prestamo/listado";
    }
}
