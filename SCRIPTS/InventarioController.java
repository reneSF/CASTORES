package com.castores.inventario.controller;

import com.castores.inventario.model.Producto;
import com.castores.inventario.service.InventarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ui/inventario")
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    // LISTAR PRODUCTOS
    @GetMapping
    public String verInventario(Model model) {
        model.addAttribute("productos", inventarioService.listarProductos());
        return "inventario/lista";
    }

    // FORMULARIO NUEVO (ADMIN)
    @GetMapping("/nuevo")
    public String nuevoProductoForm(Model model) {
        model.addAttribute("producto", new Producto());
        return "inventario/nuevo";
    }

    // GUARDAR (ADMIN)
    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute Producto producto) {
        // REGLA DE NEGOCIO CASTORES: Si es un producto nuevo, la cantidad DEBE ser 0
        if (producto.getId() == null) {
            producto.setCantidad(0);
        }
        inventarioService.guardarProducto(producto);
        return "redirect:/ui/inventario";
    }

    // BAJA DE PRODUCTO (ADMIN)
    @PostMapping("/baja/{id}")
    public String darDeBaja(@PathVariable Long id) {
        inventarioService.bajaProducto(id);
        return "redirect:/ui/inventario";
    }

    // REACTIVAR PRODUCTO (ADMIN)
    @PostMapping("/reactivar/{id}")
    public String reactivarProducto(@PathVariable Long id) {
        inventarioService.reactivarProducto(id);
        return "redirect:/ui/inventario";
    }

    // Este es el que te está fallando
    @GetMapping("/ui/producto/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        // model.addAttribute("producto", new Producto()); // Si usas Thymeleaf objects
        return "inventario/nuevo"; // Debe coincidir con la carpeta y nombre del archivo
    }
}
