package com.flipkartclone.backend.service;


import com.flipkartclone.backend.dto.ProductRequestDto;
import com.flipkartclone.backend.entity.Product;
import com.flipkartclone.backend.exception.ProductNotFoundException;
import com.flipkartclone.backend.repository.ProductRepository;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repo;
    public  ProductService(ProductRepository repo){
        this.repo =repo;
    }

    public Page<Product> getAllProductsPaged(int page, int size, String sortBy, String direction) {

        Sort sort =  direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return repo.findAll(pageable);
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
        return repo.findById(id).orElseThrow(()-> new ProductNotFoundException("Product Not found"));
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
