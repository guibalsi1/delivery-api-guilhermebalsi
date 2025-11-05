package com.deliverytech.delivery.service.client;


import com.deliverytech.delivery.dto.Client.ClientDTO;
import com.deliverytech.delivery.dto.Client.ClientResponseDTO;
import com.deliverytech.delivery.entity.Client;
import com.deliverytech.delivery.exception.BusinessException;
import com.deliverytech.delivery.repository.IClientRepository;
import com.deliverytech.delivery.service.cep.CepService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ClientService implements IClientService {
    @Autowired
    private IClientRepository clientRepository;

    @Autowired
    private CepService cepService;

    @Autowired
    private ModelMapper modelMapper;

    public ClientService() {
        super();
    }

    @Override
    public List<ClientResponseDTO> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return Arrays.asList(modelMapper.map(clients, ClientResponseDTO[].class));
    }

    @Override
    public List<ClientResponseDTO> getAllActiveClients() {
        List<Client> clients = clientRepository.findAllByActiveTrue();
        return Arrays.asList(modelMapper.map(clients, ClientResponseDTO[].class));
    }

    @Override
    public ClientResponseDTO createClient(ClientDTO clientDTO) {
        if (clientRepository.existsByEmail(clientDTO.getEmail())) {
            throw new BusinessException("Email já cadastrado: " + clientDTO.getEmail());
        }
        Client entity = modelMapper.map(clientDTO, Client.class);
        entity.setActive(true);
        var location = cepService.getCepLocation(clientDTO.getCep());
        entity.setLatitude(location.latitude());
        entity.setLongitude(location.longitude());
        Client client = clientRepository.save(entity);
        return modelMapper.map(client, ClientResponseDTO.class);
    }

    @Override
    public ClientResponseDTO updateClient(Long clientId, ClientDTO clientDTO) {
        Client client = clientRepository.findById(clientId).orElse(null);
        if (client == null) {
            throw new BusinessException("Client not found with id: " + clientId);
        }
        modelMapper.map(clientDTO, client);
        client = clientRepository.save(client);
        return modelMapper.map(client, ClientResponseDTO.class);
    }

    @Override
    public ClientResponseDTO getClient(Long clientId) {
        return clientRepository.findById(clientId).map(client -> modelMapper.map(client, ClientResponseDTO.class))
                .orElseThrow(() -> new BusinessException("Client not found with id: " + clientId));
    }

    @Override
    public void deleteClient(Long clientId) {
        if (!clientRepository.existsByIdAndActiveTrue(clientId)) {
            throw new BusinessException("Client not found with id: " + clientId);
        }
        Client client = clientRepository.findById(clientId).orElseThrow(
                () -> new BusinessException("Client not found with id: " + clientId)
        );
        client.setActive(false);
        clientRepository.save(client);
    }

    @Override
    public ClientResponseDTO findByEmail(String email) {
        var client = clientRepository.findClientByEmailAndActiveTrue(email).orElseThrow(
                () -> new BusinessException("Client not found with email: " + email)
        );
        return modelMapper.map(client, ClientResponseDTO.class);
    }

    public boolean existsByIdAndActiveTrue(Long clientId) {
        return clientRepository.existsByIdAndActiveTrue(clientId);
    }

    @Override
    public List<ClientResponseDTO> searchClientByName(String name) {
        List<Client> clients = clientRepository.searchClientByNameContainingIgnoreCase(name);
        return Arrays.asList(modelMapper.map(clients, ClientResponseDTO[].class));
    }
}
