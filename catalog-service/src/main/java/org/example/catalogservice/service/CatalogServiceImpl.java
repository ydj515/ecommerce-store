package org.example.catalogservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.catalogservice.model.entity.Catalog;
import org.example.catalogservice.model.mapper.ModelMapper;
import org.example.catalogservice.model.payload.response.CatalogResponse;
import org.example.catalogservice.repository.CatalogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {

    private final CatalogRepository catalogRepository;

    @Override
    public List<CatalogResponse> getAllCatalogs() {

        List<Catalog> catalogs = catalogRepository.findAll();
        List<CatalogResponse> catalogResponses = catalogs.stream().map(ModelMapper.INSTANCE::toCatalogResponse)
                .toList();

        return catalogResponses;
    }
}
