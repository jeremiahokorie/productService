package com.productservice.service.Impl;

import com.productservice.core.exceptions.CustomException;
import com.productservice.dto.request.OrderRequestDto;
import com.productservice.persistence.entity.Category;
import com.productservice.persistence.entity.Item;
import com.productservice.persistence.entity.Order;
import com.productservice.persistence.entity.ShippingAddress;
import com.productservice.persistence.repository.CategoryRepository;
import com.productservice.persistence.repository.ITemRepository;
import com.productservice.persistence.repository.OrderRepository;
import com.productservice.persistence.repository.ShippingAddressRepository;
import com.productservice.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class DefaultOrderService implements OrderService {

    private OrderRepository orderRepository;

    private ITemRepository itemRepository;

    private CategoryRepository categoryRepository;

    private ShippingAddressRepository addressRepository;

    @Override
    public Order createOrder(OrderRequestDto request) {
        Order order = new Order();
        Item item = itemRepository.findById(request.getItemId()).orElseThrow(() -> new CustomException("Item not found"));
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new CustomException("Category not found"));
        ShippingAddress address = addressRepository.findById(request.getAddressId()).orElseThrow(() -> new CustomException("Address not found"));
        order.setItem(item);
        order.setCategory(category);
        order.setAddress(address);
        order.setQty(request.getQty());
        order.setDate(new Date());
        order.setStatus("Pending");
        orderRepository.save(order);
        return order;
    }


    @Override//45
    public List<Order> getOrder() {
     List<Order> request =  orderRepository.findAll();
        return request;
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException("Order not found"));

    }

}
