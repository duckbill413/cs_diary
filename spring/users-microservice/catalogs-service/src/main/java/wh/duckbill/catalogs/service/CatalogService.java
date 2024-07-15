package wh.duckbill.catalogs.service;

import wh.duckbill.catalogs.jpa.CatalogEntity;

public interface CatalogService {
    Iterable<CatalogEntity> getAllCatalogs();
}
