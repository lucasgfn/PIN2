package com.project.pin.dto;

import com.project.pin.entity.Autor;
import com.project.pin.entity.Livro;

import java.util.List;

public record AutorResponseDTO(
        Long id,
        String nome,
        String sobre,
        String img,
        List<Livro> listLivros
        )

{
        public AutorResponseDTO(Autor autor) {
                this(autor.getId(), autor.getNome(), autor.getSobre(), autor.getImg(), autor.getListLivros());
        }



}


