package com.project.pin.dto;

import com.project.pin.entity.Autor;
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
        String nomeAutor
    )
{
    public LivroResponseDTO(Livro livro) {
        this(
                livro.getId(),
                livro.getNomeLivro(),
                livro.getSinopse(),
                livro.getIbsn(),
                livro.getPages(),
                livro.getAnoPublicado(),
                livro.getPrecoUnit(),
                livro.getImg(),
                livro.getAutor().getNome()
        );
    }

}


