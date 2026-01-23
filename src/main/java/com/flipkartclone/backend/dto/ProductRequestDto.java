package com.flipkartclone.backend.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Data
public class ProductRequestDto {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotBlank
    private String category;


    @NotNull
    @Positive
    private Integer stock;
}
