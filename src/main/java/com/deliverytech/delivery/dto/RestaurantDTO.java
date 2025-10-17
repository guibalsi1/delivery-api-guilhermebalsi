package com.deliverytech.delivery.dto;


import lombok.Data;

import java.util.List;

@Data
public class RestaurantDTO {
    private Long id;
    private String name;
    private String description;
    private String cuisine;
    private String image;
    private String rating;
    private String address;
    private String cnpj;
    private List<ProductDTO> products;
}
