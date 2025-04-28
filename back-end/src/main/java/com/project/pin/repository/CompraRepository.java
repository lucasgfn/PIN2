package com.project.pin.repository;

import com.project.pin.entity.Compra;
import com.project.pin.entity.Comprador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompraRepository extends JpaRepository<Compra, Long> {

}
