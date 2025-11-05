package com.deliverytech.delivery.service.product;


import com.deliverytech.delivery.dto.product.ProductDTO;
import com.deliverytech.delivery.dto.product.ProductResponseDTO;
import com.deliverytech.delivery.entity.Product;
import com.deliverytech.delivery.entity.ProductStatus;
import com.deliverytech.delivery.entity.Restaurant;
import com.deliverytech.delivery.exception.BusinessException;
import com.deliverytech.delivery.repository.IProductRepository;
import com.deliverytech.delivery.service.restaurant.IRestaurantService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductService implements IProductService {
    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private IRestaurantService restaurantService;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<ProductResponseDTO> getProductsByRestaurantId(Long restaurantId) {
        if(!restaurantService.existsByIdAndActiveTrue(restaurantId)) {
            throw new BusinessException("Restaurant not found with id: " + restaurantId);
        }
        List<Product> products = productRepository.findAllByRestaurant_IdAndIsActive(restaurantId, ProductStatus.AVAILABLE);
        return Arrays.asList(modelMapper.map(products, ProductResponseDTO[].class));
    }

    @Override
    public ProductResponseDTO createProduct(ProductDTO productDTO) {
        Product entity = modelMapper.map(productDTO, Product.class);
        if(!restaurantService.existsByIdAndActiveTrue(productDTO.getRestaurantId())) {
            throw new BusinessException("Restaurant not found with id: " + productDTO.getRestaurantId());
        }
        Product product = productRepository.save(entity);
        return modelMapper.map(product, ProductResponseDTO.class);
    }

    @Override
    public ProductResponseDTO getProduct(Long productId) {
        return productRepository.findById(productId).map(product -> modelMapper.map(product, ProductResponseDTO.class))
                .orElseThrow(() -> new BusinessException("Product not found with id: " + productId));
    }

    @Override
    public List<ProductResponseDTO> getAllProductsByRestaurant(Restaurant restaurant) {
        List<Product> products = productRepository.findAllByRestaurantAndIsActive(restaurant, ProductStatus.AVAILABLE);
        return Arrays.asList(modelMapper.map(products, ProductResponseDTO[].class));
    }

    @Override
    public List<ProductResponseDTO> getAllProductsByCategory(String category) {
        List<Product> products = productRepository.findAllByCategoryAndIsActive(category, ProductStatus.AVAILABLE);
        return Arrays.asList(modelMapper.map(products, ProductResponseDTO[].class));
    }

    @Override
    public List<ProductResponseDTO> getAllProductsByNameSearch(String search) {
        List<Product> products = productRepository.findByNameContainingIgnoreCaseAndIsActive(search, ProductStatus.AVAILABLE);
        return Arrays.asList(modelMapper.map(products, ProductResponseDTO[].class));
    }

    @Override
    public List<ProductResponseDTO> getAllProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        List<Product> products = productRepository.findByPriceBetweenAndIsActive(minPrice, maxPrice, ProductStatus.AVAILABLE);
        return Arrays.asList(modelMapper.map(products, ProductResponseDTO[].class));
    }

    @Override
    public List<ProductResponseDTO> getAllProductsByMaxPrice(BigDecimal maxPrice) {
        List<Product> products = productRepository.findByPriceIsLessThanEqualAndIsActive(maxPrice, ProductStatus.AVAILABLE);
        return Arrays.asList(modelMapper.map(products, ProductResponseDTO[].class));
    }

    @Override
    public ProductResponseDTO alterProductStatus(Long productId) {
        Product productAux = productRepository.findById(productId).orElseThrow(
                () -> new BusinessException("Product not found with id: " + productId));
        if (productAux.getIsActive() == ProductStatus.AVAILABLE) {
            productAux.setIsActive(ProductStatus.UNAVAILABLE);
        } else {
            productAux.setIsActive(ProductStatus.AVAILABLE);
        }
        productRepository.save(productAux);
        return modelMapper.map(productAux, ProductResponseDTO.class);
    }

    @Override
    public ProductResponseDTO updateProduct(Long productId, ProductDTO productDTO) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new BusinessException("Product not found with id: " + productId)
        );
        modelMapper.map(productDTO, product);
        product = productRepository.save(product);
        return modelMapper.map(product, ProductResponseDTO.class);
    }

    @Override
    public boolean existsByIdAndIsActive(Long id) {
        return productRepository.existsByIdAndIsActive(id, ProductStatus.AVAILABLE);
    }

    @Override
    public boolean existsByRestaurantId(Long restaurantId) {
        return productRepository.existsByRestaurant_Id(restaurantId);
    }

    @Override
    public void deleteProduct(Long productId) {
        if(!productRepository.existsById(productId)) {
            throw new BusinessException("Product not found with id: " + productId);
        }
        productRepository.deleteById(productId);
    }

}
