package com.project.pin.controller;

import com.project.pin.entity.Compra;
import com.project.pin.service.ItemCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compras")
public class ComprasController {

    @Autowired
    private ItemCompraService itemCompraService;

    @PostMapping("/finalizar/{usuarioId}")
    public ResponseEntity<Compra> finalizarCompra(@PathVariable Long usuarioId) {
        Compra compra = itemCompraService.finalizarCompra(usuarioId);
        return ResponseEntity.ok(compra);
    }
}
