package com.project.pin.dto.ItemCompra;

import lombok.Data;

public record ItemCompraRequestDTO(
        Long idLivro,
        int quantidade
){}
