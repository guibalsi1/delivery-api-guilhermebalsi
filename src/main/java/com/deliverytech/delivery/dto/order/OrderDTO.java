package com.deliverytech.delivery.dto.order;


import com.deliverytech.delivery.dto.OrderItemDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {
    @NotNull(message = "Client id cannot be null")
    private Long clientId;
    @NotNull(message = "Restaurant id cannot be null")
    private Long restaurantId;
    @NotNull(message = "Delivery adress cannot be null")
    @Size(max = 200, message = "Delivery adress cannot exceed 200 characters")
    private String deliveryAdress;
    private String note;
    @NotEmpty(message = "Order item list cannot be empty")
    List<OrderItemDTO> orderItemList;
}
