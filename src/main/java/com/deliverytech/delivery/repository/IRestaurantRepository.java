package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;


@Repository
public interface IRestaurantRepository extends JpaRepository<Restaurant, Long> {
    public List<Restaurant> searchRestaurantByNameAndActiveTrue(String name);
    public Restaurant findRestaurantByIdAndActiveTrue(Long id);
    public boolean existsByIdAndActiveTrue(Long id);
    public List<Restaurant> searchRestaurantByCuisineAndActiveTrue(String category);
    public List<Restaurant> findAllByActiveTrue();
    public List<Restaurant> findAllByActiveFalse();
    boolean existsByName(String name);
}
