package com.deliverytech.delivery.service;


import com.deliverytech.delivery.dto.RestaurantDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IRestaurantService {
    List<RestaurantDTO> getAllRestaurants();
    RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO);
}
