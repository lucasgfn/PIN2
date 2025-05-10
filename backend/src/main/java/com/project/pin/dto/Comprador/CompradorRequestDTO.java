package com.project.pin.dto.Comprador;


public record CompradorRequestDTO(Long id, String nome, String email, String cpf, String username
        ,String rua, String telefone, String bairro, String cidade, String cep, String estado, String img
        , int nivel, boolean recebeuDesconto){
}
