package com.deliverytech.delivery.service.order;

import com.deliverytech.delivery.dto.order.OrderDTO;
import com.deliverytech.delivery.dto.order.OrderResponseDTO;
import com.deliverytech.delivery.entity.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IOrderService {
    OrderResponseDTO createOrder(OrderDTO orderDTO);
    List<OrderResponseDTO> getAllOrders();
    List<OrderResponseDTO> getAllOrdersByClientId(Long clientId);
    List<OrderResponseDTO> getAllOrdersByRestaurantId(Long restaurantId);
    List<OrderResponseDTO> getAllOrdersByClientAndRestaurantId(Long clientId, Long restaurantId);
    List<OrderResponseDTO> getAllOrdersByOrderStatus(OrderStatus orderStatus);
    List<OrderResponseDTO> getAllOrdersByOrderStatusAndClient(OrderStatus orderStatus, Long clientId);
    List<OrderResponseDTO> getAllOrdersByOrderStatusAndRestaurant(OrderStatus orderStatus, Long restaurantId);
    List<OrderResponseDTO> getAllOrdersByOrderStatusAndClientAndRestaurant(OrderStatus orderStatus, Long clientId, Long restaurantId);
    List<OrderResponseDTO> getAllOrdersByOrderDate(String orderDate);
    List<OrderResponseDTO> getAllOrdersByOrderDateBetween(String orderDateAfter, String orderDateBefore);
    List<OrderResponseDTO> getAllOrdersByOrderDateAfter(String orderDateAfter);
    List<OrderResponseDTO> getAllOrdersByOrderDateBefore(String orderDateBefore);
    List<OrderResponseDTO> getAllOrdersByOrderDateAndOrderStatus(String orderDate, OrderStatus orderStatus);
    OrderResponseDTO updateOrderStatus(Long orderId);
    OrderResponseDTO getOrder(Long orderId);
    void deleteOrder(Long orderId);
}
