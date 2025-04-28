package com.project.pin.dto;

public record LivroRequestDTO(
        Long id,
        String nomeLivro,
        String isbn,
        String sinopse,
        int pages,
        int anoPublicado,
        double precoUnit,
        String img
) {
}
