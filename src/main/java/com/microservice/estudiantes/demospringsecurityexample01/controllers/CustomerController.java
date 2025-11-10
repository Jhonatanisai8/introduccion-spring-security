package com.microservice.estudiantes.demospringsecurityexample01.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers/api/v1")
public class CustomerController {

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcome() {
        return "Bienvenido al API de Clientes con Seguridad Spring!";
    }

    @RequestMapping(value = "/welcome-not-secured", method = RequestMethod.GET)
    public String welcomeNotSecured() {
        return "Bienvenido al API de Clientes sin Seguridad Spring!";
    }


}
