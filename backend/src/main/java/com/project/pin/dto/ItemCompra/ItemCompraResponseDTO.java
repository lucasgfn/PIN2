package com.project.pin.dto.ItemCompra;

import com.project.pin.entity.Livro;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCompraResponseDTO {
    private Livro livro;
    private int quantidade;
    private double precoUnit;
}
