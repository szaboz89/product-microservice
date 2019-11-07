package com.szabodev.example.product.microservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long id;

    @NotNull
    private String name;

    private String description;

    private Integer minimumStock;

    private BigDecimal price;

    private Integer available;
}
