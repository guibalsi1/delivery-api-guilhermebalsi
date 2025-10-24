package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.dto.order.OrderDTO;
import com.deliverytech.delivery.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderRepository extends JpaRepository<Order, Long> {
}
