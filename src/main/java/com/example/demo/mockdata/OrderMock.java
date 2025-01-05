package com.example.demo.mockdata;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderProduct;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;

public class OrderMock {
    public List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();

        // Mock User 1
        User mockUser1 = createMockUser(1L, "user1", "password1");

        // Mock Order 1
        Order order1 = new Order();
        order1.setId(1L); 
        order1.setUser(mockUser1); // Set user
        order1.setOrderDate(LocalDateTime.of(2025, 1, 1, 10, 0));

        // Mock OrderProduct 1
        List<OrderProduct> orderItems1 = new ArrayList<>();
        OrderProduct item1 = new OrderProduct();
        item1.setId(1L);
        item1.setOrder(order1); 
        item1.setProduct(createMockProduct(1L, "Product A", 100.0));
        item1.setQuantity(2);
        orderItems1.add(item1);

        order1.setOrderProducts(orderItems1);

        // Add order to user
        mockUser1.getOrders().add(order1); 

        // Mock User 2
        User mockUser2 = createMockUser(2L, "user2", "password2");

        // Mock Order 2
        Order order2 = new Order();
        order2.setId(2L); 
        order2.setUser(mockUser2); 
        order2.setOrderDate(LocalDateTime.of(2025, 1, 2, 14, 0));

        // Mock OrderProduct 2
        List<OrderProduct> orderItems2 = new ArrayList<>();
        OrderProduct item2 = new OrderProduct();
        item2.setId(2L);
        item2.setOrder(order2);
        item2.setProduct(createMockProduct(2L, "Product B", 200.0));
        item2.setQuantity(1);
        orderItems2.add(item2);

        order2.setOrderProducts(orderItems2);

        // Add order to user
        mockUser2.getOrders().add(order2);

        // Add orders to the list
        orders.add(order1);
        orders.add(order2);

        return orders;
    }

// Example JSON response for /api/mock-all-orders endpoint
// [
//     {
//         "id": 1,
//         "user": {
//             "id": 1,
//             "username": "user1",
//             "password": "password1",
//             "products": null,
//             "orders": [
//                 {
//                     "id": 1,
//                     "orderDate": "2025-01-01T10:00:00",
//                     "orderItems": [
//                         {
//                             "id": 1,
//                             "product": {
//                                 "id": 1,
//                                 "name": "Product A",
//                                 "price": 100.0
//                             },
//                             "quantity": 2
//                         }
//                     ]
//                 }
//             ]
//         },
//         "orderDate": "2025-01-01T10:00:00"
//     },
//     {
//         "id": 2,
//         "user": {
//             "id": 2,
//             "username": "user2",
//             "password": "password2",
//             "products": null,
//             "orders": [
//                 {
//                     "id": 2,
//                     "orderDate": "2025-01-02T14:00:00",
//                     "orderItems": [
//                         {
//                             "id": 2,
//                             "product": {
//                                 "id": 2,
//                                 "name": "Product B",
//                                 "price": 200.0
//                             },
//                             "quantity": 1
//                         }
//                     ]
//                 }
//             ]
//         },
//         "orderDate": "2025-01-02T14:00:00"
//     }
// ]


   // Mock Product creation
   private Product createMockProduct(Long id, String name, Double price) {
    Product product = new Product();
    product.setId(id);
    product.setName(name);
    product.setPrice(price);
    return product;
}

    // Mock User creation with password
    private User createMockUser(Long id, String username, String password) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password); // Set password
        user.setOrders(new ArrayList<>()); // Initialize orders list
        return user;
    }
}
