package com.project.pin.dto.ItemCompra;

import com.project.pin.entity.ItemCompra;

public record ItemCompraResponseDTO(
        Long id,
        Long livroId,
        String nomeLivro,
        int quantidade,
        double precoUnit
) {

    public ItemCompraResponseDTO(ItemCompra item){
        this(
                item.getId(),
                item.getLivro().getId(),
                item.getLivro().getNomeLivro(),
                item.getQuantidade(),
                item.getPrecoUnit()

        );
    }
}
