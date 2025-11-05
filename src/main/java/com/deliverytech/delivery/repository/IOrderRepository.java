package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.dto.order.OrderDTO;
import com.deliverytech.delivery.entity.Order;
import com.deliverytech.delivery.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface IOrderRepository extends JpaRepository<Order, Long> {
    Order findOrderById(Long id);

    List<Order> findAllOrderByClient_Id(Long clientId);
    List<Order> findAllByRestaurant_Id(Long restaurantId);

    List<Order> findAllByOrderDateBetween(LocalDateTime orderDateAfter, LocalDateTime orderDateBefore);
    List<Order> findAllByOrderDateAfter(LocalDateTime orderDateAfter);
    List<Order> findAllByOrderDateBefore(LocalDateTime orderDateBefore);
    List<Order> findAllByOrderDate(LocalDateTime orderDate);
    List<Order> findAllByOrderStatus(OrderStatus orderStatus);

    List<Order> findAllByClient_IdAndRestaurant_Id(Long clientId, Long restaurantId);
    List<Order> findAllByOrderStatusAndOrderDateBetween(OrderStatus orderStatus, LocalDateTime orderDateAfter, LocalDateTime orderDateBefore);
    List<Order> findAllByOrderStatusAndOrderDateAfter(OrderStatus orderStatus, LocalDateTime orderDateAfter);
    List<Order> findAllByOrderStatusAndOrderDateBefore(OrderStatus orderStatus, LocalDateTime orderDateBefore);
    List<Order> findAllByOrderStatusAndOrderDate(OrderStatus orderStatus, LocalDateTime orderDate);
    List<Order> findAllByOrderStatusAndRestaurant_Id(OrderStatus orderStatus, Long restaurantId);
    List<Order> findAllByOrderStatusAndClient_Id(OrderStatus orderStatus, Long clientId);
    List<Order> findAllByOrderStatusAndClient_IdAndRestaurant_Id(OrderStatus orderStatus, Long clientId, Long restaurantId);
}
