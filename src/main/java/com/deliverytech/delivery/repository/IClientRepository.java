package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.dto.Client.ClientDTO;
import com.deliverytech.delivery.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findClientByEmailAndActiveTrue(String email);
    List<Client> searchClientByNameContainingIgnoreCase(String name);
    List<Client> findAllByActiveTrue();
    boolean existsByEmail(String email);
    boolean existsByIdAndActiveTrue(Long id);
}
