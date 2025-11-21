package com.example.sisobjetosperdidos.mapper;

import com.example.sisobjetosperdidos.dto.ObjetoResponse;
import com.example.sisobjetosperdidos.entity.FotoEntity;
import com.example.sisobjetosperdidos.entity.ObjetoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ObjetoMapper {

    @Mapping(target = "fotos", source = "fotos")
    ObjetoResponse toResponse(ObjetoEntity entity);

    default List<String> map(List<FotoEntity> fotos) {
        return fotos != null
                ? fotos.stream().map(FotoEntity::getUrl).toList()
                : List.of();
    }
}

