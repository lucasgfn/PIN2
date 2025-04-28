package com.project.pin.dto;

import com.project.pin.entity.Livro;

import java.util.List;

public record AutorRequestDTO(Long id, String nome, String sobre, String img, List<Livro> livroList) {
}
