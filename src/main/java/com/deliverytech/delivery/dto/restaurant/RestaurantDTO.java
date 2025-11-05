package com.deliverytech.delivery.dto.restaurant;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import utils.WorkHours;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.Map;

@Data
public class RestaurantDTO {
    @NotBlank(message = "Restaurant name cannot be null")
    @Size(max = 50, message = "Restaurant name cannot exceed 50 characters")
    private String name;
    @Size(max = 200, message = "Description cannot exceed 200 characters")
    private String description;
    @NotBlank(message = "Cuisine cannot be null")
    @Size(max = 25, message = "Cuisine cannot exceed 25 characters")
    private String cuisine;
    @NotBlank(message = "Restaurant address cannot be null")
    @Size(max = 200, message = "Address cannot exceed 200 characters")
    private String address;
    @NotBlank(message = "CNPJ cannot be null")
    private String cnpj;
    @NotBlank(message = "Restaurant CEP cannot be null")
    private String cep;
    @NotNull(message = "Restaurant delivery fee cannot be null")
    private BigDecimal deliveryFee;
    private BigDecimal deliveryKmFee;
    @NotBlank(message = "Restaurant phone cannot be null")
    private String phone;

    // Permitir que o cliente envie o mapa de horários (opcional)
    private Map<DayOfWeek, WorkHours> workHours;
}
