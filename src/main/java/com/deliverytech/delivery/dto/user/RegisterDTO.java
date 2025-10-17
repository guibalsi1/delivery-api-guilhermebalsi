package com.deliverytech.delivery.dto.user;

import com.deliverytech.delivery.entity.RoleEnum;

public record RegisterDTO(String email, String password, String name, RoleEnum role) {
}
