package com.project.pin.mapper;

import com.project.pin.dto.Comprador.CompradorRequestDTO;
import com.project.pin.entity.Comprador;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-17T15:20:02-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Ubuntu)"
)
@Component
public class CompradorMapperImpl implements CompradorMapper {

    @Override
    public void updateFromDto(CompradorRequestDTO dto, Comprador comprador) {
        if ( dto == null ) {
            return;
        }
    }
}
