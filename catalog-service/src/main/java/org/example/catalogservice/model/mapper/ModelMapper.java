package org.example.catalogservice.model.mapper;

import org.example.catalogservice.model.entity.Catalog;
import org.example.catalogservice.model.payload.response.CatalogResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ModelMapper {
    ModelMapper INSTANCE = Mappers.getMapper(ModelMapper.class);

    CatalogResponse toCatalogResponse(Catalog catalog);

}
