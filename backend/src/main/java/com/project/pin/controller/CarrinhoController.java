package com.project.pin.controller;

import com.project.pin.dto.Autor.AutorResponseDTO;
import com.project.pin.dto.ItemCompra.ItemCompraRequestDTO;
import com.project.pin.dto.ItemCompra.ItemCompraResponseDTO;
import com.project.pin.entity.Compra;
import com.project.pin.service.CarrinhoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrinho")
@RequiredArgsConstructor
public class CarrinhoController {
    private final CarrinhoService carrinhoSession;

    @PostMapping("/add")
    public ResponseEntity<ItemCompraResponseDTO> adicionarItem(@RequestBody ItemCompraRequestDTO itemDTO) {
        ItemCompraResponseDTO itemCompraResponseDTO = carrinhoSession.adicionarItem(itemDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemCompraResponseDTO);
    }


    @GetMapping("/itens")
    public List<ItemCompraResponseDTO> listarItens() {
        return carrinhoSession.getItens();
    }

    @PutMapping("/atualizar/{livroId}")
    public void atualizarQuantidade(@PathVariable Long livroId, @RequestParam int quantidade) {
        carrinhoSession.atualizarQuantidade(livroId, quantidade);
    }


    @DeleteMapping("/remover/{livroId}")
    public void removerItem(@PathVariable Long livroId) {
        carrinhoSession.removerItem(livroId);
    }

    @PostMapping("/finalizar/{usuarioId}")
    public Compra finalizarCompra(@PathVariable Long usuarioId) {
        return carrinhoSession.finalizarCompra(usuarioId);
    }

    @GetMapping("/total")
    public double calcularTotal() {
        return carrinhoSession.getItens().stream()
                .mapToDouble(item -> item.getQuantidade() * item.getPrecoUnit())
                .sum();
    }
}
