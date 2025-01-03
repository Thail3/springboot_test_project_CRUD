package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Product;

public interface IProductService {
    List<Product> getAllProducts();

    Product getProductById(Long id);

    Product createProduct(Product product, Long userId);

    void deleteProduct(Long id);

    Product updateProduct(Long productId, Product productRequest);
}
