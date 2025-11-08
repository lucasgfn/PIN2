package com.project.pin.controller;

import com.project.pin.dto.Comprador.CompradorRequestDTO;
import com.project.pin.dto.Comprador.CompradorResponseDTO;
import com.project.pin.entity.Comprador;
import com.project.pin.service.CompradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.*;

@RestController
@RequestMapping("/compradores")
public class CompradorController {

    @Autowired
    private CompradorService compradorService;

    @PostMapping
    public ResponseEntity<Comprador> cadastrar(@Valid @RequestBody Comprador comprador) {
        Comprador criado = compradorService.cadastrarComprador(comprador);
        return new ResponseEntity<>(criado, HttpStatus.CREATED);
    }

    @DeleteMapping("deletar/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        compradorService.deletarComprador(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("Comprador deletado com sucesso.");
    }

    @PutMapping("atualizar/{id}")
    public ResponseEntity<CompradorResponseDTO> atualizar(@PathVariable Long id, @RequestBody CompradorRequestDTO dto) {
        compradorService.updateComprador(id, dto);
        CompradorResponseDTO response = compradorService.getInfosComprador(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("listar/{id}")
    public ResponseEntity<CompradorResponseDTO> buscar(@PathVariable Long id) {
        CompradorResponseDTO dto = compradorService.getInfosComprador(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{username}")
    public Comprador getUsuarioByUsername(@PathVariable String username) {
        return compradorService.buscarPorUsername(username);
    }
}
