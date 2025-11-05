package com.deliverytech.delivery.dto.restaurant;


import lombok.Data;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.Map;

@Data
public class RestaurantResponseDTO {
    private Long id;
    private String cousine;
    private String image;
    private String rating;
    private boolean active;
    private String description;
    private String name;
    private String cuisine;
    private String address;
    private String cnpj;
    private Double latitude;
    private Double longitude;
    private BigDecimal deliveryFee;
    private BigDecimal deliveryKmFee;
    private String phone;
    private Map<DayOfWeek,WorkHoursDTO> workHours;
}
