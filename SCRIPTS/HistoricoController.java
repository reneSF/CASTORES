package com.castores.inventario.controller;

import com.castores.inventario.service.MovimientoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/historico")
public class HistoricoController {

    private final MovimientoService movimientoService;

    public HistoricoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @GetMapping
    public String verHistorico(Model model) {
        model.addAttribute("movimientos", movimientoService.obtenerTodos());
        return "historico/listar";
    }
}