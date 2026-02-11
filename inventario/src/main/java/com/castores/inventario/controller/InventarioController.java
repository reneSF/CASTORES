package com.castores.inventario.controller;

import com.castores.inventario.model.Producto;
import com.castores.inventario.service.InventarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/inventario")
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    // ================================
    // VER INVENTARIO (ADMIN y ALMACENISTA)
    // ================================
    @GetMapping
    public String verInventario(Model model) {
        model.addAttribute("productos", inventarioService.listarProductos());
        return "inventario/lista";
    }

    // ================================
    // FORM NUEVO PRODUCTO (SOLO ADMIN)
    // ================================
    @GetMapping("/nuevo")
    public String nuevoProductoForm(Model model) {
        model.addAttribute("producto", new Producto());
        return "inventario/nuevo";
    }

    // ================================
    // GUARDAR PRODUCTO (SOLO ADMIN)
    // ================================
    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute Producto producto) {
        inventarioService.guardarProducto(producto);
        return "redirect:/inventario";
    }

    // ================================
    // AUMENTAR INVENTARIO (SOLO ADMIN)
    // ================================
    @PostMapping("/entrada/{id}")
    public String aumentarInventario(
            @PathVariable Long id,
            @RequestParam int cantidad
    ) {
        inventarioService.aumentarInventario(id, cantidad);
        return "redirect:/inventario";
    }

    // ================================
    // DAR DE BAJA PRODUCTO (SOLO ADMIN)
    // ================================
    @PostMapping("/baja/{id}")
    public String darDeBaja(@PathVariable Long id) {
        inventarioService.bajaProducto(id);
        return "redirect:/inventario";
    }

    // ================================
    // REACTIVAR PRODUCTO (SOLO ADMIN)
    // ================================
    @PostMapping("/reactivar/{id}")
    public String reactivarProducto(@PathVariable Long id) {
        inventarioService.reactivarProducto(id);
        return "redirect:/inventario";
    }
}