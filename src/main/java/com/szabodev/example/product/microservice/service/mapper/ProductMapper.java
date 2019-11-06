package com.szabodev.example.product.microservice.service.mapper;

import com.szabodev.example.product.microservice.dto.ProductDTO;
import com.szabodev.example.product.microservice.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toDTO(Product product);

    List<ProductDTO> toDTOs(List<Product> products);

    Product toEntity(ProductDTO productDTO);
}
