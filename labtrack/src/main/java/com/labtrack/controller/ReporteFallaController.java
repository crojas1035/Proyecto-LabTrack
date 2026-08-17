package com.labtrack.controller;

import com.labtrack.domain.ReporteFalla;
import com.labtrack.service.EquipoService;
import com.labtrack.service.ReporteFallaService;
import com.labtrack.service.UsuarioService;
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
@RequestMapping("/reporte")
public class ReporteFallaController {

    private final ReporteFallaService reporteService;
    private final EquipoService equipoService;
    private final UsuarioService usuarioService;

    public ReporteFallaController(ReporteFallaService reporteService, EquipoService equipoService, UsuarioService usuarioService) {
        this.reporteService = reporteService;
        this.equipoService = equipoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        var reportes = reporteService.getReportes();
        model.addAttribute("reportes", reportes);
        model.addAttribute("totalReportes", reportes.size());
        return "/reporte/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        ReporteFalla reporte = new ReporteFalla();
        reporte.setEstado("Pendiente");
        model.addAttribute("reporte", reporte);
        model.addAttribute("equipos", equipoService.getEquipos(true));
        model.addAttribute("usuarios", usuarioService.getUsuarios(true));
        return "/reporte/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid ReporteFalla reporte, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("equipos", equipoService.getEquipos(true));
            model.addAttribute("usuarios", usuarioService.getUsuarios(true));
            return "/reporte/formulario";
        }
        reporteService.save(reporte);
        redirectAttributes.addFlashAttribute("todoOk", "Failure report saved successfully");
        return "redirect:/reporte/listado";
    }

    @GetMapping("/modificar/{idReporte}")
    public String modificar(@PathVariable Integer idReporte, Model model, RedirectAttributes redirectAttributes) {
        Optional<ReporteFalla> reporteOpt = reporteService.getReporte(idReporte);
        if (reporteOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "The requested report does not exist");
            return "redirect:/reporte/listado";
        }
        model.addAttribute("reporte", reporteOpt.get());
        model.addAttribute("equipos", equipoService.getEquipos(true));
        model.addAttribute("usuarios", usuarioService.getUsuarios(true));
        return "/reporte/formulario";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer idReporte, RedirectAttributes redirectAttributes) {
        reporteService.delete(idReporte);
        redirectAttributes.addFlashAttribute("todoOk", "Report deleted successfully");
        return "redirect:/reporte/listado";
    }
}
