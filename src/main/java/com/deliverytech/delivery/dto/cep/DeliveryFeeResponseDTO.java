package com.deliverytech.delivery.dto.cep;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DeliveryFeeResponseDTO {
    private String destinationCep;
    private String originCep;
    private BigDecimal deliveryFee;
}
