package com.project.pin.dto.Livro;

import com.project.pin.entity.Livro;

public record LivroResponseDTO(
        Long id,
        String isbn,
        String nomeLivro,
        String sinopse,
        int pages,
        int anoPublicado,
        double precoUnit,
        String img,
        Long idAutor,
        String nomeAutor
) {
    public LivroResponseDTO(Livro livro) {
        this(
                livro.getId(),
                livro.getIsbn(),
                livro.getNomeLivro(),
                livro.getSinopse(),
                livro.getPages(),
                livro.getAnoPublicado(),
                livro.getPrecoUnit(),
                livro.getImg() != null ? livro.getImg() : "/images/default-img-book.jpg", // Usando imagem padrão
                livro.getAutor().getId(),
                livro.getAutor().getNome()
        );
    }

}
