package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IProductRepository extends JpaRepository<Product, Long> {
    public Product findByName(String name);
    public boolean existsByName(String name);
    public List<Product> findAllByRestaurantId(Long restaurantId);
}
