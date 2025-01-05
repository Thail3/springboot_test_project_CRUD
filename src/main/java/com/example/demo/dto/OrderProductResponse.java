package com.example.demo.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.entity.OrderProduct;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class OrderProductResponse {
    private final List<OrderProduct> orderProducts;
    private final List<ProductItem> products;

    public OrderProductResponse(List<OrderProduct> orderProducts, List<ProductItem> products) {
        this.orderProducts = orderProducts;
        this.products = products;

    }

    // ProductItem class ที่เก็บข้อมูลของสินค้าในแต่ละรายการ
    @Getter
    @Setter
    public static class ProductItem {
        private Long productId;  // ID ของสินค้า
        private Integer quantity;  // จำนวนสินค้า
        private Double price;  // ราคาต่อหน่วย
    }
}
