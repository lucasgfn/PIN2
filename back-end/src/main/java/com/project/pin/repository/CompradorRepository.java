package com.project.pin.repository;

import com.project.pin.entity.Comprador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompradorRepository extends JpaRepository<Comprador, Long> {
    Optional<Comprador> findByCPFOrUsername(String cpf, String username);
}
