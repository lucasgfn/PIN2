package com.project.pin.controller;

import com.project.pin.dto.Comprador.CompradorRequestDTO;
import com.project.pin.dto.Comprador.CompradorResponseDTO;
import com.project.pin.entity.Comprador;
import com.project.pin.service.CompradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compradores")
public class CompradorController {

    @Autowired
    private CompradorService compradorService;

    @PostMapping
    public ResponseEntity<Comprador> cadastrar(@RequestBody Comprador comprador) {
        Comprador criado = compradorService.cadastrarComprador(comprador);
        return new ResponseEntity<>(criado, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        compradorService.deletarComprador(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comprador> atualizar(@PathVariable Long id, @RequestBody CompradorRequestDTO dto) {
        Comprador atualizado = compradorService.updateComprador(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompradorResponseDTO> buscar(@PathVariable Long id) {
        CompradorResponseDTO dto = compradorService.getInfosComprador(id);
        return ResponseEntity.ok(dto);
    }
}
