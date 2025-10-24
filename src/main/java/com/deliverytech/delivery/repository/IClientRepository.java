package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.dto.Client.ClientDTO;
import com.deliverytech.delivery.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClientRepository extends JpaRepository<Client, Long> {
    Client findClientByEmail(String email);

    boolean existsByEmail(String email);
}
