package com.productservice.controller;

import com.productservice.core.constants.AppConstant;
import com.productservice.dto.request.CartDto;
import com.productservice.persistence.entity.Cart;
import com.productservice.service.CartService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AppConstant.APP_CONTEXT)
@AllArgsConstructor
@Slf4j
//@PreAuthorize("isAuthenticated()")
public class CartController {
    private CartService cartService;

    //    @PostMapping("/cart/{cartId}/add")
    //    public ResponseEntity<String> addItemToCart(@PathVariable Long cartId, @RequestParam Long itemId) {
    //        cartService.addItemToCart(cartId, itemId);
    //        return ResponseEntity.ok("Item added to cart successfully");
    //    }

    @GetMapping("/cart/getCart")
    public ResponseEntity<?> getAllCart() {
        List<Cart> cart = cartService.getAllCart();
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @DeleteMapping("/deleteCartItem")
    public void deleteCartItem() {
        cartService.deleteCartItem();
    }
    //add to cart
    //get cart
    //delete cart
    //delete item from cart
    //update cart
    //get cart by id

    @PostMapping("/cart/addToCart")
    public CartDto addToCart(@RequestBody CartDto cartDto) {
        return cartService.addToCart(cartDto);
    }

    @GetMapping("/cart/{id}")
    public ResponseEntity<CartDto>getById(@PathVariable("id") Long id){
        CartDto cartDto = cartService.getCartById(id);
        return ResponseEntity.ok(cartDto);
    }

    @DeleteMapping("/cart/{id}")
    public void deleteCart(@PathVariable("id") Long id){
        cartService.deleteCart(id);
    }

    //AddItemToCart
    @PostMapping("/cart/{cartId}/add")
    public ResponseEntity<String> addItemToCart(@PathVariable Long cartId, @RequestParam Long itemId) {
        cartService.addItemToCart(cartId, itemId);
        return ResponseEntity.ok("Item added to cart successfully");
    }

    //generate api request for add item to cart
    //generate api request for get cart
    //generate api request for delete cart
    //generate api request for delete item from cart



}
