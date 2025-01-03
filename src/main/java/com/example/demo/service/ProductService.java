package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.entity.SpacificProduct;
import com.example.demo.entity.User;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;

@Service

public class ProductService implements IProductService {
    // Instance of ProductRepository
    @Autowired
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    // Constructor to inject ProductRepository
    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    // Method to retrieve all products from the repository
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Method to retrieve a specific product by its ID
    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    // Method to create a new product
    @Override
    public Product createProduct(Product product, Long userId) {
        // ค้นหา User จากฐานข้อมูล
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        product.setName(product.getName());
        product.setPrice(product.getPrice());
        product.setUser(user);
        return productRepository.save(product);
    }

    // Method to delete a product by its ID
    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // Method to update an existing product
    @Override
    public Product updateProduct(Long productId, Product productRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        // อัปเดตข้อมูลของ Product (เช่น name, price)
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());

        // อัปเดตข้อมูล SpacificProduct
        SpacificProduct existingSpecificProduct = product.getSpacificProduct();
        System.out.println("existingSpecificProduct: " + existingSpecificProduct);

        if (existingSpecificProduct == null) {
            existingSpecificProduct = new SpacificProduct();
            product.setSpacificProduct(existingSpecificProduct);
        }

        // อัปเดตค่าของ SpacificProduct
        existingSpecificProduct.setColor(productRequest.getSpacificProduct().getColor());
        existingSpecificProduct.setWeight(productRequest.getSpacificProduct().getWeight());
        existingSpecificProduct.setDimensions(productRequest.getSpacificProduct().getDimensions());

        return productRepository.save(product);
    }
}
