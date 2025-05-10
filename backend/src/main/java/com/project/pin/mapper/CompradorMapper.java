package com.project.pin.mapper;

import com.project.pin.dto.Comprador.CompradorRequestDTO;
import com.project.pin.entity.Comprador;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

public interface CompradorMapper {

    CompradorMapper INSTANCE = Mappers.getMapper(CompradorMapper.class);

    void updateFromDto(CompradorRequestDTO dto, @MappingTarget Comprador comprador);
}
