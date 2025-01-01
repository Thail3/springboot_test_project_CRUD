package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable // This annotation indicates that the class can be embedded in another entity.
@Getter
@Setter
public class SpacificProduct {
    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private String dimensions;

    @Override
    public String toString() {
        return "SpacificProduct{" +
                "color='" + color + '\'' +
                ", weight=" + weight +
                ", dimensions='" + dimensions + '\'' +
                '}';
    }
}
