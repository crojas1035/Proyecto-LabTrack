package com.labtrack.controller;

import com.labtrack.domain.Laboratorio;
import com.labtrack.domain.Solicitud;
import com.labtrack.domain.Usuario;
import com.labtrack.service.LaboratorioService;
import com.labtrack.service.SolicitudService;
import com.labtrack.service.UsuarioService;
import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/solicitud")
public class SolicitudController {

    private final SolicitudService solicitudService;
    private final UsuarioService usuarioService;
    private final LaboratorioService laboratorioService;

    public SolicitudController(
            SolicitudService solicitudService,
            UsuarioService usuarioService,
            LaboratorioService laboratorioService) {

        this.solicitudService = solicitudService;
        this.usuarioService = usuarioService;
        this.laboratorioService = laboratorioService;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        var solicitudes = solicitudService.getSolicitudes();

        model.addAttribute("solicitudes", solicitudes);
        model.addAttribute(
                "totalSolicitudes",
                solicitudes.size());

        return "/solicitud/listado";
    }

    @GetMapping("/nueva")
    public String nueva(Model model) {
        Solicitud solicitud = new Solicitud();

        solicitud.setEstado("Pendiente");
        solicitud.setUsuario(new Usuario());
        solicitud.setLaboratorio(new Laboratorio());

        cargarListas(model);
        model.addAttribute("solicitud", solicitud);

        return "/solicitud/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(
            @Valid Solicitud solicitud,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            cargarListas(model);
            return "/solicitud/formulario";
        }

        if (solicitud.getUsuario() == null
                || solicitud.getUsuario().getIdUsuario() == null
                || solicitud.getLaboratorio() == null
                || solicitud.getLaboratorio()
                        .getIdLaboratorio() == null) {

            model.addAttribute(
                    "error",
                    "Debe seleccionar usuario y laboratorio");

            cargarListas(model);
            return "/solicitud/formulario";
        }

        Optional<Usuario> usuarioOpt
                = usuarioService.getUsuario(
                        solicitud.getUsuario().getIdUsuario());

        Optional<Laboratorio> laboratorioOpt
                = laboratorioService.getLaboratorio(
                        solicitud.getLaboratorio()
                                .getIdLaboratorio());

        if (usuarioOpt.isEmpty()
                || laboratorioOpt.isEmpty()) {

            model.addAttribute(
                    "error",
                    "El usuario o laboratorio no existe");

            cargarListas(model);
            return "/solicitud/formulario";
        }

        solicitud.setUsuario(usuarioOpt.get());
        solicitud.setLaboratorio(laboratorioOpt.get());
        solicitud.setEstado("Pendiente");

        solicitudService.save(solicitud);

        redirectAttributes.addFlashAttribute(
                "todoOk",
                "Solicitud registrada correctamente");

        return "redirect:/solicitud/listado";
    }

    @PostMapping("/estado")
    public String cambiarEstado(
            @RequestParam Integer idSolicitud,
            @RequestParam String estado,
            RedirectAttributes redirectAttributes) {

        try {
            solicitudService.cambiarEstado(
                    idSolicitud,
                    estado);

            redirectAttributes.addFlashAttribute(
                    "todoOk",
                    "Estado de la solicitud actualizado");

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    e.getMessage());
        }

        return "redirect:/solicitud/listado";
    }

    private void cargarListas(Model model) {
        model.addAttribute(
                "usuarios",
                usuarioService.getUsuarios(true));

        model.addAttribute(
                "laboratorios",
                laboratorioService.getLaboratorios(true));
    }
}