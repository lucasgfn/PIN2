package com.project.pin.dto;

import com.project.pin.entity.Livro;
import java.util.List;

public record CompradorRequestDTO(Long id, String nome, String email, String cpf, String username
        ,String rua, int numero, String bairro, String cidade, String cep, String estado, String img
        , int nivel, boolean recebeuDesconto){
}
