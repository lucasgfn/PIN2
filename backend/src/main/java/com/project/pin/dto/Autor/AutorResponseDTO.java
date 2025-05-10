package com.project.pin.dto.Autor;

import com.project.pin.dto.Livro.LivroResumoDTO;
import com.project.pin.entity.Autor;
import java.util.List;
import java.util.stream.Collectors;

public record AutorResponseDTO(
        Long id,
        String nome,
        String sobre,
        String img,
        List<LivroResumoDTO> listLivros
) {
        public AutorResponseDTO(Autor autor) {
                this(
                        autor.getId(),
                        autor.getNome(),
                        autor.getSobre(),
                        autor.getImg() != null ? autor.getImg() : "/images/default-img-profile.jpg", // Usando imagem padrão caso não tenha
                        autor.getListLivros().stream()
                                .map(LivroResumoDTO::new) // Transforma livros em DTO
                                .collect(Collectors.toList())
                );
        }
}
