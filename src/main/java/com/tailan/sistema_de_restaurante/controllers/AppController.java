package com.tailan.sistema_de_restaurante.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class AppController {
    @GetMapping("/")
    public String home() {
        return "index"; // Retorna a página index.html
    }
}
