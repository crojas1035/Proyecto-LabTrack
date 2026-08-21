package com.labtrack.controller;

import com.labtrack.domain.CategoriaEquipo;
import com.labtrack.service.CategoriaEquipoService;
import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categoria")
public class CategoriaEquipoController {

    private final CategoriaEquipoService categoriaService;

    public CategoriaEquipoController(CategoriaEquipoService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        var categorias = categoriaService.getCategorias();
        model.addAttribute("categorias", categorias);
        model.addAttribute("totalCategorias", categorias.size());
        return "/categoria/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        CategoriaEquipo categoria = new CategoriaEquipo();
        categoria.setActivo(true);
        model.addAttribute("categoria", categoria);
        return "/categoria/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid CategoriaEquipo categoria, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "/categoria/formulario";
        }
        categoriaService.save(categoria);
        redirectAttributes.addFlashAttribute("todoOk", "Categoría guardada correctamente");
        return "redirect:/categoria/listado";
    }

    @GetMapping("/modificar/{idCategoria}")
    public String modificar(@PathVariable Integer idCategoria, Model model, RedirectAttributes redirectAttributes) {
        Optional<CategoriaEquipo> categoriaOpt = categoriaService.getCategoria(idCategoria);
        if (categoriaOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "La categoría solicitada no existe");
            return "redirect:/categoria/listado";
        }
        model.addAttribute("categoria", categoriaOpt.get());
        return "/categoria/formulario";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer idCategoria, RedirectAttributes redirectAttributes) {
        try {
            categoriaService.delete(idCategoria);
            redirectAttributes.addFlashAttribute("todoOk", "Categoría desactivada correctamente");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/categoria/listado";
    }
}
