package com.productservice.persistence.repository;

import com.productservice.persistence.entity.Cart;
import com.productservice.persistence.entity.ITem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}