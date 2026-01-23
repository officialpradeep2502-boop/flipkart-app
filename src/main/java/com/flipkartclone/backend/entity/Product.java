package com.flipkartclone.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;

@Builder
@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue (strategy =GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(length = 2000)
    private String description;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private String category;  // mobile
    private String brand;
    private String imageUrl;

    @Column(nullable = false)
    private Integer stock;  // inventory

    @Builder.Default
    private Instant createdAt = Instant.now();



}
