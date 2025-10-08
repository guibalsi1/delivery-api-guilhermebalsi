package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IRestaurantRepository extends JpaRepository<Restaurant, Long> {
    public Restaurant findByName(String name);
    public boolean existsByName(String name);
}
