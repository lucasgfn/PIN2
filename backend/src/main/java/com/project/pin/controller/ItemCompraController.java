package com.project.pin.controller;

import com.project.pin.dto.ItemCompra.ItemCompraRequestDTO;
import com.project.pin.dto.ItemCompra.ItemCompraResponseDTO;
import com.project.pin.entity.ItemCompra;
import com.project.pin.entity.Livro;
import com.project.pin.repository.LivroRepository;
import com.project.pin.service.ItemCompraService;
import com.project.pin.session.CarrinhoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens")
public class ItemCompraController {
    @Autowired
    private ItemCompraService itemCompraService;
    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private CarrinhoSession carrinhoSession; // INJECAO AO CARRINHO --> DADOS FICAM TEMPORARIOS

    @PostMapping
    public ResponseEntity<ItemCompraResponseDTO> addItem(
            @RequestBody ItemCompraRequestDTO itemCompraRequestDTO
    ) {
        // Obter livro a partir do ID
        Livro livro = livroRepository.findById(itemCompraRequestDTO.idLivro())
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        // Criar o ItemCompra a partir do DTO e livro
        ItemCompra itemCompra = new ItemCompra();
        itemCompra.setLivro(livro);
        itemCompra.setQuantidade(itemCompraRequestDTO.quantidade());
        itemCompra.setPrecoUnit(livro.getPrecoUnit());

        // Adicionar ao CarrinhoSession
        carrinhoSession.adicionarItem(itemCompra);

        // Chamar o serviço para salvar o item, caso necessário
        ItemCompraResponseDTO itemCompraResponseDTO = itemCompraService.adicionarItem(itemCompra);

        return ResponseEntity.status(HttpStatus.CREATED).body(itemCompraResponseDTO);
    }

    @GetMapping
    public List<ItemCompraResponseDTO> listarItens() {
        return carrinhoSession.getItens().stream()
                .map(ItemCompraResponseDTO::new)
                .toList();
    }

}
