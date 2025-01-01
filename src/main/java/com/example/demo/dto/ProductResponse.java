package com.example.demo.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.entity.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class ProductResponse {
    private List<Product> products;
    private long totalProducts;

    public ProductResponse(List<Product> products, long totalProducts) {
        this.products = products;
        this.totalProducts = totalProducts;
    }
}
