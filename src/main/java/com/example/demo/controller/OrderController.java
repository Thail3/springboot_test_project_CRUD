package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    
}
