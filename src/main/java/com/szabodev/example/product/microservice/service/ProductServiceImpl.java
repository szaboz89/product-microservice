package com.szabodev.example.product.microservice.service;

import com.szabodev.example.product.microservice.dto.ProductDTO;
import com.szabodev.example.product.microservice.dto.ProductStockDTO;
import com.szabodev.example.product.microservice.model.Product;
import com.szabodev.example.product.microservice.repository.ProductRepository;
import com.szabodev.example.product.microservice.service.mapper.ProductMapper;
import com.szabodev.example.product.microservice.service.remote.ProductStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final ProductStockService productStockService;

    @Override
    public ProductDTO save(ProductDTO productDTO) {
        Product saved = productRepository.save(productMapper.toEntity(productDTO));
        return productMapper.toDTO(saved);
    }

    @Override
    public Optional<ProductDTO> findById(Long id) {
        Optional<Product> byId = productRepository.findById(id);
        return Optional.ofNullable(productMapper.toDTO(byId.orElse(null)));
    }

    @Override
    public List<ProductDTO> findAll(boolean getAvailableCount) {
        List<ProductDTO> productDTOS = productMapper.toDTOs((List<Product>) productRepository.findAll());
        if (getAvailableCount) {
            productDTOS.forEach(product -> {
                Optional<ProductStockDTO> stock = productStockService.findAvailableCountForProduct(product.getId());
                stock.ifPresent(productStockDTO -> {
                    product.setAvailable(productStockDTO.getAvailable());
                    product.setRequiredAmount(productStockDTO.getRequiredAmount());
                });
            });
        }
        return productDTOS;
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
