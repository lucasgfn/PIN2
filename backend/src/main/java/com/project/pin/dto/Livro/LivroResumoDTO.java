package com.project.pin.dto.Livro;

import com.project.pin.entity.Livro;

public record LivroResumoDTO(
        Long id,
        String nomeLivro
) {
    public LivroResumoDTO(Livro livro) {
        this(livro.getId(), livro.getNomeLivro());
    }
}
