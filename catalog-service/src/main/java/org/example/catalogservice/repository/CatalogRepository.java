package org.example.catalogservice.repository;

import org.example.catalogservice.model.entity.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Long> {
    Catalog findByProductId(String productId);

}
