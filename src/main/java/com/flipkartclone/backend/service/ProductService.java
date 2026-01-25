package com.flipkartclone.backend.service;

import com.flipkartclone.backend.dto.ProductRequestDto;
import com.flipkartclone.backend.entity.Product;
import com.flipkartclone.backend.exception.ProductNotFoundException;
import com.flipkartclone.backend.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    // ===============================
    // 1️⃣ Pagination + Sorting
    // ===============================
    public Page<Product> getAllProductsPaged(int page, int size, String sortBy, String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return repo.findAll(pageable);
    }

    // ===============================
    // 2️⃣ Add Product
    // ===============================
    public Product add(@Valid ProductRequestDto dto) {

        Product product = Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .category(dto.getCategory())
                .stock(dto.getStock())
                .build();

        return repo.save(product);
    }

    // ===============================
    // 3️⃣ Update Product
    // ===============================
    public Product update(Long id, Product p) {

        Product dbProduct = repo.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found with id: " + id));

        dbProduct.setName(p.getName());
        dbProduct.setDescription(p.getDescription());
        dbProduct.setPrice(p.getPrice());
        dbProduct.setCategory(p.getCategory());
        dbProduct.setBrand(p.getBrand());
        dbProduct.setImageUrl(p.getImageUrl());
        dbProduct.setStock(p.getStock());

        return repo.save(dbProduct);
    }

    // ===============================
    // 4️⃣ Get All Products (No Pagination)
    // ===============================
    public List<Product> all() {
        return repo.findAll();
    }

    // ===============================
    // 5️⃣ Get Product by ID
    // ===============================
    public Product byId(Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found with id: " + id));
    }

    // ===============================
    // 6️⃣ Get Products by Category
    // ===============================
    public List<Product> byCategory(String category) {
        return repo.findByCategoryIgnoreCase(category);
    }

    // ===============================
    // 7️⃣ Search Products by Name
    // ===============================
    public List<Product> search(String keyword) {
        return repo.findByNameContainingIgnoreCase(keyword);
    }

    // ===============================
    // 8️⃣ Delete Product
    // ===============================
    public void delete(Long id) {

        Product product = repo.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found with id: " + id));

        repo.delete(product);
    }
}