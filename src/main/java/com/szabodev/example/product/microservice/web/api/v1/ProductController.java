package com.szabodev.example.product.microservice.web.api.v1;

import com.szabodev.example.product.microservice.dto.ProductDTO;
import com.szabodev.example.product.microservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> products(@RequestParam(required = false, defaultValue = "false") boolean getAvailableCount) {
        return ResponseEntity.ok(productService.findAll(getAvailableCount));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        Optional<ProductDTO> byId = productService.findById(id);
        return byId.<ResponseEntity<Object>>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found"));
    }
}
