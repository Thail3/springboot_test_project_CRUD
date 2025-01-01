package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ProductResponse;
import com.example.demo.entity.Product;
import com.example.demo.entity.SpacificProduct;
import com.example.demo.service.ProductService;

@RestController
@RequestMapping("/api") // ? Annotate controller class with base URL prefix
public class ProductController {
    // * Instance variables
    private final ProductService productService;
    private final ProductResponse productResponse;

    // * Constructor with dependency injection
    public ProductController(ProductService productService, ProductResponse productResponse) {
        this.productService = productService;
        this.productResponse = productResponse;
    }

    @GetMapping("/products")
    public ProductResponse getAllProducts() {
        List<Product> products = productService.getAllProducts();
        long totalProducts = products.size();

        productResponse.setProducts(products);
        productResponse.setTotalProducts(totalProducts);
        return productResponse;
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable("id") Long id) {
        return productService.getProductById(id);
    }

    @PostMapping("/product/{userId}")
    public ResponseEntity<?> createProduct(@PathVariable Long userId, @RequestBody Product product) {
        System.out.println("Product created: " + product.getName());
        SpacificProduct createdSpacificProduct = product.getSpacificProduct();
        if (createdSpacificProduct != null) {
            product.setSpacificProduct(createdSpacificProduct);
        }

        // เรียก Service เพื่อสร้าง Product
        productService.createProduct(product, userId);

        // ส่งข้อความตอบกลับพร้อมข้อมูล Product ที่สร้าง
        return ResponseEntity.ok("Product created successfully");
    }

    @DeleteMapping("/delete-product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }


    @PutMapping("/update-product/{productId}")
    public ResponseEntity<?>  updateProduct(
            @PathVariable Long productId, @RequestBody Product productRequest) {
        System.out.println("updateProduct: " + productRequest);

        // ตรวจสอบค่าที่รับจาก request body ว่าถูกต้อง
        if (productRequest == null) {
            return ResponseEntity.badRequest().body(null); // แจ้งว่าขาดข้อมูล
        }

        // เรียก Service เพื่ออัปเดตข้อมูล SpacificProduct
        Product updatedProduct = productService.updateProduct(productId, productRequest);
        return ResponseEntity.ok("Product updated successfully" + updatedProduct);
    }
}
