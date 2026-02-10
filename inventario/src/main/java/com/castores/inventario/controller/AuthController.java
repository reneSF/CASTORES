package com.castores.inventario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @GetMapping("/")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String correo,
                                @RequestParam String password,
                                Model model) {

        // TEMPORAL (luego conectamos BD)
        if (correo.equals("admin@castores.com") && password.equals("1234")) {
            return "redirect:/inventario";
        }

        model.addAttribute("error", "Credenciales incorrectas");
        return "login";
    }
}