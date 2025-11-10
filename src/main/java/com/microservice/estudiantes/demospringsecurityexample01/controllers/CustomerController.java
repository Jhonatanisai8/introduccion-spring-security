package com.microservice.estudiantes.demospringsecurityexample01.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;


@RestController
@RequestMapping("/customers/api/v1")
public class CustomerController {

    private static final Logger logger = Logger.getLogger(CustomerController.class.getName());

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcome() {
        logger.info("Se accedió al endpoint seguro /welcome");
        return "Bienvenido al API de Clientes con Seguridad Spring!";
    }

    @RequestMapping(value = "/welcome-not-secured", method = RequestMethod.GET)
    public String welcomeNotSecured() {
        logger.warning("Se accedió al endpoint no seguro /welcome-not-secured");
        return "Bienvenido al API de Clientes sin Seguridad Spring!";
    }


}
