package com.castores.inventario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ui")
public class ViewController {

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