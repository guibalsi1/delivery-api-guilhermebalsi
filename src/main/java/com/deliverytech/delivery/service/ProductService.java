package com.deliverytech.delivery.service;


import com.deliverytech.delivery.dto.ProductDTO;
import com.deliverytech.delivery.entity.Product;
import com.deliverytech.delivery.repository.IProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductService implements IProductService{
    @Autowired
    private IProductRepository productRepository;

    @Override
    public List<ProductDTO> getProductsByRestaurantId(Long restaurantId) {
        ModelMapper modelMapper = new ModelMapper();
        List<Product> products = productRepository.findAllByRestaurantId(restaurantId);
        return Arrays.asList(modelMapper.map(products, ProductDTO[].class));
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO, Long restaurantId) {
        return null;
    }
}
