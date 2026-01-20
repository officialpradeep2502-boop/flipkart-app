package com.flipkartclone.backend.service;


import com.flipkartclone.backend.dto.ProductRequestDto;
import com.flipkartclone.backend.entity.Product;
import com.flipkartclone.backend.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repo;
    public  ProductService(ProductRepository repo){
        this.repo =repo;
    }



    public Product add(@Valid ProductRequestDto dto ){
        Product p = Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .category(dto.getCategory())
                .stock(dto.getStock())
                .build();
        return repo.save(p);
    }
    public Product update (Long id, Product p){
        Product db = repo.findById(id).orElseThrow(()-> new RuntimeException("Product Not Found"));
        db.setName(p.getName());
        db.setDescription(p.getDescription());
        db.setPrice(p.getPrice());
        db.setCategory(p.getCategory());
        db.setBrand(p.getBrand());
        db.setImageUrl(p.getImageUrl());
        db.setStock(p.getStock());
        return repo.save(db);

    }

    public List<Product> all(){
        return repo.findAll();
    }

    public Product byId(Long id){
        return repo.findById(id).orElseThrow(()-> new RuntimeException("Product Not found"));
    }
     public List<Product> byCategory(String c){
        return repo.findByCategoryIgnoreCase(c);
     }
     public List<Product> search(String q){
        return repo.findByNameContainingIgnoreCase(q);
     }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
