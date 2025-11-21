package com.example.sisobjetosperdidos.mapper;

import com.example.sisobjetosperdidos.dto.EstudianteResponse;
import com.example.sisobjetosperdidos.entity.EstudianteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EstudianteMapper {

    @Mapping(source = "usuario.nombres", target = "nombres")
    @Mapping(source = "usuario.correoInstitucional", target = "correoInstitucional")
    @Mapping(source = "usuario.codigo", target = "codigo")
    @Mapping(source = "usuario.rol", target = "rol")
    EstudianteResponse toResponse(EstudianteEntity entity);
}
