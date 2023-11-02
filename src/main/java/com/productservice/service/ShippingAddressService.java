package com.productservice.service;

import com.productservice.dto.request.ShippingAddressDto;
import com.productservice.dto.request.ShippingAddressRequest;
import com.productservice.persistence.entity.ShippingAddress;

import java.util.List;

public interface ShippingAddressService {
    ShippingAddressDto createAddress(ShippingAddressRequest shippingAddressRequest);

    List<ShippingAddress> getShippingAddress();

    ShippingAddress getShippingAddressById(Long id);
}
