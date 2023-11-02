package com.productservice.controller;

import com.productservice.core.constants.AppConstant;
import com.productservice.dto.request.OrderRequestDto;
import com.productservice.persistence.entity.Order;
import com.productservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(AppConstant.APP_CONTEXT)
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/createOrder")
    public Order createOrder(@RequestBody OrderRequestDto orderRequestDto){
        return orderService.createOrder(orderRequestDto);
    }

    @GetMapping("/getOrder")
    public ResponseEntity<Order> getAllOrder(){
        List<Order> order = orderService.getOrder();
        return new ResponseEntity(order,HttpStatus.FOUND);
    }

    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable("orderId") Long orderId){
        return orderService.getOrderById(orderId);
    }





}
