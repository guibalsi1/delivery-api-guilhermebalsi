package com.deliverytech.delivery.service.order;


import com.deliverytech.delivery.dto.OrderItemDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IOrderItemService {
    List<OrderItemDTO> getAllItensOrder();
    OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO);
    OrderItemDTO updateOrderItem(Long orderItemId, OrderItemDTO orderItemDTO);
    OrderItemDTO getOrderItem(Long orderItemId);
    void deleteOrderItem(Long orderItemId);
}
