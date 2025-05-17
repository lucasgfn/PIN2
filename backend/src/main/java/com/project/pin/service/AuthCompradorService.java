package com.project.pin.service;

import java.util.Optional;

import com.project.pin.entity.Comprador;
import com.project.pin.repository.CompradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
;

@Service
public class AuthCompradorService {

    @Autowired
    private CompradorRepository compradorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Comprador autenticar(String username, String password) {
        Optional<Comprador> compradorOpt = compradorRepository.findByUsername(username);

        if (compradorOpt.isPresent()) {
            Comprador comprador = compradorOpt.get();
            if (passwordEncoder.matches(password, comprador.getPassword())) {
                //Retorna comprador autenticado
                return comprador;
            }
        }

        return null;
    }
}