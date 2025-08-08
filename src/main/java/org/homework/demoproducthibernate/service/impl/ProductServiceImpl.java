package org.homework.demoproducthibernate.service.impl;

import lombok.RequiredArgsConstructor;
import org.homework.demoproducthibernate.baseResponse.PaginationResponse;
import org.homework.demoproducthibernate.model.request.ProductRequest;
import org.homework.demoproducthibernate.model.response.ProductResponse;
import org.homework.demoproducthibernate.repository.ProductRepository;
import org.homework.demoproducthibernate.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Developed by ChhornSeyha
 * Date: 07/08/2025
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;


    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        return productRepository.createProduct(productRequest);
    }

    @Override
    public ProductResponse getProductById(Long id) {
        return productRepository.getProductById(id);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        return productRepository.updateProduct(id,productRequest);
    }

    @Override
    public PaginationResponse<ProductResponse> getAllProduct(int size, int page) {
        return productRepository.getAllProducts(size,page);
    }

    @Override
    public List<ProductResponse> searchByProductName(String productName) {
        return productRepository.searchByProductName(productName);
    }

    @Override
    public List<ProductResponse> filterByQuantity(int quantity) {
        return productRepository.filterByQuantity(quantity);
    }
}
