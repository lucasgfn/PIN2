package com.project.pin.repository;

import com.project.pin.entity.Comprador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompradorRepository extends JpaRepository<Comprador, Long> {
    Optional<Comprador> findByCpfOrUsername(String cpf, String username);
    Optional<Comprador> findByUsername(String username);

}
