package com.castores.inventario.controller;

import com.castores.inventario.model.Movimiento;
import com.castores.inventario.model.TipoMovimiento;
import com.castores.inventario.service.MovimientoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/salidas")
public class SalidaInventarioController {

    private final MovimientoService movimientoService;

    public SalidaInventarioController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @PostMapping
    public Movimiento registrarSalida(@RequestBody Movimiento movimiento) {
        movimiento.setTipoMovimiento(TipoMovimiento.SALIDA);
        return movimientoService.registrarMovimiento(movimiento);
    }
}