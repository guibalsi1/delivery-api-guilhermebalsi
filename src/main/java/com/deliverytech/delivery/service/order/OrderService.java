package com.deliverytech.delivery.service.order;

import com.deliverytech.delivery.dto.orderItem.OrderItemDTO;
import com.deliverytech.delivery.dto.order.OrderDTO;
import com.deliverytech.delivery.dto.order.OrderResponseDTO;
import com.deliverytech.delivery.entity.Order;
import com.deliverytech.delivery.entity.OrderItem;
import com.deliverytech.delivery.entity.OrderStatus;
import com.deliverytech.delivery.exception.EntityNotFoundException;
import com.deliverytech.delivery.repository.IOrderRepository;
import com.deliverytech.delivery.service.client.IClientService;
import com.deliverytech.delivery.service.product.IProductService;
import com.deliverytech.delivery.service.restaurant.IRestaurantService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.CalculateDeliveryFee;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private IClientService clientService;
    @Autowired
    private IRestaurantService restaurantService;
    @Autowired
    private IProductService productService;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OrderResponseDTO createOrder(OrderDTO orderDTO) {
        Order entity = modelMapper.map(orderDTO, Order.class);
        if(!clientService.existsByIdAndActiveTrue(orderDTO.getClientId())) {
            throw new EntityNotFoundException("Client not found with id: " + orderDTO.getClientId());
        } else if(!restaurantService.existsByIdAndActiveTrue(orderDTO.getRestaurantId())) {
            throw new EntityNotFoundException("Restaurant not found with id: " + orderDTO.getRestaurantId());
        }

        List<OrderItem> orderItens = new ArrayList<>();
        BigDecimal subtotal = BigDecimal.ZERO;
        for(OrderItemDTO itemDTO: orderDTO.getOrderItemList()) {
            if (!productService.existsByIdAndIsActive(itemDTO.getProductId())) {
                throw new EntityNotFoundException("Product not found with id: " + itemDTO.getProductId());
            }
            if (!productService.existsByRestaurantId(orderDTO.getRestaurantId())) {
                throw new EntityNotFoundException("Product not found with id: " + itemDTO.getProductId());
            }
            OrderItem item = modelMapper.map(itemDTO, OrderItem.class);
            item.setSubTotal(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            subtotal = subtotal.add(item.getSubTotal());
            orderItens.add(item);
        }
        entity.setOrderItemList(orderItens);
        var client = clientService.getClient(orderDTO.getClientId());
        var restaurant = restaurantService.getRestaurant(orderDTO.getRestaurantId());

        entity.setDeliveryFee(CalculateDeliveryFee.calculateTotalFee(client.getLatitude(), client.getLongitude(),
                restaurant.getLatitude(), restaurant.getLongitude(), restaurant.getDeliveryFee(),
                restaurant.getDeliveryKmFee())
        );
        entity.setOrderStatus(OrderStatus.PENDING);
        entity.setSubTotal(subtotal);
        entity.setTotal(subtotal.add(entity.getDeliveryFee()));
        Order order = orderRepository.save(entity);
        return modelMapper.map(order, OrderResponseDTO.class);
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        List<Order> orders = orderRepository.findAll();
        return Arrays.asList(modelMapper.map(orders, OrderResponseDTO[].class));
    }

    @Override
    public List<OrderResponseDTO> getAllOrdersByClientId(Long clientId) {
        if(!clientService.existsByIdAndActiveTrue(clientId)) {
            throw new EntityNotFoundException("Client not found with id: " + clientId);
        }
        var orders = orderRepository.findAllOrderByClient_Id(clientId);
        return Arrays.asList(modelMapper.map(orders, OrderResponseDTO[].class));
    }

    @Override
    public List<OrderResponseDTO> getAllOrdersByRestaurantId(Long restaurantId) {
        if(!restaurantService.existsByIdAndActiveTrue(restaurantId)) {
            throw new EntityNotFoundException("Restaurant not found with id: " + restaurantId);
        }
        var orders = orderRepository.findAllByRestaurant_Id(restaurantId);
        return Arrays.asList(modelMapper.map(orders, OrderResponseDTO[].class));
    }

    @Override
    public List<OrderResponseDTO> getAllOrdersByClientAndRestaurantId(Long clientId, Long restaurantId) {
        if(!restaurantService.existsByIdAndActiveTrue(restaurantId)) {
            throw new EntityNotFoundException("Restaurant not found with id: " + restaurantId);
        } else if(!clientService.existsByIdAndActiveTrue(clientId)) {
            throw new EntityNotFoundException("Client not found with id: " + clientId);
        }
        var orders = orderRepository.findAllByClient_IdAndRestaurant_Id(clientId, restaurantId);
        return Arrays.asList(modelMapper.map(orders, OrderResponseDTO[].class));
    }

    @Override
    public List<OrderResponseDTO> getAllOrdersByOrderStatus(OrderStatus orderStatus) {
        return Arrays.asList(
                modelMapper.map(orderRepository.findAllByOrderStatus(orderStatus), OrderResponseDTO[].class)
        );
    }

    @Override
    public List<OrderResponseDTO> getAllOrdersByOrderStatusAndClient(OrderStatus orderStatus, Long clientId) {
        if(!clientService.existsByIdAndActiveTrue(clientId)) {
            throw new EntityNotFoundException("Client not found with id: " + clientId);
        }
        return Arrays.asList(
                modelMapper.map(orderRepository.findAllByOrderStatusAndClient_Id(orderStatus, clientId), OrderResponseDTO[].class)
        );
    }

    @Override
    public List<OrderResponseDTO> getAllOrdersByOrderStatusAndRestaurant(OrderStatus orderStatus, Long restaurantId) {
        return List.of();
    }

    @Override
    public List<OrderResponseDTO> getAllOrdersByOrderStatusAndClientAndRestaurant(OrderStatus orderStatus, Long clientId, Long restaurantId) {
        return List.of();
    }

    @Override
    public List<OrderResponseDTO> getAllOrdersByOrderDate(String orderDate) {
        return List.of();
    }

    @Override
    public List<OrderResponseDTO> getAllOrdersByOrderDateBetween(String orderDateAfter, String orderDateBefore) {
        return List.of();
    }

    @Override
    public List<OrderResponseDTO> getAllOrdersByOrderDateAfter(String orderDateAfter) {
        return List.of();
    }

    @Override
    public List<OrderResponseDTO> getAllOrdersByOrderDateBefore(String orderDateBefore) {
        return List.of();
    }

    @Override
    public List<OrderResponseDTO> getAllOrdersByOrderDateAndOrderStatus(String orderDate, OrderStatus orderStatus) {
        return List.of();
    }

    @Override
    public OrderResponseDTO updateOrderStatus(Long orderId) {
        return null;
    }

    @Override
    public OrderResponseDTO getOrder(Long orderId) {
        return null;
    }

    @Override
    public void deleteOrder(Long orderId) {

    }
}
