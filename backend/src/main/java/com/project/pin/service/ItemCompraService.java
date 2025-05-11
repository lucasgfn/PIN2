package com.project.pin.service;

import com.project.pin.dto.ItemCompra.ItemCompraResponseDTO;
import com.project.pin.entity.Compra;
import com.project.pin.entity.Comprador;
import com.project.pin.entity.ItemCompra;
import com.project.pin.entity.Livro;
import com.project.pin.repository.CompraRepository;
import com.project.pin.repository.CompradorRepository;
import com.project.pin.repository.ItemCompraRepository;
import com.project.pin.repository.LivroRepository;
import com.project.pin.session.CarrinhoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ItemCompraService {
    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private ItemCompraRepository itemCompraRepository;

    @Autowired
    private CarrinhoSession carrinhoSession;

    @Autowired
    private CompradorRepository compradorRepository;


    public ItemCompraResponseDTO adicionarItem(ItemCompra itemCompraRequestDTO) {
        Livro livro = livroRepository.findById(itemCompraRequestDTO.getId())
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        // Cria o item de compra no carrinho (temporário)
        ItemCompra itemCompra = new ItemCompra();
        itemCompra.setLivro(livro);
        itemCompra.setQuantidade(itemCompraRequestDTO.getQuantidade());
        itemCompra.setPrecoUnit(livro.getPrecoUnit());

        // Adiciona o item ao carrinho
        carrinhoSession.adicionarItem(itemCompra);

        return new ItemCompraResponseDTO(itemCompra);
    }

   public List<ItemCompraResponseDTO> listarItemCompra(Long compraId){
       return itemCompraRepository.findByCompraId(compraId).stream()
               .map(ItemCompraResponseDTO::new)
               .toList();
   }

    public Compra finalizarCompra(Long usuarioId) {
        double total = carrinhoSession.calcularItensCarrinho();

        Compra compra = new Compra();

        Comprador comprador = new Comprador();
        comprador.setId(usuarioId); //SOMENTE ASSOCIA O ID DO COMPRADOR
        compra.setComprador(comprador);

        compra.setTotalCompra(total);
        compra.setDataCompra(LocalDateTime.now());
        compra = compraRepository.save(compra);

        //CRIA OS ITEM COMPRA
        for (ItemCompra item : carrinhoSession.getItens()) {
            ItemCompra itemCompra = new ItemCompra();
            itemCompra.setCompra(compra);
            itemCompra.setLivro(item.getLivro());
            itemCompra.setQuantidade(item.getQuantidade());
            itemCompra.setPrecoUnit(item.getPrecoUnit());
            itemCompraRepository.save(itemCompra);
        }

        carrinhoSession.limparCarrrinho();
        return compra;
    }

}
