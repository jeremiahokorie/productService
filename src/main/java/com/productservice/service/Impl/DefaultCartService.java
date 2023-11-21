package com.productservice.service.Impl;

import com.productservice.core.exceptions.CustomException;
import com.productservice.dto.request.CartDto;
import com.productservice.persistence.entity.Cart;
import com.productservice.persistence.entity.Item;
import com.productservice.persistence.entity.User;
import com.productservice.persistence.repository.CartRepository;
import com.productservice.persistence.repository.ITemRepository;
import com.productservice.persistence.repository.UserRepository;
import com.productservice.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class DefaultCartService implements CartService {
    ITemRepository iTemRepository;
    CartRepository cartRepository;
    UserRepository userRepository;

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
        Item item = iTemRepository.findById(cartDto.getItemId())
              .orElseThrow(() -> new CustomException("Item not found"));

        User user = userRepository.findById(cartDto.getUserId())
                .orElseThrow(() -> new CustomException("User not found"));
        Cart cart = new Cart();
        cart.setCartId(cartDto.getCartId());
        cart.setDate(new Date());
        cart.setItem(item);
        cart.setQuantity(cartDto.getQuantity());
        cart.setUser(user);
        cart.setStatus("in progress");
        cartRepository.save(cart);
        return cartDto;
    }

    @Override
    public CartDto getCartById(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new CustomException("Cart not found"));
        CartDto cartDto = new CartDto();
        cartDto.setCartId(cart.getCartId());
        cartDto.setDate(cart.getDate());
        cartDto.setItemId(cart.getItem().getId());
        cartDto.setQuantity(cart.getQuantity());
        cartDto.setStatus(cart.getStatus());
        return cartDto;
    }

    @Override
    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }

    //AddItemToCart
    @Override
    public void addItemToCart(Long cartId, Long itemId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CustomException("Cart not found"));
        Item item = iTemRepository.findById(itemId)
                .orElseThrow(() -> new CustomException("Item not found"));

        cart.setItem(item);
        cart.setDate(new Date());
        cart.setStatus("active");
        cartRepository.save(cart);
    }


}

