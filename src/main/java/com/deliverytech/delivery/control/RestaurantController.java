package com.deliverytech.delivery.control;


import com.deliverytech.delivery.dto.RestaurantDTO;
import com.deliverytech.delivery.service.IRestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants")
@Tag(name = "Restaurante", description = "Restaurante API")
public class RestaurantController {

    @Autowired
    private IRestaurantService restaurantService;

    //Caso não haja o framework do spring, podemos fazer assim:
    //public void RestController(IRestaurantService restaurantService) {
    //    restaurantService = restaurantService;
    //}

    @Operation(
            summary = "Lista todos os Restaurantes",
            description = "Retorna a lista de Restaurantes registrados",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Restaurantes listados com sucesso",
                        content = @Content(mediaType = "application/json")
                )
            }
    )
    @GetMapping
    public ResponseEntity<List<RestaurantDTO>> list() {
        List<RestaurantDTO> restaurants = restaurantService.getAllRestaurants();
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurants);
    }

    @Operation(
            summary = "Cadastra um novo Restaurante",
            description = "Cadastra um novo Restaurante",
            responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Restaurante cadastrado com sucesso",
                        content = @Content(mediaType = "application/json")
                )
            }
    )
    @PostMapping
    public ResponseEntity<RestaurantDTO> create(@Valid @RequestBody RestaurantDTO restaurantDTO) {
        RestaurantDTO restaurant = restaurantService.createRestaurant(restaurantDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurant);
    }
}
