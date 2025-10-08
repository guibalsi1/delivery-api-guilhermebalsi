package com.deliverytech.delivery.service;

import com.deliverytech.delivery.dto.RestaurantDTO;
import com.deliverytech.delivery.entity.Restaurant;
import com.deliverytech.delivery.repository.IRestaurantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class RestaurantService implements IRestaurantService{

    @Autowired
    private IRestaurantRepository restaurantRepository;

    public RestaurantService() {
        super();
    }

    @Override
    public List<RestaurantDTO> getAllRestaurants() {
        ModelMapper modelMapper = new ModelMapper();
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return Arrays.asList(modelMapper.map(restaurants, RestaurantDTO[].class));
        //return restaurantRepository.findAll().stream().map(this::toRestaurantDTO).collect(Collectors.toList());
    }

    @Override
    public RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Restaurant entity = modelMapper.map(restaurantDTO, Restaurant.class);
        Restaurant restaurant = restaurantRepository.save(entity);
        return modelMapper.map(restaurant, RestaurantDTO.class);
    }

    private Restaurant toRestaurant(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantDTO.getName());
        restaurant.setDescription(restaurantDTO.getDescription());
        return restaurant;
    }

    private RestaurantDTO toRestaurantDTO(Restaurant restaurant) {
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setName(restaurant.getName());
        restaurantDTO.setDescription(restaurant.getDescription());
        return restaurantDTO;
    }
}
