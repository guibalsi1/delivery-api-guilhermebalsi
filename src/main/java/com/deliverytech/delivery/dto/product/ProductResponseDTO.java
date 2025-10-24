package com.deliverytech.delivery.dto.product;

import com.deliverytech.delivery.entity.ProductStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String image;
    private String category;
    private int quantity;
    private BigDecimal rating;
    private ProductStatus isActive;
    private String restaurantId;
}
