package com.castores.inventario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.castores.inventario.service.InventarioService;

@Controller
@RequestMapping("/ui")
public class ViewController {

    private final InventarioService inventarioService;

    //  EL CONSTRUCTOR DEBE LLAMARSE IGUAL QUE LA CLASE
    public ViewController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/salida")
    public String salida() {
        return "salida";
    }

    @GetMapping("/historico")
    public String historico() {
        return "historico";
    }
}