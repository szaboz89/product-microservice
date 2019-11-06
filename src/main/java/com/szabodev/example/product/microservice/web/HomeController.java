package com.szabodev.example.product.microservice.web;

import com.szabodev.example.product.microservice.dto.ProductDTO;
import com.szabodev.example.product.microservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private static final String PRODUCT_PRODUCTS = "product/products";
    private static final String PRODUCT_PRODUCT_FORM = "product/product-form";
    private static final String REDIRECT_PRODUCTS = "redirect:/products";
    private static final String EDITED_PRODUCT_MODEL = "editedProduct";

    private final ProductService productService;

    @GetMapping({"/", "/products"})
    public String productList(Model model) {
        model.addAttribute("products", productService.findAll());
        return PRODUCT_PRODUCTS;
    }

    @GetMapping("/add-product")
    public String productFormForNew(Model model) {
        model.addAttribute(EDITED_PRODUCT_MODEL, ProductDTO.builder().build());
        return PRODUCT_PRODUCT_FORM;
    }

    @GetMapping("/products/{id}/delete")
    public String deleteProduct(@PathVariable Long id) {
        productService.findById(id).ifPresent(product -> {
            log.info("Delete product: {}", product);
            productService.deleteById(id);
        });
        return REDIRECT_PRODUCTS;
    }

    @GetMapping("/products/{id}/edit")
    public String productFormForEdit(Model model, @PathVariable Long id) {
        Optional<ProductDTO> byId = productService.findById(id);
        if (byId.isPresent()) {
            model.addAttribute(EDITED_PRODUCT_MODEL, byId.get());
            return PRODUCT_PRODUCT_FORM;
        } else {
            return REDIRECT_PRODUCTS;
        }
    }

    @PostMapping("/products")
    public String saveProduct(@ModelAttribute(EDITED_PRODUCT_MODEL) ProductDTO product) {
        ProductDTO saved = productService.save(product);
        log.info("Product saved: {}", saved);
        return REDIRECT_PRODUCTS;
    }
}
