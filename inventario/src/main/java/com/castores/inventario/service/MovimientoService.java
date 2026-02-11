package com.castores.inventario.service;

import com.castores.inventario.model.Movimiento;
import com.castores.inventario.model.TipoMovimiento;

import java.util.List;

public interface MovimientoService {

    Movimiento registrarMovimiento(Movimiento movimiento);

    List<Movimiento> obtenerTodos();

    List<Movimiento> obtenerPorTipo(TipoMovimiento tipoMovimiento);
}