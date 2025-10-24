package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderItemRepository extends JpaRepository<OrderItem, Long> {
}
