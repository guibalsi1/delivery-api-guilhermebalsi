package com.deliverytech.delivery;

import com.deliverytech.delivery.dto.restaurant.RestaurantDTO;
import com.deliverytech.delivery.dto.restaurant.RestaurantResponseDTO;
import com.deliverytech.delivery.entity.Restaurant;
import com.deliverytech.delivery.repository.IRestaurantRepository;
import com.deliverytech.delivery.service.restaurant.IRestaurantService;
import com.deliverytech.delivery.service.restaurant.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do RestaurantService")
public class RestaurantServiceTest {
    @Mock
    private IRestaurantRepository repository;

    @InjectMocks
    private RestaurantService service;

    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Rio's Gourmet");
        restaurant.setCep("11111111");
        restaurant.setPhone("11999999999");
        restaurant.setAddress("Rua das Flores, 123");
        restaurant.setCuisine("Italiana");
        restaurant.setCnpj("11111111111");
        restaurant.setDeliveryFee(BigDecimal.ONE);
        restaurant.setCreatedAt(LocalDateTime.now());
    }

    @Test
    @DisplayName("Deve salvar restaurante com dados válidos")
    void should_SaveRestaurante_When_ValidData() {
        // Given
        when(repository.existsByName(anyString())).thenReturn(false);
        when(repository.save(any(Restaurant.class))).thenReturn(restaurant);

        ModelMapper mapper = new ModelMapper();
        RestaurantDTO restaurantDto = mapper.map(restaurant, RestaurantDTO.class);

        // When
        RestaurantResponseDTO resultado = service.createRestaurant(restaurantDto);

        // Then
        assertNotNull(resultado);
        assertEquals("Rio's Gourmet", resultado.getName());

        assertEquals("11999999999", resultado.getPhone());
        verify(repository).save(restaurant);
    }
}
