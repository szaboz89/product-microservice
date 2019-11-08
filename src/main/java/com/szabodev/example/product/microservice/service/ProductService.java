package com.szabodev.example.product.microservice.service;

import com.szabodev.example.product.microservice.dto.ProductDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    ProductDTO save(ProductDTO productDTO);

    Optional<ProductDTO> findById(Long id);

    List<ProductDTO> findAll(boolean getAvailableCount);

    void deleteById(Long id);
}
