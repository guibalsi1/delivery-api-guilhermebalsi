package com.deliverytech.delivery.control;


import com.deliverytech.delivery.dto.ApiResponseWrapper;
import com.deliverytech.delivery.dto.restaurant.RestaurantDTO;
import com.deliverytech.delivery.dto.restaurant.RestaurantResponseDTO;
import com.deliverytech.delivery.service.restaurant.IRestaurantService;
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

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants")
@Tag(name = "Restaurante", description = "Restaurante API")
public class RestaurantController {

    @Autowired
    private IRestaurantService restaurantService;

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    @Operation(
            summary = "Lista todos os Restaurantes",
            description = "Retorna a lista de Restaurantes registrados",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Restaurantes listados com sucesso",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<ApiResponseWrapper<List<RestaurantResponseDTO>>> list(
    ) {
        List<RestaurantResponseDTO> restaurants = restaurantService.getAllRestaurants();
        ApiResponseWrapper<List<RestaurantResponseDTO>> response = new ApiResponseWrapper<>(
                true,
                restaurants,
                "Restaurantes listados com sucesso"
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    @Operation(
            summary = "Cadastra um novo Restaurante",
            description = "Cadastra um novo Restaurante",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Restaurante cadastrado com sucesso",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<ApiResponseWrapper<RestaurantResponseDTO>> create(@Valid @RequestBody RestaurantDTO restaurantDTO) {
        RestaurantResponseDTO restaurant = restaurantService.createRestaurant(restaurantDTO);
        ApiResponseWrapper<RestaurantResponseDTO> response = new ApiResponseWrapper<>(
                true,
                restaurant,
                "Restaurante cadastrado com sucesso"
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(value = "/{restaurantId}")
    @Operation(
            summary = "Retorna o Restaurante pelo ID",
            description = "Retorna o Restaurante correspondente ao ID fornecido",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Restaurante retornado com sucesso",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida, não há nenhum restaurante com esse id"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<ApiResponseWrapper<RestaurantResponseDTO>> getRestaurant(@Valid @PathVariable("restaurantId") Long restaurantId) {
        RestaurantResponseDTO restaurant = restaurantService.getRestaurant(restaurantId);
        ApiResponseWrapper<RestaurantResponseDTO> response = new ApiResponseWrapper<>(
                true,
                restaurant,
                "Restaurante retornado com sucesso"
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping(value = "/{restaurantId}" )
    @Operation(
            summary = "Atualiza o Restaurante pelo ID",
            description = "Atualiza o Restaurante correspondente ao ID fornecido",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Restaurante atualizado com sucesso",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida, não há nenhum restaurante com esse id"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<ApiResponseWrapper<RestaurantResponseDTO>> updateRestaurant(@PathVariable("restaurantId") Long restaurantId,
                                                          @Valid @RequestBody RestaurantDTO restaurantDTO) {
        RestaurantResponseDTO updatedRestaurant = restaurantService.updateRestaurant(restaurantId, restaurantDTO);
        ApiResponseWrapper<RestaurantResponseDTO> response = new ApiResponseWrapper<>(
                true,
                updatedRestaurant,
                "Restaurante atualizado com sucesso"
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping(value = "/{restaurantId}" )
    public ResponseEntity<Void> deleteRestaurant(@PathVariable("restaurantId") Long restaurantId) {
        restaurantService.deleteRestaurant(restaurantId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
