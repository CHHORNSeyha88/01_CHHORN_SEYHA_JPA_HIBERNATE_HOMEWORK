package org.homework.demoproducthibernate.repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.homework.demoproducthibernate.baseResponse.Pagination;
import org.homework.demoproducthibernate.baseResponse.PaginationResponse;
import org.homework.demoproducthibernate.database.Product;
import org.homework.demoproducthibernate.exception.NotFoundException;
import org.homework.demoproducthibernate.model.mapper.ProductMapper;
import org.homework.demoproducthibernate.model.request.ProductRequest;
import org.homework.demoproducthibernate.model.response.ProductResponse;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Developed by ChhornSeyha
 * Date: 07/08/2025
 */

@Repository
@RequiredArgsConstructor
public class ProductRepository {
    @PersistenceContext
    private final EntityManager entityManager;

//    mapper
    private final ProductMapper productMapper;

    @Transactional
    public ProductResponse createProduct(ProductRequest productRequest){
        Product productCreated = productMapper.mapToProductEntity(productRequest);
//        save
        entityManager.persist(productCreated);

        return productMapper.mapToProductResponse(productCreated);
    }


//    fetch product by ID
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id){
        Product fetchProduct = entityManager.find(Product.class,id);
        if(fetchProduct ==null){
            throw new NotFoundException("can't find this product with Id :" + id);
        }
        return productMapper.mapToProductResponse(fetchProduct);
    }


//    fetch all with pagination
    @Transactional(readOnly = true)
    public PaginationResponse<ProductResponse> getAllProducts(int size, int page){

//        count products by id
        String countTotalQuery = "SELECT COUNT(p.id) FROM Product p";
        Long totalElement = entityManager.createQuery(countTotalQuery,Long.class).getSingleResult();

//        calculate pages
        int totalPages = (int) Math.ceil((double) totalElement/size);

//        query data
        TypedQuery<Product> query = entityManager.createQuery("SELECT p from Product p ", Product.class);
        int offset = (page-1) * size;

        query.setFirstResult(offset);
        query.setMaxResults(size);

//push to our list
        List<Product> fetchAllProductOnPage = query.getResultList();

//        map result on page to dto
        List<ProductResponse> items = fetchAllProductOnPage.stream()
                .map(productMapper::mapToProductResponse)
                .collect(Collectors.toList());

//        add pagination
        Pagination pagination = Pagination.builder()
                .currentPages(page)
                .pageSize(size)
                .totalPages(totalPages)
                .totalElements(totalElement)
                .build();

        return PaginationResponse.<ProductResponse>builder()
                .items(items)
                .pagination(pagination)
                .build();
    }


//    update
    @Transactional
    public ProductResponse updateProduct(Long id,ProductRequest productRequest){
        Product existProduct= entityManager.find(Product.class,id);
        if(existProduct ==null){
            throw new NotFoundException("can't find this product with Id :" + id);
        }
        existProduct.setProductName(productRequest.productName());
        existProduct.setPrice(productRequest.price());
        existProduct.setQuantity(productRequest.quantity());

        // detach from manage
        entityManager.detach(existProduct);
        // save
        entityManager.merge(existProduct);

        return productMapper.mapToProductResponse(existProduct);
    }


//    delete
    public String deleteProduct(Long id){
        Product fetchProduct = entityManager.find(Product.class,id);
        if(fetchProduct ==null){
            throw new NotFoundException("can't find this product with Id :" + id);
        }
        entityManager.remove(fetchProduct);

        return "delete product successfully with this Id :" + id;

    }

//    search by name

    public List<ProductResponse> searchByProductName(String productName) {
//        defind sql
        String jpql = "SELECT p FROM Product p where lower(p.productName) LIKE lower(:name) ";
        // 2. Create the TypedQuery
        TypedQuery<Product> query = entityManager.createQuery(jpql, Product.class);

        query.setParameter("name", productName + "%");
        List<Product> foundProducts = query.getResultList();

        return foundProducts.stream()
                .map(productMapper::mapToProductResponse)
                .collect(Collectors.toList());
    }


    public List<ProductResponse> filterByQuantity(int quantity){
        String jpql = "select p from Product p where p.quantity < :quantity";
        TypedQuery<Product> query = entityManager.createQuery(jpql, Product.class);
        query.setParameter("quantity",quantity);

        List<Product> filteredProducts = query.getResultList();
        return filteredProducts.stream()
                .map(productMapper::mapToProductResponse)
                .collect(Collectors.toList());
    }
}
