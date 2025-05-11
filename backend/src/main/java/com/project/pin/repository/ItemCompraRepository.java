package com.project.pin.repository;

import com.project.pin.entity.ItemCompra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemCompraRepository extends JpaRepository<ItemCompra, Long> {
    List<ItemCompra> findByCompraId(Long id);
}
