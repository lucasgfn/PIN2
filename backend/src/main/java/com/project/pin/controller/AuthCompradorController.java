package com.project.pin.controller;

import com.project.pin.dto.Comprador.AuthCompradorDTO;
import com.project.pin.entity.Comprador;
import com.project.pin.service.AuthComprador;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/")
public class AuthCompradorController {

    private static final Logger logger = LoggerFactory.getLogger(AuthCompradorController.class);

    @Autowired
    private AuthComprador authComprador;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody @Validated AuthCompradorDTO authCompradorDTO, HttpSession session) {
        logger.info("Tentando autenticar o paciente com login: {}", authCompradorDTO.username());

        Comprador comprador = authComprador.autenticar(authCompradorDTO.username(), authCompradorDTO.password());

        if (comprador != null) {
            session.setAttribute("login", comprador.getUsername());
            logger.info("Login bem-sucedido para o usuário: {}", comprador.getUsername());
            return ResponseEntity.ok("Login bem-sucedido!");
        } else {
            logger.warn("Falha na autenticação para o login: {}", authCompradorDTO.username());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas!");
        }
    }

    @PostMapping("logoff")
    public String logoff(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
            logger.info("Logoff realizado com sucesso.");
            return "Logoff realizado com sucesso!";
        }

        logger.warn("Tentativa de logoff sem sessão ativa.");
        return "Nenhuma sessão ativa para finalizar.";
    }
}
