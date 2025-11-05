package com.deliverytech.delivery.control;


import com.deliverytech.delivery.dto.Client.ClientDTO;
import com.deliverytech.delivery.dto.Client.ClientResponseDTO;
import com.deliverytech.delivery.service.client.IClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
@Tag(name = "Cliente", description = "Cliente API")
public class ClientController {
    @Autowired
    private IClientService clientService;


    @SecurityRequirement(name = "bearerAuth")
    @GetMapping()
    @Operation(summary = "Retorna todos os clientes cadastrados.",
            description = "Retorna todos os clientes cadastrados.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Clientes retornados com sucesso.",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<List<ClientResponseDTO>> getAllClients() {
        List<ClientResponseDTO> clients = clientService.getAllClients();
        return ResponseEntity.status(HttpStatus.OK).body(clients);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/search")
    @Operation(summary = "Retorna todos os clientes cadastrados.",
            description = "Retorna todos os clientes cadastrados.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Clientes retornados com sucesso.",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<List<ClientResponseDTO>> searchClientByName(@Valid @RequestParam("name") String name) {
        List<ClientResponseDTO> clients = clientService.searchClientByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(clients);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/active")
    @Operation(summary = "Retorna todos os clientes ativos.",
            description = "Retorna todos os clientes ativos.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Clientes ativos retornados com sucesso.",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<List<ClientResponseDTO>> findAllActive() {
        List<ClientResponseDTO> clients = clientService.getAllActiveClients();
        return ResponseEntity.status(HttpStatus.OK).body(clients);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{clientId}")
    @Operation(summary = "Retorna o cliente com o Id especificado",
            description = "Retorna o cliente com o Id especificado",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Cliente retornado com sucesso.",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida, o cliente não foi encontrado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<ClientResponseDTO> getClient(@Valid @PathVariable("clientId") Long clientId) {
        ClientResponseDTO client = clientService.getClient(clientId);
        return ResponseEntity.status(HttpStatus.OK).body(client);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{clientId}")
    @Operation(summary = "Deleta um cliente no Sistema",
            description = "Muda o status de ativo para não ativo (soft delete) do cliente com o Id especificado",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Cliente deletado com sucesso."
                    ),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida, o cliente não foi encontrado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<Void> deleteClient(@Valid @PathVariable("clientId") Long clientId) {
        clientService.deleteClient(clientId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{clientId}")
    @Operation(summary = "Atualiza o cliente com o Id especificado",
            description = "Atualiza o cliente com o Id especificado",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Cliente atualizado com sucesso.",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida, o cliente não foi encontrado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<ClientResponseDTO> updateClient(@Valid @PathVariable("clientId") Long clientId, @Valid @RequestBody ClientDTO clientDTO) {
        ClientResponseDTO updatedClient = clientService.updateClient(clientId, clientDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedClient);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    @Operation(summary = "Cadastra um novo cliente",
            description = "Cadastra um novo cliente",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Cliente cadastrado com sucesso.",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<ClientResponseDTO> createClient(@Valid @RequestBody ClientDTO clientDTO) {
        ClientResponseDTO createdClient = clientService.createClient(clientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("email/{email}")
    @Operation(summary = "Retorna o cliente com o Email especificado",
            description = "Retorna o cliente com o Email especificado",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Cliente retornado com sucesso.",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida, o cliente não foi encontrado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<ClientResponseDTO> findClientByEmail(@Valid @PathVariable("email") String email) {
        ClientResponseDTO client = clientService.findByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(client);
    }


}
