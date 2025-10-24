package com.deliverytech.delivery.dto.order;

import com.deliverytech.delivery.entity.OrderItem;
import com.deliverytech.delivery.entity.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDTO {
    private Long id;
    private Long clientId;
    private Long restaurantId;
    private String deliveryAddress;
    private String status;
    private String totalPrice;
    private LocalDateTime date;
    private OrderStatus orderStatus;
    private String note;
    private List<OrderItem> orderItemList;
}
