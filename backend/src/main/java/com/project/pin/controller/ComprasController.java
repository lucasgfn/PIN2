package com.project.pin.controller;

import com.project.pin.entity.Compra;
import com.project.pin.service.CarrinhoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


//------------------------ A MUDAR ------------------------
@RestController
@RequestMapping("/compras")
public class ComprasController {

    @Autowired
    private CarrinhoService carrinhoSession;

    @PostMapping("/finalizar/{usuarioId}")
    public ResponseEntity<Compra> finalizarCompra(@PathVariable Long usuarioId) {
        Compra compra = carrinhoSession.finalizarCompra(usuarioId);
        return ResponseEntity.ok(compra);
    }
}
