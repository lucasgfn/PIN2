package com.project.pin.dto.Livro;

import jakarta.validation.constraints.*;

public record LivroRequestDTO(
        Long id,

        @NotBlank(message = "Nome do livro é obrigatório")
        String nomeLivro,

        @NotBlank(message = "ISBN do livro é obrigatório")
        String isbn,

        @NotBlank(message = "Sinopse do livro é obrigatória")
        String sinopse,

        @Min(value = 1, message = "Número de páginas deve ser maior que zero")
        int pages,

        @Min(value = 1000, message = "Ano de publicação inválido") // ajuste conforme necessário
        int anoPublicado,

        @DecimalMin(value = "0.0", inclusive = false, message = "Preço deve ser maior que zero")
        double precoUnit,

        String img,

        @NotNull(message = "ID do autor é obrigatório")
        Long autorId
) {}
