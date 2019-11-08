package com.szabodev.example.product.microservice.service.stock;

import com.szabodev.example.product.microservice.dto.ProductStockDTO;

public interface ProductStockService {

    ProductStockDTO findAvailableCountForProduct(Long productId);
}
