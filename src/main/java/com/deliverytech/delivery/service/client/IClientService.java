package com.deliverytech.delivery.service.client;

import com.deliverytech.delivery.dto.Client.ClientDTO;
import com.deliverytech.delivery.dto.Client.ClientResponseDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IClientService {
    List<ClientResponseDTO> getAllClients();
    ClientResponseDTO createClient(ClientDTO clientDTO);
    ClientResponseDTO updateClient(Long clientId, ClientDTO clientDTO);
    ClientResponseDTO getClient(Long clientId);
    void deleteClient(Long clientId);
    ClientResponseDTO searchByEmail(@Valid String email);
}
