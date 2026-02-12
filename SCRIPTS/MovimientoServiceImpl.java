package com.castores.inventario.service.impl;

import com.castores.inventario.model.Movimiento;
import com.castores.inventario.model.TipoMovimiento;
import com.castores.inventario.repository.MovimientoRepository;
import com.castores.inventario.service.MovimientoService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepository movimientoRepository;

    public MovimientoServiceImpl(MovimientoRepository movimientoRepository) {
        this.movimientoRepository = movimientoRepository;
    }

    @Override
    public Movimiento registrarMovimiento(Movimiento movimiento) {
        movimiento.setFecha(LocalDateTime.now());
        return movimientoRepository.save(movimiento);
    }

    @Override
    public List<Movimiento> obtenerTodos() {
        return movimientoRepository.findAll();
    }

    @Override
    public List<Movimiento> obtenerPorTipo(TipoMovimiento tipoMovimiento) {
        return movimientoRepository.findByTipoMovimiento(tipoMovimiento);
    }
}