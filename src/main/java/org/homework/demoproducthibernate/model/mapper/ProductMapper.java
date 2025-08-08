package org.homework.demoproducthibernate.model.mapper;

import org.homework.demoproducthibernate.database.Product;
import org.homework.demoproducthibernate.model.request.ProductRequest;
import org.homework.demoproducthibernate.model.response.ProductResponse;
import org.springframework.stereotype.Component;

/**
 * Developed by ChhornSeyha
 * Date: 07/08/2025
 */

@Component
public class ProductMapper {

    public Product mapToProductEntity(ProductRequest productRequest){
        if(productRequest == null){
            return null;
        }
        Product productEntity = new Product();
        productEntity.setProductName(productRequest.productName());
        productEntity.setPrice(productRequest.price());
        productEntity.setQuantity(productRequest.quantity());
        return productEntity;
    }

    public ProductResponse mapToProductResponse (Product productEntity){
        if(productEntity == null){
            return null;
        }
        return new ProductResponse(
                productEntity.getId(),
                productEntity.getProductName(),
                productEntity.getPrice(),
                productEntity.getQuantity()
        );
    }
}
