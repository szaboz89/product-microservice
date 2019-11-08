package com.szabodev.example.product.microservice.service;

import com.szabodev.example.product.microservice.config.JmsConfig;
import com.szabodev.example.product.microservice.dto.ProductDTO;
import com.szabodev.example.product.microservice.dto.ProductDemandDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockValidator {

    private final ProductService productService;

    private final JmsTemplate jmsTemplate;

    @Scheduled(fixedRate = 5000, initialDelay = 5000)
    public void checkAvailableStockAndRequestIfNeeded() {
        productService.findAll(true).forEach(productDTO -> {
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