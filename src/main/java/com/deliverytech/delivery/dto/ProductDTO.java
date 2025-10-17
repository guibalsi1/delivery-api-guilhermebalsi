package com.deliverytech.delivery.dto;


import com.deliverytech.delivery.entity.ProductStatus;
import com.deliverytech.delivery.entity.Restaurant;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String image;
    private String category;
    private int quantity;
    private boolean active;
    private BigDecimal rating;
    private ProductStatus isActive;
    private Long restaurantId;
}
