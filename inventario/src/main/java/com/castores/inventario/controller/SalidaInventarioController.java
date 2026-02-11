package com.castores.inventario.controller;

import com.castores.inventario.service.MovimientoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/salidas")
public class SalidaInventarioController {

    private final MovimientoService movimientoService;

    public SalidaInventarioController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    // Sacar producto del almacén (ALMACENISTA)
    @PostMapping("/registrar")
    public String registrarSalida(@RequestParam Long productoId,
                                  @RequestParam int cantidad) {
        movimientoService.registrarSalida(productoId, cantidad);
        return "redirect:/inventario";
    }
}