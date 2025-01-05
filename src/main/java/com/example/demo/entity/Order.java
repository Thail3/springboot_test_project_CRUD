package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "purchase_order")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // ป้องกันปัญหาจาก Hibernate Lazy Initialization
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // Many orders can be associated with one user
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY) // One order can have many order items
    @JsonManagedReference // ป้องกันการวนลูปจาก Order -> OrderProduct
    // @JsonIgnore
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime orderDate = LocalDateTime.now();

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + (user != null ? user.getId() : null) +
                ", orderItems=" + orderProducts.stream().map(OrderProduct::toString).collect(Collectors.joining(", ")) +
                ", orderDate=" + orderDate +
                '}';
    }
}
