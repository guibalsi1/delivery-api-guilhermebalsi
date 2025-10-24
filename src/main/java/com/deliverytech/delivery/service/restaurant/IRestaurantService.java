package com.deliverytech.delivery.service.restaurant;


import com.deliverytech.delivery.dto.restaurant.RestaurantDTO;
import com.deliverytech.delivery.dto.restaurant.RestaurantResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IRestaurantService {
    List<RestaurantResponseDTO> getAllRestaurants();
    RestaurantResponseDTO createRestaurant(RestaurantDTO restaurantDTO);
    RestaurantResponseDTO updateRestaurant(Long restaurantId, RestaurantDTO restaurantDTO);
    RestaurantResponseDTO getRestaurant(Long restaurantId);
    void deleteRestaurant(Long restaurantId);
}
