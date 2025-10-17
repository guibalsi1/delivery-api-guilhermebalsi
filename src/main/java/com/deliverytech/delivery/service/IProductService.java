package com.deliverytech.delivery.service;


import com.deliverytech.delivery.dto.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IProductService {
    List<ProductDTO> getProductsByRestaurantId(Long restaurantId);
    ProductDTO createProduct(ProductDTO productDTO, Long restaurantId);
}
