package org.homework.demoproducthibernate.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.homework.demoproducthibernate.baseResponse.ApiResponse;
import org.homework.demoproducthibernate.baseResponse.PaginationResponse;
import org.homework.demoproducthibernate.model.request.ProductRequest;
import org.homework.demoproducthibernate.model.response.ProductResponse;
import org.homework.demoproducthibernate.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.List;

/**
 * Developed by ChhornSeyha
 * Date: 07/08/2025
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/create")
    @Operation(summary = "Add New Product")
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct (@RequestBody @Valid ProductRequest productRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<ProductResponse>builder()
                        .message("Product created successfully")
                        .payload(productService.createProduct(productRequest))
                        .instant(Instant.now())
                        .build()
        );
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update New Product")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct (@Valid @PathVariable Long id, @RequestBody ProductRequest productRequest){
        return ResponseEntity.status(HttpStatus.OK).body(
          ApiResponse.<ProductResponse>builder()
                  .message("Product updated successfully")
                  .payload(productService.updateProduct(id,productRequest))
                  .instant(Instant.now())
                  .build()
        );
    }

    @GetMapping("get/{id}")
    @Operation(summary = "Get all Product")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(
          ApiResponse.<ProductResponse>builder()
                  .message("Get Product Successfully")
                  .payload(productService.getProductById(id))
                  .instant(Instant.now())
                  .build()
        );
    }

    @GetMapping("/get/all-products")
    @Operation(summary = "Get all Products (Pagination)")
    public ResponseEntity<ApiResponse<PaginationResponse<ProductResponse>>> getAllProducts (
            @RequestParam(defaultValue = "10")@Min(value = 1, message = "size must be greater than 0") int size,
            @RequestParam(defaultValue = "1") @Min(value = 1, message = "page must be greater than 0")int page)
    {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.<PaginationResponse<ProductResponse>>builder()
                        .message("Products fetched successfully")
                        .payload(productService.getAllProduct(size,page))
                        .instant(Instant.now())
                        .build()
        );
    }

    @GetMapping("/products/search")
    @Operation(summary = "Search for products by name")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> searchProductName(@RequestParam String searchName) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.<List<ProductResponse>>builder()
                        .message("Products matching name" + " '" + searchName + "' " + "fetched successfully")
                        .payload(productService.searchByProductName(searchName))
                        .instant(Instant.now())
                        .build()
        );
    }

    @GetMapping("/products/low-stock")
    @Operation(summary = "Get Low Stock Products")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> filterByQuantity(@RequestParam int quantity) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.<List<ProductResponse>>builder()
                        .message("Products with quantity less than " + "" + quantity + " " + "fetched successfully")
                        .payload(productService.filterByQuantity(quantity))
                        .instant(Instant.now())
                        .build()
        );
    }

}
