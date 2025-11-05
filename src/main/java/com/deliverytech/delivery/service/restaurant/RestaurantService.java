package com.deliverytech.delivery.service.restaurant;

import com.deliverytech.delivery.dto.cep.CepLocationDTO;
import com.deliverytech.delivery.dto.cep.DeliveryFeeResponseDTO;
import com.deliverytech.delivery.dto.restaurant.RestaurantDTO;
import com.deliverytech.delivery.dto.restaurant.RestaurantResponseDTO;
import com.deliverytech.delivery.entity.Restaurant;
import com.deliverytech.delivery.exception.EntityNotFoundException;
import com.deliverytech.delivery.repository.IRestaurantRepository;
import com.deliverytech.delivery.service.cep.ICepService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.CalculateDeliveryFee;

import java.util.Arrays;
import java.util.List;

@Service
public class RestaurantService implements IRestaurantService {

    @Autowired
    private IRestaurantRepository restaurantRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ICepService cepService;

    public RestaurantService() {
        super();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestaurantResponseDTO> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAllByActiveTrue();
        for (Restaurant r : restaurants) {
            if (r.getProducts() != null) {
                r.getProducts().size();
            }
            if (r.getWorkHours() != null) {
                r.getWorkHours().size();
            }
        }
        return Arrays.asList(modelMapper.map(restaurants, RestaurantResponseDTO[].class));
    }

    @Override
    public RestaurantResponseDTO createRestaurant(RestaurantDTO restaurantDTO) {
        Restaurant entity = modelMapper.map(restaurantDTO, Restaurant.class);
        CepLocationDTO cepLocationDTO = cepService.getCepLocation(restaurantDTO.getCep());
        entity.setLatitude(cepLocationDTO.latitude());
        entity.setLongitude(cepLocationDTO.longitude());
        entity.setActive(true);
        Restaurant restaurant = restaurantRepository.save(entity);
        return modelMapper.map(restaurant, RestaurantResponseDTO.class);
    }

    @Transactional
    public RestaurantResponseDTO updateRestaurant(Long restaurantId, RestaurantDTO restaurantDTO) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        if (restaurant == null) {
            throw new EntityNotFoundException("Restaurant not found with id:" + restaurantId);
        }
        modelMapper.map(restaurantDTO, restaurant);
        CepLocationDTO cepLocationDTO = cepService.getCepLocation(restaurantDTO.getCep());
        restaurant.setLatitude(cepLocationDTO.latitude());
        restaurant.setLongitude(cepLocationDTO.longitude());
        restaurant = restaurantRepository.save(restaurant);
        return modelMapper.map(restaurant, RestaurantResponseDTO.class);
    }

    @Override
    public RestaurantResponseDTO getRestaurant(Long restaurantId) {
        return restaurantRepository.findById(restaurantId).map(restaurant -> modelMapper
                .map(restaurant, RestaurantResponseDTO.class))
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found with id:" + restaurantId));
    }

    @Override
    public List<RestaurantResponseDTO> searchByName(String name) {
        List<Restaurant> restaurants = restaurantRepository.searchRestaurantByNameAndActiveTrue(name);
        return Arrays.asList(modelMapper.map(restaurants, RestaurantResponseDTO[].class));
    }

    @Override
    public List<RestaurantResponseDTO> searchByCategory(String category) {
        List<Restaurant> restaurants = restaurantRepository.searchRestaurantByCuisineAndActiveTrue(category);
        return Arrays.asList(modelMapper.map(restaurants, RestaurantResponseDTO[].class));
    }

    @Override
    public List<RestaurantResponseDTO> searchAllActiveTrue() {
        List<Restaurant> restaurants = restaurantRepository.findAllByActiveTrue();
        return Arrays.asList(modelMapper.map(restaurants, RestaurantResponseDTO[].class));
    }

    @Override
    public List<RestaurantResponseDTO> searchAllActiveFalse() {
        List<Restaurant> restaurants = restaurantRepository.findAllByActiveFalse();
        return Arrays.asList(modelMapper.map(restaurants, RestaurantResponseDTO[].class));
    }

    @Override
    public RestaurantResponseDTO alterRestaurantStatus(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new EntityNotFoundException("Restaurant not found with id: " + restaurantId)
        );
        restaurant.setActive(!restaurant.isActive());
        restaurant = restaurantRepository.save(restaurant);
        return modelMapper.map(restaurant, RestaurantResponseDTO.class);
    }

    @Override
    public DeliveryFeeResponseDTO getDeliveryFee(Long restaurantId, String destinationCep) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new EntityNotFoundException("Restaurant not found with id: " + restaurantId)
        );
        CepLocationDTO cepLocationDTO = cepService.getCepLocation(destinationCep);
        var fee = CalculateDeliveryFee.calculateTotalFee(
                restaurant.getLatitude(),
                restaurant.getLongitude(),
                cepLocationDTO.latitude(),
                cepLocationDTO.longitude(),
                restaurant.getDeliveryFee(),
                restaurant.getDeliveryKmFee()
                );
        var response = new DeliveryFeeResponseDTO();
        response.setDeliveryFee(fee);
        response.setDestinationCep(destinationCep);
        response.setOriginCep(restaurant.getCep());
        return response;
    }

    @Override
    public boolean existsByIdAndActiveTrue(Long restaurantId) {
        return restaurantRepository.existsByIdAndActiveTrue(restaurantId);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) {
        if(!restaurantRepository.existsByIdAndActiveTrue(restaurantId)) {
            throw new EntityNotFoundException("Restaurant not found with id: " + restaurantId);
        }
        var restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new EntityNotFoundException("Restaurant not found with id: " + restaurantId)
        );
        restaurant.setActive(false);
        restaurantRepository.save(restaurant);
    }
}
