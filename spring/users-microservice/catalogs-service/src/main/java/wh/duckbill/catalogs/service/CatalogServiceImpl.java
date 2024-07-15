package wh.duckbill.catalogs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import wh.duckbill.catalogs.jpa.CatalogEntity;
import wh.duckbill.catalogs.jpa.CatalogRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {
    private final CatalogRepository catalogRepository;

    @Override
    public Iterable<CatalogEntity> getAllCatalogs() {
        return catalogRepository.findAll();
    }
}
