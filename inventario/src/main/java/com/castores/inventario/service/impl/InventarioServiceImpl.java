package com.castores.inventario.service.impl;

import com.castores.inventario.model.Producto;
import com.castores.inventario.repository.ProductoRepository;
import com.castores.inventario.service.InventarioService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventarioServiceImpl implements InventarioService {

    private final ProductoRepository productoRepository;

    public InventarioServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    // Correción: El método guardarProducto ahora establece el estatus del producto a true por defecto
    @Override
    public void guardarProducto(Producto producto) {
        producto.setEstatus(true);
        productoRepository.save(producto);
    }

    @Override
    public Producto obtenerProductoPorId(Long idProducto) {

        if (idProducto == null) {
            throw new IllegalArgumentException("El id del producto no puede ser null");
        }

        return productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    @Override
    public void aumentarInventario(Long idProducto, int cantidad) {

        if (idProducto == null) {
            throw new IllegalArgumentException("El id del producto no puede ser null");
        }

        Producto producto = obtenerProductoPorId(idProducto);
        producto.setCantidad(producto.getCantidad() + cantidad);
        productoRepository.save(producto);
    }

    @Override
    public void bajaProducto(Long idProducto) {

        if (idProducto == null) {
            throw new IllegalArgumentException("El id del producto no puede ser null");
        }

        Producto producto = obtenerProductoPorId(idProducto);
        producto.setEstatus(false);
        productoRepository.save(producto);
    }
    @Override
    public void reactivarProducto(Long idProducto) {

        if (idProducto == null) {
            throw new IllegalArgumentException("El id del producto no puede ser null");
        }

        Producto producto = obtenerProductoPorId(idProducto);
        producto.setEstatus(true);
        productoRepository.save(producto);
    }

}