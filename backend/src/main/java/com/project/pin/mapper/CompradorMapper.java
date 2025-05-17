package com.project.pin.mapper;

import com.project.pin.dto.Comprador.CompradorRequestDTO;
import com.project.pin.entity.Comprador;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CompradorMapper {
    void updateFromDto(CompradorRequestDTO dto, @MappingTarget Comprador comprador);
}
