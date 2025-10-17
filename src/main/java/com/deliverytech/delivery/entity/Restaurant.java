package com.deliverytech.delivery.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long id;
    @Column(name = "restaurant_name", nullable = false)
    private String name;
    @Column(name = "restaurant_description", nullable = false)
    private String description;
    private String cuisine;
    @Column(name = "restaurant_rating")
    private BigDecimal rating;
    private String image;
    private String address;
    private String cnpj;
    @OneToMany(mappedBy = "restaurant")
    private List<Product> products;
}
