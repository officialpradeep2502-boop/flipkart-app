package com.flipkartclone.backend.controller;

import com.flipkartclone.backend.dto.ProductRequestDto;
import com.flipkartclone.backend.entity.Product;
import com.flipkartclone.backend.response.SuccessResponse;
import com.flipkartclone.backend.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.time.LocalDateTime;
import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // ===================== USER + ADMIN =====================

    @Operation(summary = "Get all products (without pagination)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products fetched successfully")
    })
    @GetMapping("/all")
    public ResponseEntity<SuccessResponse<List<Product>>> all(HttpServletRequest request) {
        return ResponseEntity.ok(
                new SuccessResponse<>(
                        LocalDateTime.now(),
                        HttpStatus.OK.value(),
                        "All products fetched successfully",
                        service.all(),
                        request.getRequestURI()
                )
        );
    }

    @Operation(summary = "Get product by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<Product>> byId(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(
                new SuccessResponse<>(
                        LocalDateTime.now(),
                        HttpStatus.OK.value(),
                        "Product fetched successfully",
                        service.byId(id),
                        request.getRequestURI()
                )
        );
    }

    @Operation(summary = "Get products by category")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products fetched by category")
    })
    @GetMapping("/category/{cat}")
    public ResponseEntity<SuccessResponse<List<Product>>> byCategory(
            @PathVariable String cat,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(
                new SuccessResponse<>(
                        LocalDateTime.now(),
                        HttpStatus.OK.value(),
                        "Products fetched by category",
                        service.byCategory(cat),
                        request.getRequestURI()
                )
        );
    }

    @Operation(summary = "Search products by name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Search results returned")
    })
    @GetMapping("/search")
    public ResponseEntity<SuccessResponse<List<Product>>> search(
            @RequestParam String q,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(
                new SuccessResponse<>(
                        LocalDateTime.now(),
                        HttpStatus.OK.value(),
                        "Products search result",
                        service.search(q),
                        request.getRequestURI()
                )
        );
    }

    @Operation(summary = "Get products with pagination and sorting")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Products fetched with pagination")
    })
    @GetMapping
    public ResponseEntity<SuccessResponse<Page<Product>>> getAllProductsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(
                new SuccessResponse<>(
                        LocalDateTime.now(),
                        HttpStatus.OK.value(),
                        "Products fetched with pagination",
                        service.getAllProductsPaged(page, size, sortBy, direction),
                        request.getRequestURI()
                )
        );
    }

    // ===================== ADMIN ONLY =====================

    @Operation(summary = "Create new product (ADMIN only)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
     @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<SuccessResponse<Product>> add(
            @Valid @RequestBody ProductRequestDto dto,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new SuccessResponse<>(
                        LocalDateTime.now(),
                        HttpStatus.CREATED.value(),
                        "Product created successfully",
                        service.add(dto),
                        request.getRequestURI()
                )
        );
    }

    @Operation(summary = "Update product (ADMIN only)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
   @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse<Product>> update(
            @PathVariable Long id,
            @RequestBody Product p,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(
                new SuccessResponse<>(
                        LocalDateTime.now(),
                        HttpStatus.OK.value(),
                        "Product updated successfully",
                        service.update(id, p),
                        request.getRequestURI()
                )
        );
    }

    @Operation(summary = "Delete product (ADMIN only)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<Void>> delete(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        service.delete(id);

        return ResponseEntity.ok(
                new SuccessResponse<>(
                        LocalDateTime.now(),
                        HttpStatus.OK.value(),
                        "Product deleted successfully",
                        null,
                        request.getRequestURI()
                )
        );
    }
}