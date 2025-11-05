package com.deliverytech.delivery.dto.Client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ClientDTO {
    @NotNull(message = "Client name cannot be null")
    private String name;
    @NotNull(message = "Client email cannot be null")
    @Email(message = "Invalid email")
    private String email;
    @NotNull(message = "Client CPF cannot be null")
    private String cpf;
    private String phone;
    @NotNull(message = "Client CEP cannot be null")
    private String cep;
    @NotNull(message = "Client address cannot be null")
    private String address;
}
