package com.flipkartclone.backend.controller;


import com.flipkartclone.backend.entity.Product;
import com.flipkartclone.backend.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;
    public ProductController(ProductService service){
        this.service = service;
    }

    // User + ADMIN (No Restriction)
    @GetMapping
    public List<Product> all(){
        return service.all();
    }

    @GetMapping("/{id}")
    public Product byId(@PathVariable Long id){
        return service.byId(id);
    }

    @GetMapping("/category/{cat}")
    public  List<Product> byCategory(@PathVariable String cat){
        return service.byCategory(cat);
    }

    @GetMapping("/search")
    public List<Product> search(@RequestParam String q){
        return service.search(q);
    }


    // ADMIN (POST/PUT/DELETE) — protected by SecurityConfig rules below
    // For Add Product
  //  @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product add(@RequestBody Product p ){
        return service.add(p);
    }

    // For Update Product
  //  @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product p){
        return service.update(id, p);

    }

    // For Delete Product
  // @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }

}
