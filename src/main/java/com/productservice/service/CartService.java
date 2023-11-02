package com.productservice.service;

import com.productservice.dto.request.CartDto;
import com.productservice.persistence.entity.Cart;
import com.productservice.persistence.entity.ITem;

import java.util.List;

public interface CartService {

//
//    void addItemToCart(Long cartId, Long itemId);
//

    List<Cart> getAllCart();

    void deleteCartItem();

    CartDto addToCart(CartDto cartDto);
}
