package org.homework.demoproducthibernate.service;

import org.homework.demoproducthibernate.baseResponse.PaginationResponse;
import org.homework.demoproducthibernate.model.request.ProductRequest;
import org.homework.demoproducthibernate.model.response.ProductResponse;

import java.util.List;

/**
 * Developed by ChhornSeyha
 * Date: 07/08/2025
 */

public interface ProductService {
//    Product
    ProductResponse createProduct (ProductRequest productRequest);
    ProductResponse getProductById (Long id);
    ProductResponse updateProduct (Long id, ProductRequest productRequest);
    PaginationResponse<ProductResponse> getAllProduct (int size, int page);
    List<ProductResponse> searchByProductName(String productName);
    List<ProductResponse> filterByQuantity(int quantity);
}
