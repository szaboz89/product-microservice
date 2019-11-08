package com.szabodev.example.product.microservice.service;

import com.szabodev.example.product.microservice.service.remote.ProductStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockValidator {

    private final ProductService productService;

    private final ProductStockService productStockService;

    @Scheduled(fixedRate = 5000, initialDelay = 5000)
    public void checkAvailableStockAndRequestIfNeeded() {
        productStockService.checkAvailableStockAndRequestIfNeeded(productService.findAll(true));
    }
}