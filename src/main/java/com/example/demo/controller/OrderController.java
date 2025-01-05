package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.OrderProductResponse;
import com.example.demo.entity.Order;
import com.example.demo.mockdata.OrderMock;
import com.example.demo.service.OrderService;

@RestController
@RequestMapping()
public class OrderController {
    // * Instance variables
    private final OrderService orderService;
    private final OrderMock orderMock = new OrderMock();

    // * Constructor
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/mock-all-orders")
    public List<Order> getMethodName() {
        return orderMock.getOrders();
    }

    @GetMapping("/orders")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping("/create-order/{userId}")
    public ResponseEntity<Order> createOrder(@RequestBody OrderProductResponse orderProductResponse, @PathVariable Long userId) {
        Order createdOrder = orderService.createOrder(userId, orderProductResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }
    
    @DeleteMapping("/delete-order/{orderId}")
    public ResponseEntity<Order> deleteOrderById(@PathVariable Long orderId) {
        Order deleteOrder = orderService.deleteOrder(orderId);
        return ResponseEntity.status(HttpStatus.CREATED).body(deleteOrder);
    }
}
