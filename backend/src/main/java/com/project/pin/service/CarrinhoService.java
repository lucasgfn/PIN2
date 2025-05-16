package com.project.pin.service;

import com.project.pin.dto.ItemCompra.ItemCompraRequestDTO;
import com.project.pin.dto.ItemCompra.ItemCompraResponseDTO;
import com.project.pin.entity.Compra;
import com.project.pin.entity.Comprador;
import com.project.pin.entity.ItemCompra;
import com.project.pin.entity.Livro;
import com.project.pin.repository.CompraRepository;
import com.project.pin.repository.ItemCompraRepository;
import com.project.pin.repository.LivroRepository;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Data
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CarrinhoService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private ItemCompraRepository itemCompraRepository;

    @Getter
    private final List<ItemCompraResponseDTO> itens = new ArrayList<>();

    public ItemCompraResponseDTO adicionarItem(ItemCompraRequestDTO novoItemDTO) {
        for (ItemCompraResponseDTO item : itens) {
            if (item.getLivro().getId().equals(novoItemDTO.idLivro())) {
                item.setQuantidade(item.getQuantidade() + novoItemDTO.quantidade());
                return item;
            }
        }

        Livro livro = livroRepository.findById(novoItemDTO.idLivro())
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        ItemCompraResponseDTO novoItem = new ItemCompraResponseDTO(
                livro,
                novoItemDTO.quantidade(),
                livro.getPrecoUnit()
        );
        itens.add(novoItem);
        return novoItem;
    }



    private void limparCarrinho() {
        itens.clear();
    }

    private double calcularItensCarrinho() {
        return itens.stream()
                .mapToDouble(item -> item.getQuantidade() * item.getPrecoUnit())
                .sum();
    }

    public void atualizarQuantidade(Long livroId, int novaQuantidade) {
        if (novaQuantidade <= 0) {
            removerItem(livroId);
            return;
        }

        for (ItemCompraResponseDTO item : itens) {
            if (item.getLivro().getId().equals(livroId)) {
                item.setQuantidade(novaQuantidade);
                return;
            }
        }
    }

    public void removerItem(Long livroId) {
        itens.removeIf(item -> item.getLivro().getId().equals(livroId));
    }

    public Compra finalizarCompra(Long usuarioId) {
        if (itens.isEmpty()) {
            throw new RuntimeException("Carrinho está vazio. Não é possível finalizar a compra.");
        }

        double total = calcularItensCarrinho();

        Compra compra = new Compra();
        Comprador comprador = new Comprador();
        comprador.setId(usuarioId);
        compra.setComprador(comprador);
        compra.setTotalCompra(total);
        compra.setDataCompra(LocalDateTime.now());

        compra = compraRepository.save(compra);

        for (ItemCompraResponseDTO itemDTO : itens) {
            ItemCompra itemCompra = new ItemCompra();
            itemCompra.setCompra(compra);
            itemCompra.setLivro(itemDTO.getLivro());
            itemCompra.setQuantidade(itemDTO.getQuantidade());
            itemCompra.setPrecoUnit(itemDTO.getPrecoUnit());

            itemCompraRepository.save(itemCompra);
        }

        limparCarrinho();
        return compra;
    }


}
