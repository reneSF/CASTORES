package com.castores.inventario.repository;

import com.castores.inventario.model.Movimiento;
import com.castores.inventario.model.TipoMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    List<Movimiento> findByTipoMovimiento(TipoMovimiento tipoMovimiento);
}