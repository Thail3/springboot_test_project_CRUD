package com.example.demo.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") // @JsonIdentityInfo
                                                                                           // เพื่อแก้ปัญหาการวนลูปใน
                                                                                           // JSON response
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore // ป้องกันการวนซ้ำ
    private User user;

    @Embedded // object ที่มีความสัมพันธ์กับ product
    private SpacificProduct spacificProduct;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true) // mappedBy คือ field ใน OrderItem
    // @JsonManagedReference // ป้องกันการวนลูปจาก product -> orderProducts
    // @JsonIgnore // ป้องกันการวนลูปจาก product -> orderProducts
    private List<OrderProduct> orderProducts;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", userId=" + (user != null ? user.getId() : null) +
                ", spacificProduct=" + spacificProduct +
                '}';
    }
}
