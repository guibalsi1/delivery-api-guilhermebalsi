package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.entity.Product;
import com.deliverytech.delivery.entity.ProductStatus;
import com.deliverytech.delivery.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface IProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryAndIsActive(String category, ProductStatus isActive);

    List<Product> findAllByCategoryAndIsActive(String category, ProductStatus isActive);

    List<Product> findByNameContainingIgnoreCaseAndIsActive(String name, ProductStatus isActive);

    List<Product> findByPriceBetweenAndIsActive(BigDecimal priceAfter, BigDecimal priceBefore, ProductStatus isActive);

    List<Product> findByPriceIsLessThanEqualAndIsActive(BigDecimal priceIsLessThanEqual, ProductStatus isActive);

    List<Product> findAllByRestaurantAndIsActive(Restaurant restaurant, ProductStatus isActive);

    List<Product> findAllByRestaurant_IdAndIsActive(Long restaurantId, ProductStatus productStatus);

    boolean existsByIdAndIsActive(Long id, ProductStatus isActive);

    boolean existsByRestaurant_Id(Long restaurantId);
}
