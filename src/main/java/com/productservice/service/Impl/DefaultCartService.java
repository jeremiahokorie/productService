package com.productservice.service.Impl;

import com.productservice.core.exceptions.CustomException;
import com.productservice.dto.request.CartDto;
import com.productservice.dto.request.CartItemRequest;
import com.productservice.persistence.entity.Cart;
import com.productservice.persistence.entity.ITem;
import com.productservice.persistence.repository.CartRepository;
import com.productservice.persistence.repository.ITemRepository;
import com.productservice.service.CartService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultCartService implements CartService {
    ITemRepository iTemRepository;
    CartRepository cartRepository;

//    @Override
//    public void addItemToCart(Long cartId, Long itemId) {
//        Cart cart = cartRepository.findById(cartId)
//                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));
//        ITem item = iTemRepository.findById(itemId)
//                .orElseThrow(() -> new EntityNotFoundException("Item not found"));
//
//        cart.getItems().add(item);
//        cart.setDate(new Date());
//        cartRepository.save(cart);
//    }

    @Override
    public List<Cart> getAllCart() {
        return cartRepository.findAll();
    }

    @Override
    public void deleteCartItem() {
        cartRepository.deleteAll();
    }

    @Override
    public CartDto addToCart(CartDto cartDto) {
        ITem item = iTemRepository.findById(cartDto.getItem().getId())
              .orElseThrow(() -> new CustomException("Item not found"));
        Cart cart = new Cart();
        cart.setDate(new Date());
        cart.getItems().add(item);
        cart.setQuantity(cart.getQuantity());
        cartRepository.save(cart);
        return cartDto;
    }


}

