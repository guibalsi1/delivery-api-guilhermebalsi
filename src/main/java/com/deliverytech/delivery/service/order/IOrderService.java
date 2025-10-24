package com.deliverytech.delivery.service.order;

import com.deliverytech.delivery.dto.order.OrderDTO;
import com.deliverytech.delivery.dto.order.OrderResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IOrderService {
    List<OrderResponseDTO> getAllOrders();
    OrderResponseDTO createOrder(OrderDTO orderDTO);
    OrderResponseDTO updateOrder(Long orderId, OrderDTO orderDTO);
    OrderResponseDTO getOrder(Long orderId);
    void deleteOrder(Long orderId);
}
