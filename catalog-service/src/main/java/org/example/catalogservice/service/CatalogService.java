package org.example.catalogservice.service;

import org.example.catalogservice.model.entity.Catalog;
import org.example.catalogservice.model.payload.response.CatalogResponse;

import java.util.List;

public interface CatalogService {
    List<CatalogResponse> getAllCatalogs();
}
