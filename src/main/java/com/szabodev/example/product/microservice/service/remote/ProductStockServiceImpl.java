package com.szabodev.example.product.microservice.service.remote;

import com.szabodev.example.product.microservice.config.JmsConfig;
import com.szabodev.example.product.microservice.dto.ProductDTO;
import com.szabodev.example.product.microservice.dto.ProductDemandDTO;
import com.szabodev.example.product.microservice.dto.ProductStockDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductStockServiceImpl implements ProductStockService {

    private final RestTemplate restTemplate;

    private final JmsTemplate jmsTemplate;

    @Value("${book.store.microservice.apiUrl}")
    private String apiUrl;

    @Override
    public Optional<ProductStockDTO> findAvailableCountForProduct(Long productId) {
        try {
            return Optional.ofNullable(restTemplate.getForObject(apiUrl + "/product-stocks/" + productId, ProductStockDTO.class));
        } catch (HttpClientErrorException e) {
            // stock details for product id not available
            return Optional.empty();
        }
    }

    @Override
    public void checkAvailableStockAndRequestIfNeeded(List<ProductDTO> products) {
        products.forEach(productDTO -> {
            if ((productDTO.getAvailable() == null || productDTO.getAvailable() < productDTO.getMinimumStock())
                    && (productDTO.getRequiredAmount() == null || productDTO.getRequiredAmount() < 1)) {
                requestMoreProduct(productDTO);
            }
        });
    }

    private void requestMoreProduct(ProductDTO productDTO) {
        ProductDemandDTO productDemand = ProductDemandDTO.builder()
                .productId(productDTO.getId())
                .requiredAmount(productDTO.getMinimumStock())
                .build();
        jmsTemplate.convertAndSend(JmsConfig.PRODUCT_DEMAND_QUEUE, productDemand);
    }
}
