package com.szabodev.example.product.microservice.service.stock;

public interface ProductStockService {

    Integer findAvailableCountForProduct(Long productId);
}
