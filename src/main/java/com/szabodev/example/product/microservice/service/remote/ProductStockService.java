package com.szabodev.example.product.microservice.service.remote;

import com.szabodev.example.product.microservice.dto.ProductDTO;
import com.szabodev.example.product.microservice.dto.ProductStockDTO;

import java.util.List;
import java.util.Optional;

public interface ProductStockService {

    Optional<ProductStockDTO> findAvailableCountForProduct(Long productId);

    void checkAvailableStockAndRequestIfNeeded(List<ProductDTO> products);
}
