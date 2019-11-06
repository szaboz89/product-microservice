package com.szabodev.example.product.microservice;

import com.szabodev.example.product.microservice.model.Product;
import com.szabodev.example.product.microservice.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class ProductMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductMicroserviceApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ProductRepository productRepository) {
        return args -> {
            if (productRepository.count() == 0) {
                productRepository.save(Product.builder().name("product 1").description("description 1").price(new BigDecimal(2.90)).minimumStock(3).build());
                productRepository.save(Product.builder().name("product 2").description("description 2").price(new BigDecimal(3.49)).minimumStock(2).build());
                productRepository.save(Product.builder().name("product 3").description("description 3").price(new BigDecimal(5.19)).minimumStock(3).build());
                productRepository.save(Product.builder().name("product 4").description("description 4").price(new BigDecimal(4.00)).minimumStock(5).build());
                productRepository.save(Product.builder().name("product 5").description("description 5").price(new BigDecimal(5.50)).minimumStock(4).build());
            }
        };
    }
}
