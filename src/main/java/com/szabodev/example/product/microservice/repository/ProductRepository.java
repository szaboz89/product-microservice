package com.szabodev.example.product.microservice.repository;

import com.szabodev.example.product.microservice.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
