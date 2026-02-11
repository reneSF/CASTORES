package com.castores.inventario.service;

import com.castores.inventario.model.Producto;
import java.util.List;

public interface InventarioService {

    // Ver inventario (Administrador y Almacenista)
    List<Producto> listarProductos();

    // Obtener producto por ID (uso interno)
    Producto obtenerProductoPorId(Long idProducto);

    // Crear producto (Administrador)
    void guardarProducto(Producto producto);

    // Aumentar stock (Administrador)
    void aumentarInventario(Long idProducto, int cantidad);

    // Dar de baja producto (Administrador)
    void bajaProducto(Long idProducto);

    // Reactivar producto (Administrador)
    void reactivarProducto(Long idProducto);
}