package com.szabodev.example.product.microservice.service.stock;

import com.szabodev.example.product.microservice.dto.ProductStockDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ProductStockServiceImpl implements ProductStockService {

    private final RestTemplate restTemplate;

    @Value("${book.stock.microservice.apiUrl}")
    private String apiUrl;

    @Override
    public Integer findAvailableCountForProduct(Long productId) {
        try {
            ProductStockDTO stock = restTemplate.getForObject(apiUrl + "/product-stocks/" + productId, ProductStockDTO.class);
            return stock != null ? stock.getAvailable() : 0;
        } catch (HttpClientErrorException e) {
            // stock details for product id not available
            return null;
        }
    }
}
