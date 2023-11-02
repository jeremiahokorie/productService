package com.productservice.service;

import com.productservice.dto.request.OrderRequestDto;
import com.productservice.persistence.entity.Order;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface OrderService{
    Order createOrder(OrderRequestDto orderRequestDto);

    List<Order> getOrder();

    Order getOrderById(Long orderId);
}
