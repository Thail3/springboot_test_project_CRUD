package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.OrderProductResponse;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderProduct;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    public OrderService(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public List<Order> getAllOrders() {    
        return orderRepository.findAll();
    }

    public Order createOrder(Long userId, OrderProductResponse orderProductResponse) {
        // ค้นหา User จาก userId
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        // สร้าง Order และกำหนด User
        Order order = new Order();
        order.setUser(user);

        // ตรวจสอบว่า orderProducts มีข้อมูลหรือไม่ ถ้าไม่มีให้สร้างลิสต์ว่าง
        List<OrderProduct> orderProducts = orderProductResponse.getOrderProducts() != null ? orderProductResponse.getOrderProducts() : new ArrayList<>();

        for (OrderProductResponse.ProductItem productItem : orderProductResponse.getProducts()) {
        // ค้นหาสินค้าในฐานข้อมูลตาม productId
        Product product = productRepository.findById(productItem.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productItem.getProductId()));

        // สร้าง OrderProduct จาก ProductItem
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProduct(product);  // ตั้งค่าผลิตภัณฑ์
        orderProduct.setQuantity(productItem.getQuantity());  // ตั้งค่าจำนวน
        orderProduct.setPrice(productItem.getPrice());  // ตั้งค่าราคา
        orderProduct.setOrder(order);  // ตั้งค่า Order ให้กับ OrderProduct

        // เพิ่ม OrderProduct ไปยังรายการ
        orderProducts.add(orderProduct);
    }

        // ตั้งค่า OrderProducts ให้กับ Order
        order.setOrderProducts(orderProducts);

        // บันทึก Order ลงในฐานข้อมูล
        return orderRepository.save(order);
    }

    public Order deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + orderId));

        orderRepository.delete(order);
        return order;
    }

}
