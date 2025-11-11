package com.microservice.estudiantes.demospringsecurityexample01.controllers;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


@RestController
@RequestMapping("/customers/api/v1")
public class CustomerController {

    private static final Logger logger = Logger.getLogger(CustomerController.class.getName());
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private SessionRegistry sessionRegistry;

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcome() {
        logger.warning("Se accedió al endpoint seguro /welcome");
        return "Bienvenido al API de Clientes con Seguridad Spring!";
    }

    @RequestMapping(value = "/welcome-not-secured", method = RequestMethod.GET)
    public String welcomeNotSecured() {
        logger.warning("Se accedió al endpoint no seguro /welcome-not-secured");
        return "Bienvenido al API de Clientes sin Seguridad Spring!";
    }

    @RequestMapping(value = "/inicio", method = RequestMethod.GET)
    public String index() {
        logger.warning("Se accedió al endpoint no seguro /inicio");
        return "<h1>Bienvenido</h1>";
    }

    @RequestMapping(path = "/detalles-session", method = RequestMethod.GET)
    public ResponseEntity<?> getDetallesSession() {
        logger.warning("Se accedió al endpoint seguro /detalles-session");
        String sessionId = "";
        User userObject = null;
        List<Object> sessions = sessionRegistry.getAllPrincipals();
        for (Object session : sessions) {
            if (session instanceof User) {
                userObject = (User) session;
            }
            List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(session, false);
            for (SessionInformation sessionInformation : sessionInformations) {
                sessionId = sessionInformation.getSessionId();
            }
        }
        Map<String, Object> response = new HashMap<>();
        response.put("response", "Hola Mundo desde detalles-session");
        response.put("sessionId", sessionId);
        response.put("userObject", userObject);
        log.info(response.toString());
        return ResponseEntity.ok(response);
    }


}
