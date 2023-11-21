package com.productservice.service;

import com.productservice.dto.request.CartDto;
import com.productservice.persistence.entity.Cart;

import java.util.List;

public interface CartService {

//
//    void addItemToCart(Long cartId, Long itemId);
//

    List<Cart> getAllCart();

    void deleteCartItem();

    CartDto addToCart(CartDto cartDto);

    CartDto getCartById(Long id);

    void deleteCart(Long id);

    void addItemToCart(Long cartId, Long itemId);
}
