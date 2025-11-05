package com.deliverytech.delivery.service.restaurant;


import com.deliverytech.delivery.dto.cep.DeliveryFeeResponseDTO;
import com.deliverytech.delivery.dto.restaurant.RestaurantDTO;
import com.deliverytech.delivery.dto.restaurant.RestaurantResponseDTO;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
public interface IRestaurantService {
    List<RestaurantResponseDTO> getAllRestaurants();
    RestaurantResponseDTO createRestaurant(RestaurantDTO restaurantDTO);
    RestaurantResponseDTO updateRestaurant(Long restaurantId, RestaurantDTO restaurantDTO);
    RestaurantResponseDTO getRestaurant(Long restaurantId);
    List<RestaurantResponseDTO> searchByName(String name);
    List<RestaurantResponseDTO> searchByCategory(String category);
    List<RestaurantResponseDTO> searchAllActiveTrue();
    List<RestaurantResponseDTO> searchAllActiveFalse();
    RestaurantResponseDTO alterRestaurantStatus(Long restaurantId);
    DeliveryFeeResponseDTO getDeliveryFee(Long restaurantId, String destinationCep);
    boolean existsByIdAndActiveTrue(Long id);
    void deleteRestaurant(Long restaurantId);
}
