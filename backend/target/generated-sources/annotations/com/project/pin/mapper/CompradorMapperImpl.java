package com.project.pin.mapper;

import com.project.pin.dto.Comprador.CompradorRequestDTO;
import com.project.pin.entity.Comprador;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-17T16:11:50-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
@Component
public class CompradorMapperImpl implements CompradorMapper {

    @Override
    public void updateFromDto(CompradorRequestDTO dto, Comprador comprador) {
        if ( dto == null ) {
            return;
        }

        if ( dto.id() != null ) {
            comprador.setId( dto.id() );
        }
        if ( dto.nome() != null ) {
            comprador.setNome( dto.nome() );
        }
        if ( dto.email() != null ) {
            comprador.setEmail( dto.email() );
        }
        if ( dto.cpf() != null ) {
            comprador.setCpf( dto.cpf() );
        }
        if ( dto.username() != null ) {
            comprador.setUsername( dto.username() );
        }
        if ( dto.telefone() != null ) {
            comprador.setTelefone( dto.telefone() );
        }
        if ( dto.rua() != null ) {
            comprador.setRua( dto.rua() );
        }
        if ( dto.bairro() != null ) {
            comprador.setBairro( dto.bairro() );
        }
        if ( dto.cidade() != null ) {
            comprador.setCidade( dto.cidade() );
        }
        if ( dto.cep() != null ) {
            comprador.setCep( dto.cep() );
        }
        if ( dto.estado() != null ) {
            comprador.setEstado( dto.estado() );
        }
        if ( dto.img() != null ) {
            comprador.setImg( dto.img() );
        }
        comprador.setNivel( dto.nivel() );
        comprador.setRecebeuDesconto( dto.recebeuDesconto() );
    }
}
